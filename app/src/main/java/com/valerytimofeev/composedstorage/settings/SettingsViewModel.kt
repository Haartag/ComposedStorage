package com.valerytimofeev.composedstorage.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.sortCategoryListByKey
import com.valerytimofeev.composedstorage.utils.sortTabListByKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {


    private val currentTabOrder = mutableStateOf(listOf(""))
    private val currentCategoryOrder = mutableStateOf(listOf(""))

    var tabDatabaseOrder = listOf<TabItem>()
    var categoryDatabaseOrder = listOf<CategoryItem>()

    var deletedTabs = mutableListOf<String>()
    var deletedCategories = mutableListOf<String>()

    fun getTabFlow(): Flow<List<TabItem>> {
        return repository.getTabsFlow()
    }

    fun getCategoryFlow(tabName: String): Flow<List<CategoryItem>> {
        return repository.getCategoryByTabFlow(Tab = tabName)
    }

    fun saveCurrentTabOrder(order: List<String>) {
        currentTabOrder.value = order
    }
    fun saveCurrentCategoryOrder(order: List<String>) {
        currentCategoryOrder.value = order
    }

    fun saveToDeletedTabs(item: String) {
        deletedTabs.add(item)
    }

    fun saveToDeletedCategories(item: String) {
        deletedCategories.add(item)
    }

    fun saveNewTabOrderInDatabase(navController: NavController) {
        val newTabList = tabDatabaseOrder.sortTabListByKey(currentTabOrder.value)
        viewModelScope.launch {
            repository.deleteTabTable()
            newTabList.forEach {
                repository.insertNewTab(it)
            }
            deletedTabs.forEach {
                repository.deleteTabFromStorages(it)
                repository.deleteTabFromCategories(it)
            }
            navController.navigate("category_list_screen")
        }
    }

    fun saveNewCategoryOrderInDatabase(navController: NavController, tabName: String) {
        val newCategoryList = categoryDatabaseOrder.sortCategoryListByKey(currentCategoryOrder.value)
        viewModelScope.launch {
            repository.deleteCategoryTable(tabName)
            newCategoryList.forEach {
                repository.insertNewCategory(it)
            }
            deletedCategories.forEach {
                repository.deleteCategoryFromStorages(it)
            }
            navController.navigate("category_list_screen")
        }
    }
}
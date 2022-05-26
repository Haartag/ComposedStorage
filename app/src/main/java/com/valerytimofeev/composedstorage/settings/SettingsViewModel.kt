package com.valerytimofeev.composedstorage.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.utils.sortListByKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {


    val currentTabOrder = mutableStateOf(listOf(""))

    var tabDatabaseOrder = listOf<TabItem>()

    var deletedTabs = mutableListOf<String>()

    fun getFlow(): Flow<List<TabItem>> {
        return repository.getTabsFlow()
    }

    fun saveCurrentOrder(order: List<String>) {
        currentTabOrder.value = order
    }

    fun saveToDeleted(item: String) {
        deletedTabs.add(item)
    }

    fun saveNewOrderInDatabase(navController: NavController) {
        val newTabList = tabDatabaseOrder.sortListByKey(currentTabOrder.value)
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

    fun getCardColor() {

    }


}
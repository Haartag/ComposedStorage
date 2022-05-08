package com.valerytimofeev.composedstorage.categorylist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.data.database.TabWithCategoriesRelation
import com.valerytimofeev.composedstorage.ui.theme.*
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    private val tabsAndCategoriesList = mutableStateListOf<TabWithCategoriesRelation>()
    val tabList = mutableStateListOf<TabItem>()
    val tabCount = mutableStateOf(0)

    //for pager infinite loop
    val startIndex = Int.MAX_VALUE / 2

    val tabListLoadEnded = mutableStateOf(false)

    init {
        preload()
    }

    fun getCategoryNamesByTab(page: Int): List<String> {
        return tabsAndCategoriesList[page].categoryItems.map { it.category }
    }

    private fun preload() {
        viewModelScope.launch {
            loadTabCount()
            loadTabList()
            loadTabsWithCategories(tabList)
            tabListLoadEnded.value = true
        }
    }

    private suspend fun loadTabCount() {
        tabCount.value = repository.getTabsCount()
    }

    private suspend fun loadTabList() {
        repository.getTabs().forEach {
            tabList.add(it)
        }
    }

    private suspend fun loadTabsWithCategories(tabNames: List<TabItem>) {
        tabNames.forEach { tab ->
            repository.getCategoriesOfTab(tab.tabName).forEach {
                tabsAndCategoriesList.add(it)
            }
        }
    }

    fun getCategoryRowCount(tab: Int): Int {
        val sizeOfSortedCategoryList = tabsAndCategoriesList[tab].categoryItems.size
        return if (sizeOfSortedCategoryList % 2 == 0) {
            sizeOfSortedCategoryList / 2
        } else {
            sizeOfSortedCategoryList / 2 + 1
        }
    }

    var currentPage = mutableStateOf(0)

    fun getCategoryTypeColor(page: Int): Color {
        return Constants.colorsMap.getValue(tabsAndCategoriesList[page].tabItem.colorScheme)
    }
}

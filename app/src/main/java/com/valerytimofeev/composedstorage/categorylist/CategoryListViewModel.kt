package com.valerytimofeev.composedstorage.categorylist

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {
    val categoryList = mutableStateListOf<CategoryItem>()

    //for pager infinite loop
    val startIndex = Int.MAX_VALUE / 2

    val jobEnded = mutableStateOf(false)

    fun loadTabList() {
        if (categoryList.isEmpty()) {
            viewModelScope.launch {
                repository.getCategories().forEach {
                    categoryList.add(it)
                }
                jobEnded.value = true
            }
        }
    }

    fun getTabs(): List<String> {
        return categoryList.map { it.categoryType }.toSet().toList()
    }

    fun getCategoryByTab(tab: Int): List<String> {
        return categoryList.filter { it.categoryType == getTabs()[tab] }.map { it.category }
    }

    fun getTabSize(tab: Int): Int {
        return getCategoryByTab(tab).size
    }

    fun getCategoryRowCount(tab: Int): Int {
        val sizeOfSortedCategoryList = getTabSize(tab)
        return if (sizeOfSortedCategoryList % 2 == 0) {
            sizeOfSortedCategoryList / 2
        } else {
            sizeOfSortedCategoryList / 2 + 1
        }
    }

    var currentPage = mutableStateOf(0)

    //TODO rework color theming
    fun getCategoryTypeColor(page: Int): Color {
        return when (page) {
            0 -> Theme1Color
            1 -> Theme2Color
            2 -> Theme3Color
            3 -> Theme4Color
            4 -> Theme5Color
            else -> Color.LightGray
        }
    }
}

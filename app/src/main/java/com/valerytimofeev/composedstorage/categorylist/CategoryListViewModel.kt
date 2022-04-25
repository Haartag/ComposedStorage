package com.valerytimofeev.composedstorage.categorylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.StorageDatabase
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
    private val position = mutableStateOf(0)

    init {
        loadCategoryTypeList()
    }


    private val categoryTypeList = mutableStateOf(listOf(""))

    private fun loadCategoryTypeList() {
        viewModelScope.launch {
            repository.getCategories().forEach {
                categoryList.add(it)
            }
            categoryTypeList.value = categoryList.map { it.categoryType }.toSet().toList()
        }
    }


    fun getChosenCategoryTypeName(): String {
        return categoryTypeList.value[position.value]
    }

    fun getCategoryListSortedByType(): List<String> {
        return categoryList.filter { it.categoryType == getChosenCategoryTypeName() }
            .map { it.category }
    }

    //return count of rows for lazy column
    fun getCategoryListSize(): Int {
        val sizeOfSortedCategoryList = getCategoryListSortedByType().size
        return if (sizeOfSortedCategoryList % 2 == 0) {
            sizeOfSortedCategoryList / 2
        } else {
            sizeOfSortedCategoryList / 2 + 1
        }
    }

    fun changeCategoryType(change: Int) {
        position.value = when {
            position.value + change < 0 -> categoryTypeList.value.lastIndex
            position.value + change > categoryTypeList.value.lastIndex -> 0
            else -> position.value + change
        }
    }

    fun getCategoryTypeColor(): Color {
        return when (position.value) {
            0 -> Theme1Color
            1 -> Theme2Color
            2 -> Theme3Color
            3 -> Theme4Color
            4 -> Theme5Color
            else -> Color.LightGray
        }
    }
}

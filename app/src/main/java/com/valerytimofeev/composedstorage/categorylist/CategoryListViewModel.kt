package com.valerytimofeev.composedstorage.categorylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.ui.theme.Theme1Color
import com.valerytimofeev.composedstorage.ui.theme.Theme2Color
import com.valerytimofeev.composedstorage.ui.theme.Theme3Color


class CategoryListViewModel : ViewModel() {

    val categoryList = mutableStateListOf<CategoryItem>()
    val position = mutableStateOf(0)

    init {
        categoryList += CategoryItem(1, "Мясо", "Заморозка")
        categoryList += CategoryItem(2, "Рыба", "Заморозка")
        categoryList += CategoryItem(3, "Тесто", "Заморозка")
        categoryList += CategoryItem(4, "Варенье", "Консервы")
        categoryList += CategoryItem(5, "Для уборки", "Химия")
    }

    val categoryTypeList = categoryList.map { it.categoryType }.toSet().toList()

    fun getChosenCategoryTypeName(): String {
        return categoryTypeList[position.value]
    }

    fun getCategoryListSortedByType(): List<String> {
        return categoryList.filter { it.categoryType == getChosenCategoryTypeName() }
            .map { it.category }
    }

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
            position.value + change < 0 -> categoryTypeList.lastIndex
            position.value + change > categoryTypeList.lastIndex -> 0
            else -> position.value + change
        }
    }

    fun getCategoryTypeColor(): Color {
        return when (position.value) {
            0 -> Theme1Color
            1 -> Theme2Color
            2 -> Theme3Color
            else -> Color.LightGray
        }
    }
}
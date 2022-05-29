package com.valerytimofeev.composedstorage.utils

import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.TabItem

fun List<TabItem>.sortTabListByKey(key: List<String>): List<TabItem> {
    val result = mutableListOf<TabItem>()
    key.forEachIndexed {index, item ->
        result.add(TabItem(index + 1, item, this.first { it.tabName == item }.colorScheme))
    }
    return result
}

fun List<CategoryItem>.sortCategoryListByKey(key: List<String>): List<CategoryItem> {
    val result = mutableListOf<CategoryItem>()
    key.forEachIndexed {index, item ->
        result.add(CategoryItem(0, item, this.first { it.category == item }.tabName, this.first { it.category == item }.categoryImg))
    }
    return result
}
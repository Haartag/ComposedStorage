package com.valerytimofeev.composedstorage.utils

import com.valerytimofeev.composedstorage.data.database.TabItem

fun List<TabItem>.sortListByKey(key: List<String>): List<TabItem> {
    val result = mutableListOf<TabItem>()
    key.forEachIndexed {index, item ->
        result.add(TabItem(index + 1, item, this.first { it.tabName == item }.colorScheme))
    }
    return result
}
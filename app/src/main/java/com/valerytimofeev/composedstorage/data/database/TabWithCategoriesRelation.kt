package com.valerytimofeev.composedstorage.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class TabWithCategoriesRelation (
    @Embedded val tabItem: TabItem,
    @Relation(
        parentColumn = "tabName",
        entityColumn = "tabName"
    )
    val categoryItems: List<CategoryItem>
        )
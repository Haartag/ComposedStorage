package com.valerytimofeev.composedstorage.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithStorages(
    @Embedded val categoryItem: CategoryItem,
    @Relation(
        parentColumn = "category",
        entityColumn = "category"
    )
    val storageItems: List<StorageItem>
)
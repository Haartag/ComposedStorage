package com.valerytimofeev.composedstorage.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StorageDB")
data class StorageItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val name: String,
    @ColumnInfo val category: String,
    @ColumnInfo val sizeType: Int,
    @ColumnInfo val size: Int
)

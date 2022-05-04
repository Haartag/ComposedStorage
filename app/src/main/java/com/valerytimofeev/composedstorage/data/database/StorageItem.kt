package com.valerytimofeev.composedstorage.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StorageDB")
data class StorageItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo var name: String,
    @ColumnInfo var category: String,
    @ColumnInfo var sizeType: String,
    @ColumnInfo var size: Int
)

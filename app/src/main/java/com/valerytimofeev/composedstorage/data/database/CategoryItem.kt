package com.valerytimofeev.composedstorage.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryDB")
data class CategoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val category: String,
    @ColumnInfo val categoryType: String,
    @ColumnInfo val colorScheme: Int,

)
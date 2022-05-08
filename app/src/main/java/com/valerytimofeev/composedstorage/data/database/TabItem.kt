package com.valerytimofeev.composedstorage.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TabDB")
data class TabItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val tabName: String,
    @ColumnInfo val colorScheme: Int,
    )
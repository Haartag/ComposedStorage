package com.valerytimofeev.composedstorage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [StorageItem::class, CategoryItem::class, TabItem::class], version = 1, exportSchema = false)
abstract class StorageDatabase: RoomDatabase() {
    abstract fun storageDao(): StorageDAO
}
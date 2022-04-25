package com.valerytimofeev.composedstorage.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StorageItem::class, CategoryItem::class], version = 1, exportSchema = false)
abstract class StorageDatabase: RoomDatabase() {
    abstract fun storageDao(): StorageDAO
}
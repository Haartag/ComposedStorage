package com.valerytimofeev.composedstorage.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StorageItem::class, CategoryItem::class], version = 1, exportSchema = false)
abstract class StorageDatabase: RoomDatabase() {
    abstract val storageDao: StorageDAO

    companion object {
        @Volatile
        private var INSTANCE: StorageDatabase? = null

        fun getInstance(context: Context): StorageDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StorageDatabase::class.java,
                        "StorageDB"
                    )
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
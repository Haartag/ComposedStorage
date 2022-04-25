package com.valerytimofeev.composedstorage.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: StorageItem)

    @Update
    suspend fun update(item: StorageItem)

    @Delete
    suspend fun delete(item: StorageItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: CategoryItem)

    @Update
    suspend fun update(item: CategoryItem)

    @Delete
    suspend fun delete(item: CategoryItem)

    //Storage Queries

    @Query("SELECT COUNT(*) FROM StorageDB")
    suspend fun storageSize(): Int

    @Query("SELECT * FROM StorageDB")
    suspend fun getAllStorage(): List<StorageItem>

    @Query("SELECT DISTINCT category FROM StorageDB")
    suspend fun getCategories(): List<String>

    @Query("SELECT * FROM StorageDB WHERE category = :category")
    suspend fun getByCategory(category: String): List<StorageItem>

    //Category Queries
    @Query("SELECT COUNT(*) FROM CategoryDB")
    suspend fun categorySize(): Int

    @Query("SELECT * FROM CategoryDB")
    suspend fun getAllCategory(): List<CategoryItem>
}
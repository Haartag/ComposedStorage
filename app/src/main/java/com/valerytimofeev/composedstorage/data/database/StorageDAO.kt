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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: TabItem)

    @Update
    suspend fun update(item: TabItem)

    @Delete
    suspend fun delete(item: TabItem)

    //Storage Queries

    @Query("SELECT * FROM StorageDB WHERE category = :category")
    fun getByCategoryFlow(category: String): Flow<List<StorageItem>>

    @Query("SELECT name FROM StorageDB")
    fun getNamesFlow(): Flow<List<String>>

    //Category Queries

    @Query("SELECT * FROM CategoryDB WHERE tabName = :tabName")
    fun getByTabFlow(tabName: String): Flow<List<CategoryItem>>

    //CategoryWithStorage Queries

    @Transaction
    @Query("SELECT * FROM CategoryDB")
    fun getCategoriesWithItemsFlow(): Flow<List<CategoryWithStorages>>

    //Tab Queries

    @Query("SELECT * FROM TabDB")
    fun getAllTabsFlow(): Flow<List<TabItem>>


}
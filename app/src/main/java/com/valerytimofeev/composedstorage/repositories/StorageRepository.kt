package com.valerytimofeev.composedstorage.repositories

import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.StorageItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.ListForSearch
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun insertNewTab(tabItem: TabItem)

    suspend fun insertNewCategory(categoryItem: CategoryItem)

    suspend fun insertNewItem(storageItem: StorageItem)

    suspend fun updateItem(item: StorageItem)

    suspend fun deleteItem(item: StorageItem)

    suspend fun deleteTabTable()

    suspend fun deleteCategoryTable(tabName: String)

    suspend fun deleteTabFromStorages(tabName: String)

    suspend fun deleteCategoryFromStorages(category: String)

    suspend fun deleteTabFromCategories(tabName: String)

    fun getTabsFlow(): Flow<List<TabItem>>

    fun getCategoryByTabFlow(Tab: String): Flow<List<CategoryItem>>

    fun getItemsByCategoryFlow(category: String): Flow<List<StorageItem>>

    fun getList(): Flow<List<ListForSearch>>
}
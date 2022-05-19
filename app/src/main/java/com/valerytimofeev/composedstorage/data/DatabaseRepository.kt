package com.valerytimofeev.composedstorage.data

import com.valerytimofeev.composedstorage.data.database.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val storageDao: StorageDAO
) {
    suspend fun insertNewTab(tabItem: TabItem) {
        storageDao.add(tabItem)
    }

    suspend fun insertNewCategory(categoryItem: CategoryItem) {
        storageDao.add(categoryItem)
    }

    suspend fun insertNewItem(storageItem: StorageItem) {
        storageDao.add(storageItem)
    }

    suspend fun updateItem(item: StorageItem) {
        storageDao.update(item)
    }

    suspend fun deleteItem(item: StorageItem) {
        storageDao.delete(item)
    }

    //Flow functions
    fun getTabsFlow(): Flow<List<TabItem>> {
        return storageDao.getAllTabsFlow()
    }

    fun getCategoryByTabFlow(Tab: String): Flow<List<CategoryItem>> {
        return storageDao.getByTabFlow(tabName = Tab)
    }

    fun getItemsByCategoryFlow(category: String): Flow<List<StorageItem>> {
        return storageDao.getByCategoryFlow(category = category)
    }
}
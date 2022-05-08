package com.valerytimofeev.composedstorage.data

import com.valerytimofeev.composedstorage.data.database.*
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val storageDao: StorageDAO
) {
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

/*    suspend fun getCategoryByTabName(tabName: String): List<String> {
        return storageDao.getCategoryByTab(tabName = tabName)
    }*/

    suspend fun getItemsByCategory(category: String): List<StorageItem> {
        return storageDao.getByCategory(category = category)
    }

    suspend fun getTabs(): List<TabItem> {
        return storageDao.getAllTabs()
    }

    suspend fun getTabsCount(): Int {
        return storageDao.tabSize()
    }

    suspend fun getCategoryCount(): Int {
        return storageDao.categorySize()
    }



    suspend fun getCategoriesOfTab(tabName: String): List<TabWithCategoriesRelation> {
        return storageDao.getCategoriesOfTab(tabName)
    }

}
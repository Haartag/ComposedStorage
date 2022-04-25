package com.valerytimofeev.composedstorage.data

import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.StorageDAO
import com.valerytimofeev.composedstorage.data.database.StorageItem
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

    suspend fun getCategories(): List<CategoryItem> {
        return storageDao.getAllCategory()
    }

    suspend fun getItemsByCategory(category: String): List<StorageItem> {
        return storageDao.getByCategory(category = category)
    }

}
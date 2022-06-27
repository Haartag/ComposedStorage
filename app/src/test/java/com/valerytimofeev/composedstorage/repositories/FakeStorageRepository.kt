package com.valerytimofeev.composedstorage.repositories

import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.StorageItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.ListForSearch
import kotlinx.coroutines.flow.Flow

class FakeStorageRepository: StorageRepository {
    override suspend fun insertNewTab(tabItem: TabItem) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNewCategory(categoryItem: CategoryItem) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNewItem(storageItem: StorageItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: StorageItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: StorageItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTabTable() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoryTable(tabName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTabFromStorages(tabName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoryFromStorages(category: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTabFromCategories(tabName: String) {
        TODO("Not yet implemented")
    }

    override fun getTabsFlow(): Flow<List<TabItem>> {
        TODO("Not yet implemented")
    }

    override fun getCategoryByTabFlow(Tab: String): Flow<List<CategoryItem>> {
        TODO("Not yet implemented")
    }

    override fun getItemsByCategoryFlow(category: String): Flow<List<StorageItem>> {
        TODO("Not yet implemented")
    }

    override fun getList(): Flow<List<ListForSearch>> {
        TODO("Not yet implemented")
    }
}
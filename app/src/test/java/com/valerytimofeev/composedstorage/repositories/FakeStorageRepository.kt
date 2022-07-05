package com.valerytimofeev.composedstorage.repositories

import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.StorageItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.ListForSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeStorageRepository : StorageRepository {

    private val storageItems = mutableListOf<StorageItem>()
    private val categoryItems = mutableListOf<CategoryItem>()
    private val tabItems = mutableListOf<TabItem>()

    private val observableStorageItem = emitStorageItems()

    private fun emitStorageItems() = flow {
        emit(storageItems)
    }

    private fun emitCategoryItems() = flow {
        emit(categoryItems)
    }

    override suspend fun insertNewTab(tabItem: TabItem) {
        tabItems.add(tabItem)
    }

    override suspend fun insertNewCategory(categoryItem: CategoryItem) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNewItem(storageItem: StorageItem) {
        storageItems.add(storageItem)
    }

    override suspend fun updateItem(item: StorageItem) {
        val index = storageItems.indexOfFirst { it.id == item.id }
        storageItems[index] = item
    }

    override suspend fun deleteItem(item: StorageItem) {
        storageItems.remove(item)
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
        return emitCategoryItems()
    }

    override fun getItemsByCategoryFlow(category: String): Flow<List<StorageItem>> {
        return emitStorageItems()
    }

    override fun getList(): Flow<List<ListForSearch>> {
        TODO("Not yet implemented")
    }
}
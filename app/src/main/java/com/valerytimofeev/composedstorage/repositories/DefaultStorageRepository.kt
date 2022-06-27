package com.valerytimofeev.composedstorage.repositories

import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.StorageDAO
import com.valerytimofeev.composedstorage.data.local.StorageItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.ListForSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultStorageRepository @Inject constructor(
    private val storageDao: StorageDAO
): StorageRepository {
    override suspend fun insertNewTab(tabItem: TabItem) {
        storageDao.add(tabItem)
    }

    override suspend fun insertNewCategory(categoryItem: CategoryItem) {
        storageDao.add(categoryItem)
    }

    override suspend fun insertNewItem(storageItem: StorageItem) {
        storageDao.add(storageItem)
    }

    override suspend fun updateItem(item: StorageItem) {
        storageDao.update(item)
    }

    override suspend fun deleteItem(item: StorageItem) {
        storageDao.delete(item)
    }

    override suspend fun deleteTabTable() {
        storageDao.deleteTabTable()
    }

    override suspend fun deleteCategoryTable(tabName: String) {
        storageDao.deleteCategoryTable(tabName = tabName  )
    }

    override suspend fun deleteTabFromStorages(tabName: String) {
        storageDao.deleteTabFromStorages(tabName = tabName)
    }

    override suspend fun deleteCategoryFromStorages(category: String) {
        storageDao.deleteCategoryFromStorages(category = category)
    }

    override suspend fun deleteTabFromCategories(tabName: String) {
        storageDao.deleteTabFromCategories(tabName = tabName)
    }

    override fun getTabsFlow(): Flow<List<TabItem>> {
        return storageDao.getAllTabsFlow()
    }

    override fun getCategoryByTabFlow(Tab: String): Flow<List<CategoryItem>> {
        return storageDao.getByTabFlow(tabName = Tab)
    }

    override fun getItemsByCategoryFlow(category: String): Flow<List<StorageItem>> {
        return storageDao.getByCategoryFlow(category = category)
    }

    override fun getList() = flow<List<ListForSearch>> {
        val inputFlow = storageDao.getCategoriesWithItemsFlow()
        val result = mutableListOf<ListForSearch>()
        inputFlow.collect {
            it.forEach { category ->
                category.storageItems.forEach { storage ->
                    result.add(
                        ListForSearch(
                            category.categoryItem.tabName,
                            category.categoryItem.category,
                            storage.name,
                            storage.size.toString(),
                            storage.sizeType
                        )
                    )
                    emit(result)
                }
            }
        }
    }
}
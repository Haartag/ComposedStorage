package com.valerytimofeev.composedstorage.data

import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.StorageDAO
import com.valerytimofeev.composedstorage.data.database.StorageItem
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.utils.ListForSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    suspend fun deleteTabTable() {
        storageDao.deleteTabTable()
    }

    suspend fun deleteCategoryTable(tabName: String) {
        storageDao.deleteCategoryTable(tabName = tabName  )
    }

    suspend fun deleteTabFromStorages(tabName: String){
        storageDao.deleteTabFromStorages(tabName = tabName)
    }

    suspend fun deleteCategoryFromStorages(category: String){
        storageDao.deleteCategoryFromStorages(category = category)
    }

    suspend fun deleteTabFromCategories(tabName: String){
        storageDao.deleteTabFromCategories(tabName = tabName)
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


    fun getList() = flow<List<ListForSearch>> {
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


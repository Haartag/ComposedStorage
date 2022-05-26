package com.valerytimofeev.composedstorage.data

import android.util.Log
import com.valerytimofeev.composedstorage.data.database.*
import com.valerytimofeev.composedstorage.utils.ListForSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
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

    suspend fun deleteTabFromStorages(tabName: String){
        storageDao.deleteTabFromStorages(tabName = tabName)
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


package com.valerytimofeev.composedstorage.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class StorageDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: StorageDatabase
    lateinit var dao: StorageDAO

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.storageDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertStorageItem() = runTest {
        val storageItem = StorageItem(1, "name", "category", 1, 100)
        dao.add(storageItem)
        val allStorageItems = dao.getByCategoryFlow("category").first()
        assertThat(allStorageItems).isEqualTo(listOf(storageItem))
    }

    @Test
    fun deleteStorageItem() = runTest {
        val storageItem = StorageItem(1, "name", "category", 1, 100)
        dao.add(storageItem)
        dao.delete(storageItem)
        val allStorageItems = dao.getByCategoryFlow("category").first()
        assertThat(allStorageItems).isEmpty()
    }

    @Test
    fun insertCategoryItem() = runTest {
        val categoryItem = CategoryItem(1, "category", "tabName", 1)
        dao.add(categoryItem)
        val allCategoryItems = dao.getByTabFlow("tabName").first()
        assertThat(allCategoryItems).isEqualTo(listOf(categoryItem))
    }

    @Test
    fun deleteCategoryItem() = runTest {
        val categoryItem = CategoryItem(1, "category", "tabName", 1)
        dao.add(categoryItem)
        dao.delete(categoryItem)
        val allCategoryItems = dao.getByTabFlow("tabName").first()
        assertThat(allCategoryItems).isEmpty()
    }

    @Test
    fun insertTabItem() = runTest {
        val tabItem = TabItem(1, "tabName", 1)
        dao.add(tabItem)
        val allTabItems = dao.getAllTabsFlow().first()
        assertThat(allTabItems).isEqualTo(listOf(tabItem))
    }

    @Test
    fun deleteTabItem() = runTest {
        val tabItem = TabItem(1, "tabName", 1)
        dao.add(tabItem)
        dao.delete(tabItem)
        val allTabItems = dao.getAllTabsFlow().first()
        assertThat(allTabItems).isEmpty()
    }

    @Test
    fun getNames() = runTest {
        val storageItem = StorageItem(1, "test", "category", 1, 100)
        dao.add(storageItem)
        val allStorageNames = dao.getNamesFlow().first()
        assertThat(allStorageNames).isEqualTo(listOf("test"))
    }

    @Test
    fun categoriesWithItems() = runTest {
        val storageItem1 = StorageItem(1, "test1", "category", 1, 100)
        val storageItem2 = StorageItem(2, "test2", "category", 2, 200)
        val categoryItem = CategoryItem(1, "category", "tabName", 1)
        val categoryWithStorages = CategoryWithStorages(categoryItem, listOf(storageItem1, storageItem2))
        dao.add(storageItem1)
        dao.add(storageItem2)
        dao.add(categoryItem)
        val allStorageNames = dao.getCategoriesWithItemsFlow().first()
        assertThat(allStorageNames).contains(categoryWithStorages)
    }

    @Test
    fun deleteCategoryTable() = runTest {
        val categoryItem1 = CategoryItem(1, "category1", "tabName", 1)
        val categoryItem2 = CategoryItem(2, "category2", "tabName", 3)
        val categoryItem3 = CategoryItem(3, "category3", "tabName", 5)
        dao.add(categoryItem1)
        dao.add(categoryItem2)
        dao.add(categoryItem3)
        dao.deleteCategoryTable("tabName")
        val allCategoryItems = dao.getByTabFlow("tabName").first()
        assertThat(allCategoryItems).isEmpty()
    }

    @Test
    fun deleteTabTable() = runTest {
        val tabItem1 = TabItem(1, "tabName1", 1)
        val tabItem2 = TabItem(2, "tabName2", 2)
        val tabItem3 = TabItem(3, "tabName3", 3)
        dao.add(tabItem1)
        dao.add(tabItem2)
        dao.add(tabItem3)
        dao.deleteTabTable()
        val allTabItems = dao.getByTabFlow("tabName").first()
        assertThat(allTabItems).isEmpty()
    }

    @Test
    fun deleteTabFromStorages() = runTest {
        val storageItem1 = StorageItem(1, "test1", "category", 1, 100)
        val storageItem2 = StorageItem(2, "test2", "category", 2, 200)
        val categoryItem = CategoryItem(1, "category", "tabName", 1)
        val tabItem = TabItem(1, "tabName", 1)
        dao.add(storageItem1)
        dao.add(storageItem2)
        dao.add(categoryItem)
        dao.add(tabItem)
        dao.deleteTabFromStorages("tabName")
        val allStorageItems = dao.getByCategoryFlow("category").first()
        assertThat(allStorageItems).isEmpty()
    }

    @Test
    fun deleteCategoryFromStorages() = runTest {
        val storageItem1 = StorageItem(1, "test1", "category", 1, 100)
        val storageItem2 = StorageItem(2, "test2", "category", 2, 200)
        dao.add(storageItem1)
        dao.add(storageItem2)
        dao.deleteCategoryFromStorages("category")
        val allStorageItems = dao.getByCategoryFlow("category").first()
        assertThat(allStorageItems).isEmpty()
    }

    @Test
    fun deleteTabFromCategories() = runTest {
        val categoryItem1 = CategoryItem(1, "category1", "tabName", 1)
        val categoryItem2 = CategoryItem(2, "category2", "tabName", 3)
        dao.add(categoryItem1)
        dao.add(categoryItem2)
        dao.deleteTabFromCategories("tabName")
        val allCategoryItems = dao.getByTabFlow("tabName").first()
        assertThat(allCategoryItems).isEmpty()
    }
}
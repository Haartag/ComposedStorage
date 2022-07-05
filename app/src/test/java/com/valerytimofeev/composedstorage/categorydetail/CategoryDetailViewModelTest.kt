package com.valerytimofeev.composedstorage.categorydetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.StorageItem
import com.valerytimofeev.composedstorage.repositories.FakeStorageRepository
import com.valerytimofeev.composedstorage.utils.Constants
import com.valerytimofeev.composedstorage.utils.MutableStorageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CategoryDetailViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CategoryDetailViewModel

    @Before
    fun setup() {
        viewModel = CategoryDetailViewModel(FakeStorageRepository())
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `insert in db valid storageItem, get it from flow`() = runTest {
        val storageItem = MutableStorageItem(1, "name", "category", 1, 100)
        viewModel.clickedStorage = storageItem
        viewModel.addItem()
        val value = viewModel.getFlow("category").first()
        assertThat(value).isEqualTo(listOf(StorageItem(1, "name", "category", 1, 100)))
    }

    @Test
    fun `update storageItem in db`() = runTest {
        val storageItem1 = MutableStorageItem(1, "name", "category", 1, 100)
        val storageItem2 = MutableStorageItem(1, "new name", "category", 1, 500)
        viewModel.clickedStorage = storageItem1
        viewModel.addItem()
        viewModel.clickedStorage = storageItem2
        viewModel.changeItem()
        val value = viewModel.getFlow("category").first()
        assertThat(value).isEqualTo(listOf(StorageItem(1, "new name", "category", 1, 500)))
    }

    @Test
    fun `delete storageItem from db`() = runTest {
        val storageItem1 = MutableStorageItem(1, "name", "category", 1, 100)
        val storageItem2 = MutableStorageItem(1, "new name", "category", 1, 500)
        viewModel.clickedStorage = storageItem1
        viewModel.addItem()
        viewModel.clickedStorage = storageItem2
        viewModel.addItem()
        viewModel.clickedStorage = storageItem1
        viewModel.deleteItem()
        val value = viewModel.getFlow("category").first()
        assertThat(value).isEqualTo(listOf(StorageItem(1, "new name", "category", 1, 500)))
    }



    @Test
    fun `when size to decimal size and divide`() {
        val number = 1055
        val value = viewModel.sizeToDecimalSize(number)
        assertThat(value).isEqualTo("10.55")
    }

    @Test
    fun `when reset clickedStorage and place category name`() {
        val category = "test category"
        val testItem = MutableStorageItem( 0, "", "test category", 0, 0)
        viewModel.addToClickedStorage(category)
        assertThat(viewModel.clickedStorage).isEqualTo(testItem)
    }

    @Test
    fun `get decimal size divided from clickedStorage`() {
        val testItem = MutableStorageItem( 0, "", "", 0, 1055)
        viewModel.clickedStorage = testItem
        val value = viewModel.getDecimalSize()
        assertThat(value).isEqualTo("10.55")
    }

    @Test
    fun `save int size multiplied to clickedStorage`() {
        viewModel.saveIntegerSize("10.55")
        assertThat(viewModel.clickedStorage.size).isEqualTo(1055)
    }

    @Test
    fun `save item in clickedStorage`() {
        val testItem = StorageItem( 1, "name", "category", 1, 100)
        viewModel.saveClickedItem(testItem)
        val value = viewModel.clickedStorage
        val testSubject = MutableStorageItem( 1, "name", "category", 1, 100)
        assertThat(value).isEqualTo(testSubject)
    }

    @Test
    fun `less than 2 digits after dot, valid input, returns true` () {
        val input = "10.55"
        assertThat(viewModel.checkManualInput(input)).isTrue()
    }

    @Test
    fun `less than 2 digits after dot, wrong input, returns false` () {
        val input = "10.5555"
        assertThat(viewModel.checkManualInput(input)).isFalse()
    }

    @Test
    fun `less than 2 digits after dot,empty input, returns true` () {
        val input = ""
        assertThat(viewModel.checkManualInput(input)).isTrue()
    }

    @Test
    fun `manual change weight, valid input, isErrorInSize false`() {
        val input = "10.55"
        val value = viewModel.manualChangeWeight(input)
        assertThat(value).isEqualTo("10.55")
        assertThat(viewModel.isErrorInSize.value).isFalse()
    }

    @Test
    fun `manual change weight, input with wrong delimiter, isErrorInSize false`() {
        val input = "10,55"
        val value = viewModel.manualChangeWeight(input)
        assertThat(value).isEqualTo("10.55")
        assertThat(viewModel.isErrorInSize.value).isFalse()
    }

    @Test
    fun `manual change weight, wrong input last dot, isErrorInSize true`() {
        val input = "10."
        val value = viewModel.manualChangeWeight(input)
        assertThat(value).isEqualTo("10.")
        assertThat(viewModel.isErrorInSize.value).isTrue()
    }

    @Test
    fun `manual change weight, empty input, isErrorInSize false`() {
        val input = ""
        val value = viewModel.manualChangeWeight(input)
        assertThat(value).isEqualTo("")
        assertThat(viewModel.isErrorInSize.value).isTrue()
    }

    @Test
    fun `manual change weight, wrong input wrong symbol, isErrorInSize true`() {
        val input = "10.5-5"
        val value = viewModel.manualChangeWeight(input)
        assertThat(value).isEqualTo("10.5-5")
        assertThat(viewModel.isErrorInSize.value).isTrue()
    }

    @Test
    fun `button change weight, increase`() {
        val item = StorageItem( 1, "name", "category", 1, 100)
        viewModel.saveClickedItem(item)
        val value = viewModel.buttonChangeWeight(increase = true)
        val result = item.size + Constants.weightTypeToWeightChange[item.sizeType]!!
        val resultString = result
            .toBigDecimal()
            .divide(Constants.divider)
            .toString()
            .replace(',', '.')
        assertThat(value).isEqualTo(resultString)
    }

    @Test
    fun `button change weight, decrease`() {
        val item = StorageItem( 1, "name", "category", 1, 100)
        viewModel.saveClickedItem(item)
        val value = viewModel.buttonChangeWeight(increase = false)
        val result = item.size - Constants.weightTypeToWeightChange[item.sizeType]!!
        val resultString = result
            .toBigDecimal()
            .divide(Constants.divider)
            .toString()
            .replace(',', '.')
        assertThat(value).isEqualTo(resultString)
    }

    @Test
    fun `button change weight, decrease below 0 returns 0`() {
        val item = StorageItem( 1, "name", "category", 1, 0)
        viewModel.saveClickedItem(item)
        val value = viewModel.buttonChangeWeight(increase = false)
        assertThat(value).isEqualTo("0")
    }

    @Test
    fun `button change weight, increase to the nearest multiple`() {
        val item = StorageItem( 1, "name", "category", 1, 103)
        viewModel.saveClickedItem(item)
        val value = viewModel.buttonChangeWeight(increase = true)
        val result = 100 + Constants.weightTypeToWeightChange[item.sizeType]!!
        val resultString = result
            .toBigDecimal()
            .divide(Constants.divider)
            .toString()
            .replace(',', '.')
        assertThat(value).isEqualTo(resultString)
    }

    @Test
    fun `button change weight, decrease to the nearest multiple`() {
        val item = StorageItem( 1, "name", "category", 1, 103)
        viewModel.saveClickedItem(item)
        val value = viewModel.buttonChangeWeight(increase = false)
        val result = 100
        val resultString = result
            .toBigDecimal()
            .divide(Constants.divider)
            .toString()
            .replace(',', '.')
        assertThat(value).isEqualTo(resultString)
    }



    @Test
    fun `get mid picker index of initial item`() {
        val value = viewModel.getMidIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices[0])
    }

    @Test
    fun `get left picker index of initial item`() {
        val value = viewModel.getLeftIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices.last())
    }

    @Test
    fun `get right picker index of initial item`() {
        val value = viewModel.getRightIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices[1])
    }

    @Test
    fun `get mid picker index of initial item after increase`() {
        viewModel.indexIncrease()
        val value = viewModel.getMidIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices[1])
    }

    @Test
    fun `get mid picker index of initial item after full round increase`() {
        repeat(Constants.sizeTypeIndices.size) {
            viewModel.indexIncrease()
        }
        val value = viewModel.getMidIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices[0])
    }

    @Test
    fun `get mid picker index of initial item after decrease`() {
        viewModel.indexDecrease()
        val value = viewModel.getMidIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices.last())
    }

    @Test
    fun `get mid picker index of initial item after full round decrease`() {
        repeat(Constants.sizeTypeIndices.size) {
            viewModel.indexDecrease()
        }
        val value = viewModel.getMidIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices[0])
    }

    @Test
    fun `set picker index after saveClickedIndex`() {
        val item = StorageItem( 1, "name", "category", 2, 100)
        viewModel.saveClickedItem(item)
        val value = viewModel.getMidIndex()
        assertThat(value).isEqualTo(Constants.sizeTypeIndices[2])
    }
}
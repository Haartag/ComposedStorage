package com.valerytimofeev.composedstorage.categorydetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.local.StorageItem
import com.valerytimofeev.composedstorage.repositories.DefaultStorageRepository
import com.valerytimofeev.composedstorage.utils.Constants
import com.valerytimofeev.composedstorage.utils.Constants.divider
import com.valerytimofeev.composedstorage.utils.Constants.pickerText
import com.valerytimofeev.composedstorage.utils.Constants.sizeTypeIndices
import com.valerytimofeev.composedstorage.utils.MutableStorageItem
import com.valerytimofeev.composedstorage.utils.makeStorageItemImmutable
import com.valerytimofeev.composedstorage.utils.makeStorageItemMutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: DefaultStorageRepository
) : ViewModel() {
    //List
    val currentCategory = mutableStateOf("")

    //Dialog
    val openChangeOrAddNewDialog = mutableStateOf(false)
    val isChangeDialog = mutableStateOf(false)
    var clickedStorage = MutableStorageItem(0, "", "", 0, 0)
    val isErrorInSize = mutableStateOf(false)

    //Work with dialog

    //prepare ClickedStorage for add new item
    fun addToClickedStorage(category: String) {
        clickedStorage = MutableStorageItem(0, "", category, 0, 0)
        resetPickerIndex()
    }

    fun sizeToDecimalSize(number: Int): String = (number.toBigDecimal().divide(divider)).toString()

    fun getDecimalSize(): String = clickedStorage.size.toBigDecimal().divide(divider).toString()

    fun saveIntegerSize(sizeInString: String) {
        clickedStorage.size = sizeInString.toBigDecimal().multiply(divider).toInt()
    }

    fun saveClickedItem(item: StorageItem) {
        clickedStorage = makeStorageItemMutable(item)
        resetPickerIndex()
    }

    //check if not more than 2 digits after dot
    fun checkManualInput(input: String): Boolean {
        return if (input.isNotEmpty()) {
            input.takeLastWhile { it.isDigit() }.length <= 2
        } else {
            true
        }
    }

    fun manualChangeWeight(input: String): String {
        val formattedInput = input.replace(',', '.')
        val resultString = try {
            isErrorInSize.value = false
            if (formattedInput.lastOrNull() == '.' || formattedInput.isEmpty() || !formattedInput[0].isDigit()) throw Exception(
                "input error"
            )
            if (formattedInput.contains('.')) {
                formattedInput.toBigDecimal()
                    .setScale(
                        formattedInput.takeLastWhile { it.isDigit() }.length,
                        RoundingMode.DOWN
                    )
                    .toString()
            } else {
                formattedInput
            }
        } catch (e: Exception) {
            isErrorInSize.value = true
            formattedInput
        }
        return resultString
    }

    fun buttonChangeWeight(increase: Boolean): String {
        val step = Constants.weightTypeToWeightChange.getValue(clickedStorage.sizeType)
        val remainder = clickedStorage.size % step

        if (remainder != 0) {
            if (increase) {
                clickedStorage.size += step - remainder
            } else {
                clickedStorage.size -= remainder
            }
        } else {
            if (increase) clickedStorage.size += step else clickedStorage.size -= step
        }
        if (clickedStorage.size < 0) {
            clickedStorage.size = 0
            return "0"
        }
        return clickedStorage.size.toBigDecimal().divide(divider).toString()
    }

    //Work with database

    fun getFlow(categoryName: String): Flow<List<StorageItem>> {
        return repository.getItemsByCategoryFlow(categoryName)
    }

    fun addItem() {
        viewModelScope.launch {
            repository.insertNewItem(storageItem = makeStorageItemImmutable(clickedStorage))
        }
    }

    fun changeItem() {
        viewModelScope.launch {
            repository.updateItem(item = makeStorageItemImmutable(clickedStorage))
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            repository.deleteItem(item = makeStorageItemImmutable(clickedStorage))
        }
    }

    //Horizontal Picker

    private val pickerIndex = mutableStateOf(getPickerIndex(clickedStorage.sizeType))

    private fun getPickerIndex(sizeType: Int): Int {
        return sizeTypeIndices.indexOf(sizeType)
    }

    private fun getSizeType(): Int = sizeTypeIndices[pickerIndex.value]

    fun indexIncrease() {
        if (pickerIndex.value < pickerText.lastIndex) pickerIndex.value++ else pickerIndex.value = 0
        clickedStorage.sizeType = getSizeType()
    }

    fun indexDecrease() {
        if (pickerIndex.value > 0) pickerIndex.value-- else pickerIndex.value = pickerText.lastIndex
        clickedStorage.sizeType = getSizeType()
    }

    fun getMidIndex(): Int {
        return pickerIndex.value
    }

    fun getLeftIndex(): Int {
        return if (pickerIndex.value > 0) pickerIndex.value - 1 else pickerText.lastIndex
    }

    fun getRightIndex(): Int {
        return if (pickerIndex.value < pickerText.lastIndex) pickerIndex.value + 1 else 0
    }

    private fun resetPickerIndex() {
        pickerIndex.value = getPickerIndex(clickedStorage.sizeType)
    }
}



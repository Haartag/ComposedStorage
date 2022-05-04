package com.valerytimofeev.composedstorage.categorydetail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.StorageItem
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    //List
    private val currentCategory = mutableStateOf("")
    val storageList = mutableStateListOf<StorageItem>()

    //Dialog
    val openChangeDialog = mutableStateOf(false)
    val isChangeDialog = mutableStateOf(false)
    var clickedStorage = StorageItem(0, "", "", "", 0)
    var clickedIndex = -1
    val isErrorInSize = mutableStateOf(false)

    //Divider for Int / BigDecimal conversion
    private val divider = BigDecimal(100)

    fun getStorageListSize(): Int {
        return storageList.size
    }

    //Work with dialog

    //prepare ClickedStorage for add new item
    fun addToClickedStorage(category: String) {
        clickedStorage = StorageItem(0, "", category, "кг.", 0)
    }

    fun sizeToDecimalSize(number: Int): String {
        return (number.toBigDecimal().divide(divider)).toString()
    }

    fun getDecimalSize(): String {
        return clickedStorage.size.toBigDecimal().divide(divider).toString()
    }

    fun saveIntegerSize(sizeInString: String) {
        clickedStorage.size = sizeInString.toBigDecimal().multiply(divider).toInt()
    }

    fun saveClickedStorage(itemIndex: Int) {
        clickedStorage = storageList[itemIndex].copy()
        clickedIndex = itemIndex
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

    fun loadItemListForCategory(category: String) {
        currentCategory.value = category
        if (storageList.isEmpty()) {
            viewModelScope.launch {
                repository.getItemsByCategory(category = currentCategory.value).forEach {
                    storageList.add(it)
                }
            }
        }
    }

    fun addItem() {
        viewModelScope.launch {
            repository.insertNewItem(storageItem = clickedStorage)
        }
        storageList.add(clickedStorage)
    }

    fun changeItem() {
        viewModelScope.launch {
            repository.updateItem(item = clickedStorage)
        }
        storageList[clickedIndex] = clickedStorage
    }

    fun deleteItem() {
        viewModelScope.launch {
            storageList.remove(clickedStorage)
            repository.deleteItem(item = clickedStorage)
        }
    }


    //Horizontal Picker TODO some refactor

    val width = 175.dp
    val swipeLimiter = 100.dp
    val pickerText = listOf("Кг", "Шт", "Литр")
    val pickerIndex = mutableStateOf(setPickerIndex(clickedStorage.sizeType))

    private fun setPickerIndex(sizeType: String): Int {
        return when (sizeType) {
            "кг." -> 0
            "шт." -> 1
            "л." -> 2
            else -> 0
        }
    }

    private fun getSizeType(): String {
        return when (pickerIndex.value) {
            0 -> "кг."
            1 -> "шт."
            2 -> "л."
            else -> "кг."
        }
    }

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
        pickerIndex.value = setPickerIndex(clickedStorage.sizeType)
    }


}
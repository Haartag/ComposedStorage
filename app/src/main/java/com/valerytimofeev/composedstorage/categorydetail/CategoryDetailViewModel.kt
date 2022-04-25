package com.valerytimofeev.composedstorage.categorydetail

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.StorageItem
import com.valerytimofeev.composedstorage.utils.DialogInputData
import com.valerytimofeev.composedstorage.utils.DialogOutputData
import com.valerytimofeev.composedstorage.utils.HorizontalPickerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    private val currentCategory = mutableStateOf("")

    val storageList = mutableStateListOf<StorageItem>()

    val openChangeDialog = mutableStateOf(false)

    fun getStorageListSize(): Int {
        return storageList.size
    }

    fun roundSize(number: Float): String {
        val format = "%.2f"
        return String.format(format, number)
    }

    var clickedStorage = StorageItem(1, "", "", "", 0f)
    var clickedIndex = -1

    fun saveClickedStorage(itemIndex: Int) {
        clickedStorage = storageList[itemIndex].copy()
        clickedIndex = itemIndex
        resetPickerIndex()
    }

    //Database to ViewModel
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

    fun changeWeight(increase: Boolean): Float {
        if (increase) clickedStorage.size += 0.05f else clickedStorage.size -= 0.05f
        return clickedStorage.size
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


    //Horizontal Picker
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
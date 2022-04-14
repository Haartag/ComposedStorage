package com.valerytimofeev.composedstorage.categorydetail

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.valerytimofeev.composedstorage.data.database.StorageItem
import com.valerytimofeev.composedstorage.utils.DialogInputData
import com.valerytimofeev.composedstorage.utils.DialogOutputData

class CategoryDetailViewModel: ViewModel() {

    val storageList = mutableStateListOf<StorageItem>()

    val openChangeDialog = mutableStateOf(false)

    init {
        storageList += StorageItem(1, "Свинина", "Мясо", "кг.", 2.35f)
        storageList += StorageItem(2, "Курица", "Мясо", "кг.", 2.0f)
        storageList += StorageItem(3, "Баранина", "Мясо", "кг.", 0.8f)
        storageList += StorageItem(4, "Говядина", "Мясо", "кг.", 0.5f)
        storageList += StorageItem(5, "Фарш куриный", "Мясо", "кг.", 0.4f)
        storageList += StorageItem(6, "Фарш свиной", "Мясо", "кг.", 3.2f)
        storageList += StorageItem(7, "Тестовое мясо", "Мясо", "кг.", 16.65f)
    }

    fun getStorageListSize(): Int {
        return storageList.size
    }

    fun getStorageNames(): List<String> {
        return storageList.map { it.name }
    }

    fun getStorageSizes(): List<String> {
        return storageList.map { it.size.toString() }
    }

    fun getStorageSizeType(): List<String> {
        return storageList.map { it.sizeType }
    }

    val clickedStorage = mutableStateOf(DialogInputData("", "", ""))

    fun saveClickedStorage(name: String, size: String, sizeType: String) {
        clickedStorage.value = DialogInputData(name, size, sizeType)
    }

    fun loadClickedStorage(): DialogOutputData {
        return DialogOutputData(
            name = listOf(clickedStorage.value.name),
            size = listOf(clickedStorage.value.size),
            sizeType = listOf(clickedStorage.value.sizeType),
        )
    }

}
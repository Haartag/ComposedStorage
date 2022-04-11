package com.valerytimofeev.composedstorage.categorydetail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.valerytimofeev.composedstorage.data.database.StorageItem

class CategoryDetailViewModel: ViewModel() {

    val storageList = mutableStateListOf<StorageItem>()

    init {
        storageList += StorageItem(1, "Свинина", "Мясо", "кг.", 2.35f)
        storageList += StorageItem(2, "Курица", "Мясо", "кг.", 2.0f)
        storageList += StorageItem(3, "Баранина", "Мясо", "кг.", 0.8f)
        storageList += StorageItem(4, "Говядина", "Мясо", "кг.", 0.5f)
        storageList += StorageItem(5, "Фарш куриный", "Мясо", "кг.", 0.4f)
        storageList += StorageItem(6, "Фарш свиной", "Мясо", "кг.", 3.2f)
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

}
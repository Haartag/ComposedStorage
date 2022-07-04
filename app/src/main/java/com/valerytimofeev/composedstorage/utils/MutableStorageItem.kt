package com.valerytimofeev.composedstorage.utils

import com.valerytimofeev.composedstorage.data.local.StorageItem

data class MutableStorageItem(
    var id: Int,
    var name: String,
    var category: String,
    var sizeType: Int,
    var size: Int
)

fun makeStorageItemMutable(item: StorageItem): MutableStorageItem = MutableStorageItem(id = item.id, name = item.name, category = item.category, sizeType = item.sizeType, size = item.size)

fun makeStorageItemImmutable(item: MutableStorageItem): StorageItem = StorageItem(id = item.id, name = item.name, category = item.category, sizeType = item.sizeType, size = item.size)




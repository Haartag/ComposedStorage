package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewCategoryViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    //val colorScheme = mutableStateOf(0)
    val dropDownExpanded = mutableStateOf(false)
    val selectedIndex = mutableStateOf(0)
    val tabNames = mutableListOf<TabItem>()
    var tabsLoaded = mutableStateOf(false)



    init {
        viewModelScope.launch {
            //repository.getTabs().forEach { tabNames.add(it) }
            tabsLoaded.value = true
        }
    }

    fun getColorByIndex(): Color {
        return Constants.colorsMap[tabNames[selectedIndex.value].colorScheme] ?: Color.LightGray
    }

    fun addCategory() {
        viewModelScope.launch {
            repository.insertNewCategory(CategoryItem(0, "", "", 0))
        }
    }
}

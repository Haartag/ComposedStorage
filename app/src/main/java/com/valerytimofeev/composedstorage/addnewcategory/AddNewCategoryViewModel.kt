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

    val colorScheme = mutableStateOf(0)
    val tabNames = mutableStateListOf<TabItem>()

    fun getColorByIndex(index: Int): Color {
        return Constants.colorsMap[index] ?: Color.LightGray
    }

    fun addCategory() {
        viewModelScope.launch {
            repository.insertNewCategory(CategoryItem(0,"", "", 0))
        }
    }
}

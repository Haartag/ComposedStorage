package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.CategoryItem
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class AddNewCategoryViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    val colorScheme = mutableStateOf(1)
    val dropDownExpanded = mutableStateOf(false)
    val selectedTabIndex = mutableStateOf(0)
    var selectedTabName = ""

    val categoryName = mutableStateOf("")

    val focusRequester = FocusRequester()
    val isInputError = mutableStateOf(false)

    val buttonSelected = mutableStateOf(0)

    fun getTabFlow(): Flow<List<TabItem>> {
        return repository.getTabsFlow()
    }

    fun getImgPickerRows(): Int {
        return ceil(Constants.imgMap.size / 4.0).toInt()
    }

    fun getOneImgRow(rowIndex: Int): List<Int> {
        return Constants.imgMap.values.drop(rowIndex * 4).take(4)
    }

    fun imgPickerOutline(index: Int): Color {
        return if (index == buttonSelected.value) Color.Yellow else Color.DarkGray
    }

    fun getCategoryTypeColor(colorScheme: Int): Color {
        return Constants.colorsMap[colorScheme] ?: Color.LightGray
    }

    fun getCategoryImg(img: Int): Int {
        return Constants.imgMap[img] ?: R.drawable.placeholder50
    }

    fun getIndexByImg(img: Int): Int {
        return Constants.imgMap.filterValues { it == img }.keys.elementAt(0)
    }

    fun addCategory() {
        viewModelScope.launch {
            repository.insertNewCategory(
                CategoryItem(
                    0,
                    categoryName.value,
                    selectedTabName,
                    buttonSelected.value
                )
            )
        }
    }
}

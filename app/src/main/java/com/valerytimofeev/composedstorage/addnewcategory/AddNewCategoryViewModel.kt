package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.local.CategoryItem
import com.valerytimofeev.composedstorage.data.local.TabItem
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    val tabItemPlaceholder = TabItem(0, "", 2)

    val categoryName = mutableStateOf("")

    val focusRequester = FocusRequester()

    //if wrong data in textField
    val isInputError = mutableStateOf(false)

    val buttonSelected = mutableStateOf(0)

    //current set of img tiles
    val setSelected = mutableStateOf(0)


    val leftButtonClickable = mutableStateOf(false)
    val rightButtonClickable = mutableStateOf(true)


    fun getTabFlow(): Flow<List<TabItem>> {
        return repository.getTabsFlow()
    }

    init {
        viewModelScope.launch {
            getTabFlow().collect { selectedTabName = it[0].tabName }
        }
    }

    //calculate number of rows
    fun getImgPickerRows(): Int {
        return ceil(Constants.imgMap.size / 4.0).toInt()
    }

    fun getOneImgRow(rowIndex: Int): List<Int> {
        return Constants.imgMap.values.drop((setSelected.value * 3 + rowIndex) * 4).take(4)
    }

    fun imgPickerOutline(index: Int): Color {
        return if (index == buttonSelected.value) Color.Yellow else Color.DarkGray
    }

    fun getCategoryTypeColor(colorScheme: Int): Color {
        return Constants.colorsMap[colorScheme] ?: Color.LightGray
    }

    fun getCategoryImg(img: Int): Int {
        return Constants.imgMap[img] ?: R.drawable.placeholder_white
    }

    fun getIndexByImg(img: Int): Int {
        return Constants.imgMap.filterValues { it == img }.keys.elementAt(0)
    }

    fun setButtons() {
        leftButtonClickable.value = setSelected.value > 0
        rightButtonClickable.value = setSelected.value < getImgPickerRows() / 3
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

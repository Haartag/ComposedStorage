package com.valerytimofeev.composedstorage.addnewtab

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil


@HiltViewModel
class AddNewTabViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    val tabNameText = mutableStateOf("")
    val buttonSelected = mutableStateOf(1)

    val isInputError = mutableStateOf(false)

    val focusRequester = FocusRequester()

    //calculate number of rows
    fun getColorPickerRows(): Int {
        return ceil(Constants.colorsMap.size / 5.0).toInt()
    }

    fun getOneColorRow(rowIndex: Int): List<Color> {
        return Constants.colorsMap.values.drop(rowIndex * 5).take(5)
    }

    fun colorPickerOutline(index: Int): Color {
        return if (index == buttonSelected.value) Color.Yellow else Color.DarkGray
    }

    fun getColorByIndex(index: Int): Color {
        return Constants.colorsMap[index] ?: Color.LightGray
    }

    fun getIndexByColor(color: Color): Int {
        return Constants.colorsMap.filterValues { it == color}.keys.elementAt(0)
    }

    fun addTab() {
        viewModelScope.launch {
            repository.insertNewTab(TabItem(0, tabNameText.value, buttonSelected.value))
        }
    }



}
package com.valerytimofeev.composedstorage.addnewtab

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.valerytimofeev.composedstorage.data.DatabaseRepository
import com.valerytimofeev.composedstorage.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.roundToInt


@HiltViewModel
class AddNewTabViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    fun getColorPickerRows(): Int {
        return ceil(Constants.colorsMap.size / 5.0).toInt()
    }

    fun getOneColorRow(rowIndex: Int): List<Color> {
        return Constants.colorsMap.values.drop(rowIndex * 5).take(5)
    }

    fun colorPickerOutline(index: Int): Color {
        return if (index == buttonSelected.value) Color.Yellow else Color.Black
    }

    fun getIndexByColor(color: Color): Int {
        return Constants.colorsMap.filterValues { it == color}.keys.elementAt(0)
    }

    val buttonSelected = mutableStateOf(1)

}
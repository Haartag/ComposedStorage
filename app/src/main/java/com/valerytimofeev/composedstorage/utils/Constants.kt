package com.valerytimofeev.composedstorage.utils

import androidx.compose.ui.unit.dp
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.ui.theme.*
import java.math.BigDecimal

object Constants {

    val weightTypeToWeightChange = mapOf(0 to 5, 1 to 25, 2 to 10)

    val colorsMap = mapOf(
        1 to Theme1Color,
        2 to Theme2Color,
        3 to Theme3Color,
        4 to Theme4Color,
        5 to Theme5Color,
        6 to Theme6Color,
        7 to Theme7Color,
        8 to Theme8Color,
        9 to Theme9Color,
        10 to Theme10Color,
    )

    val imgMap = mapOf(
        0 to R.drawable.placeholder50,
        1 to R.drawable.canned50,
        2 to R.drawable.fish50,
        3 to R.drawable.meat50,
        4 to R.drawable.dumplings50,
        5 to R.drawable.croissant50,
        6 to R.drawable.vegetables50,
        7 to R.drawable.noodles50,
        8 to R.drawable.wine50,
    )

    val sizeTypeMap = mapOf(
        0 to R.string.size_1,
        1 to R.string.size_2,
        2 to R.string.size_3,
    )
    val sizeTypeIndices = sizeTypeMap.map { it.key }
    val pickerText = sizeTypeMap.map { it.value }

    //HorizontalPicker constants
    val horizontalPickerWidth = 175.dp
    val horizontalPickerSwipeLimiter = 100.dp

    //Divider for Int / BigDecimal conversion
    val divider = BigDecimal(100)
}
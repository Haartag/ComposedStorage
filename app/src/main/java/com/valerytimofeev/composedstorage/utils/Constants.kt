package com.valerytimofeev.composedstorage.utils

import androidx.compose.ui.unit.dp
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.ui.theme.*

object Constants {

    val weightTypeToWeightChange = mapOf("кг." to 5, "шт." to 25, "л." to 10)

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
        0 to R.drawable.placeholder,
        1 to R.drawable.canned50,
        2 to R.drawable.fish50,
        3 to R.drawable.meat50,
        4 to R.drawable.dumplings50
    )

    val sizeTypes = listOf("кг.", "шт.", "л.")


    val pickerText = listOf("Кг", "Шт", "Литр")

    val horizontalPickerWidth = 175.dp
    val horizontalPickerSwipeLimiter = 100.dp
}
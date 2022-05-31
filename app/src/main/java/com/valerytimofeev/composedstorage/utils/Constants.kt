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
        //set 1
        0 to R.drawable.placeholder_white,
        1 to R.drawable.fennel,
        2 to R.drawable.broccoli,
        3 to R.drawable.tomato,

        4 to R.drawable.vegetables,
        5 to R.drawable.onion_1,
        6 to R.drawable.onion_2,
        7 to R.drawable.potato,

        8 to R.drawable.frozen_food,
        9 to R.drawable.pea,
        10 to R.drawable.vegetable_juice,
        11 to R.drawable.green_juice,

        //set 2
        12 to R.drawable.mushrooms_1,
        13 to R.drawable.mushrooms_2,
        14 to R.drawable.spinach,
        15 to R.drawable.parsely,

        16 to R.drawable.fruits_1,
        17 to R.drawable.oranges,
        18 to R.drawable.fruits_2,
        19 to R.drawable.strawberry,

        20 to R.drawable.blueberries,
        21 to R.drawable.berries,
        22 to R.drawable.chili,
        23 to R.drawable.spices,

        //set 3
        24 to R.drawable.bread_1,
        25 to R.drawable.bread_2,
        26 to R.drawable.croissant,
        27 to R.drawable.donut,

        28 to R.drawable.cupcake,
        29 to R.drawable.baked,
        30 to R.drawable.noodles_1,
        31 to R.drawable.noodles_2,

        32 to R.drawable.nuts_1,
        33 to R.drawable.nuts_2,
        34 to R.drawable.nuts_3,
        35 to R.drawable.nuts_4,

        //set 4
        36 to R.drawable.dumplings,
        37 to R.drawable.meatballs,
        38 to R.drawable.bbq,
        39 to R.drawable.lambsteak,

        40 to R.drawable.meat_1,
        41 to R.drawable.meat_2,
        42 to R.drawable.meat_3,
        43 to R.drawable.meat_4,

        44 to R.drawable.meat_5,
        45 to R.drawable.meat_6,
        46 to R.drawable.fish_1,
        47 to R.drawable.fish_2,

        //set 5
        48 to R.drawable.canfish,
        49 to R.drawable.can_1,
        50 to R.drawable.can_2,
        51 to R.drawable.cancorn,

        52 to R.drawable.pickled_cucumbers,
        53 to R.drawable.pickled_peppers,
        54 to R.drawable.pickled_vegetables_1,
        55 to R.drawable.pickled_vegetables_2,

        56 to R.drawable.pickled_vegetables_3,
        57 to R.drawable.pickled_vegetables_4,
        58 to R.drawable.mason_jars,
        59 to R.drawable.glass_bottles,

        //set 6
        60 to R.drawable.chocolate,
        61 to R.drawable.fudge,
        62 to R.drawable.haribo,
        63 to R.drawable.candy_1,

        64 to R.drawable.candy_2,
        65 to R.drawable.candy_3,
        66 to R.drawable.candy_4,
        67 to R.drawable.candy_5,

        68 to R.drawable.icecream_1,
        69 to R.drawable.icecream_2,
        70 to R.drawable.currant_popsicles,
        71 to R.drawable.macaroon,

        //set 7
        72 to R.drawable.coffee_beans,
        73 to R.drawable.peppermint,
        74 to R.drawable.tea_1,
        75 to R.drawable.tea_2,

        76 to R.drawable.tea_3,
        77 to R.drawable.tea_4,
        78 to R.drawable.beer_bottle,
        79 to R.drawable.wine_1,

        80 to R.drawable.wine_2,
        81 to R.drawable.wine_3,
        82 to R.drawable.wine_4,
        83 to R.drawable.alcohol,

        //set 8
        84 to R.drawable.brushes,
        85 to R.drawable.button,
        86 to R.drawable.cotton,
        87 to R.drawable.pins,

        88 to R.drawable.pruning_shears,
        89 to R.drawable.thread,
        90 to R.drawable.wool,
        91 to R.drawable.watch,

        92 to R.drawable.potter,
        93 to R.drawable.sequins_1,
        94 to R.drawable.sequins_2,
        95 to R.drawable.sequins_3,

        //set 9
        96 to R.drawable.container,
        97 to R.drawable.nails,
        98 to R.drawable.screws,
        99 to R.drawable.spanner,

        100 to R.drawable.tools_1,
        101 to R.drawable.tools_2,
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
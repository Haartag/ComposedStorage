package com.valerytimofeev.composedstorage.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*private val DarkColorPalette = darkColors(
    primary = LightBlue,
    background = Violet,
    onBackground = Mint,
    surface = Blue,
    onSurface = Mint
)*/

/*private val LightColorPalette = lightColors(
    primary = Blue,
    background = Color.White,
    onBackground = Color.Black,
    surface = BackgroundLight,
    onSurface = Color.Black
)*/

private val LightColorPalette = lightColors(
    primary = altBlue,
    onPrimary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Background,
    onSurface = Color.Black
)

@Composable
fun ComposedStorageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
/*    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }*/

    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
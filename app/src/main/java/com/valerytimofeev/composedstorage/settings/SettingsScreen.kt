package com.valerytimofeev.composedstorage.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.TopBar

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = stringResource(R.string.settings_title),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
        )
        TabSettings(navController = navController)
        Divider()
        CategorySettings(navController = navController)
        Divider()

    }
}

@Composable
fun TabSettings(navController: NavController) {
    SettingsBaseItem(
        mainText = stringResource(R.string.tab_settings),
        subText = stringResource(R.string.tab_settings_hint),
        onClick = { navController.navigate("tab_settings") })
}

@Composable
fun CategorySettings(navController: NavController) {
    SettingsBaseItem(
        mainText = stringResource(R.string.category_settings),
        subText = stringResource(R.string.category_settings_hint),
        onClick = { navController.navigate("category_settings_tab_chooser") })
}

@Composable
fun SettingsBaseItem(
    mainText: String,
    subText: String = "",
    onClick: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxWidth()
            .requiredHeight(80.dp)
            .clickable { onClick() }
    ) {
        val haveSubText = subText.isNotEmpty()
        val additionalPadding = if (haveSubText) 0.dp else 12.dp
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = (additionalPadding + 16.dp)),
            text = mainText,
            fontSize = 20.sp,
            maxLines = 1,

        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 44.dp),
            text = subText,
            color = Color.Gray,
            fontSize = 14.sp,
        )

    }
}
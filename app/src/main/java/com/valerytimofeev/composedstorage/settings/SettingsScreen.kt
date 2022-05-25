package com.valerytimofeev.composedstorage.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.common.TopBar

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "Settings",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
        )
        TabSettings(navController = navController)
        Divider()

        Divider()

    }
}

@Composable
fun TabSettings(navController: NavController) {
    SettingsBaseItem(mainText = "Tab settings", subText = "remove or reorder", onClick = {navController.navigate("tab_settings")})
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
        //.border(width = 1.dp, shape = RoundedCornerShape(2.dp), color = Color.LightGray)
    ) {
        val haveSubText = subText.isNotEmpty()
        val additionalPadding = if (haveSubText) 0.dp else 12.dp
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = (additionalPadding + 16.dp)),
            text = mainText,
            fontSize = 20.sp
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
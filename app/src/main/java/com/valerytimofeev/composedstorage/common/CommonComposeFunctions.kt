package com.valerytimofeev.composedstorage.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.valerytimofeev.composedstorage.ui.theme.Mint


@Composable
fun TopBar(
    title: String = "",
    buttonIcon: ImageVector,
    onButtonClicked: () -> Unit,
    additionalInfo: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        actions = {
            additionalInfo()
        },
        backgroundColor = Mint
    )
}
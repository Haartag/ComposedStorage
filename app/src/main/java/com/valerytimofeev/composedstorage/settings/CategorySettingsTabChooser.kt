package com.valerytimofeev.composedstorage.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.common.TopBarOkIcon

@Composable
fun CategorySettingsTabChooser(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "Category settings",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },

            )

        val tabFlow = viewModel.getTabFlow().collectAsState(initial = emptyList())
        tabFlow.value.forEach { tabItem ->
            val categoryFlow =
                viewModel.getCategoryFlow(tabItem.tabName).collectAsState(initial = emptyList())
            SettingsBaseItem(
                mainText = tabItem.tabName,
                subText = categoryFlow.value.joinToString(separator = ", ") { it.category },
                onClick = { navController.navigate("category_settings/${tabItem.tabName}") })
            Divider()
        }
    }
}
package com.valerytimofeev.composedstorage.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.TopBar

@Composable
fun AboutLicensesScreen(
    navController: NavController
) {
    Column {
        TopBar(
            title = stringResource(R.string.about_licenses_title),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
        )
        LibrariesContainer(
            Modifier.fillMaxSize()
        )
    }
}
package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.common.TopBar

@Composable
fun AddNewCategoryScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(
                title = "Add new category",
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() }
            )
            Text(text = "DD NEW category HERE!")
        }
    }
}

@Composable
fun CategoryName() {
    
}
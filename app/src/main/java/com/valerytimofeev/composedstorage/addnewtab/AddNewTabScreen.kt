package com.valerytimofeev.composedstorage.addnewtab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.categorydetail.CategoryDetailViewModel
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.utils.Constants

@Composable
fun AddNewTabScreen(
    navController: NavController,
    viewModel: AddNewTabViewModel = hiltViewModel(),
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
                title = "Add new tab",
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() }
            )
            ColorPicker()
        }
    }
}

@Composable
fun TabName() {

}

@Composable
fun ColorPicker(
    viewModel: AddNewTabViewModel = hiltViewModel(),
) {
    Column() {
        repeat(viewModel.getColorPickerRows()) { row ->
            Spacer(modifier = Modifier.height(1.dp))
            Row() {
                viewModel.getOneColorRow(row).forEach {
                    Spacer(modifier = Modifier.width(1.dp))
                    ColorBox(color = it, index = viewModel.getIndexByColor(it))
                }
            }
        }
    }
}

@Composable
fun ColorBox(
    index: Int,
    color: Color,
    viewModel: AddNewTabViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(color = viewModel.colorPickerOutline(index))
            .requiredHeight(42.dp)
            .aspectRatio(1f)
            .clickable {
                viewModel.buttonSelected.value = index
            }
    ) {
        Box(
            modifier = Modifier
                .background(color = color)
                .requiredHeight(40.dp)
                .aspectRatio(1f)
                .offset(x = (-1).dp, y = (-1).dp)

        )
    }
}
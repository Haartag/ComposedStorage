package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.addnewtab.AddNewTabViewModel
import com.valerytimofeev.composedstorage.common.TabNameBackground
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.common.TopBarOkIcon

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
                onButtonClicked = { navController.popBackStack() },
                additionalInfo = {
                    TopBarOkIcon(onClick = {/*TODO*/})
                }
            )
            TabNameChooser()
            CategoryTilePreview()
            CategoryNameInput()
            ImgPicker()
        }
    }
}

@Composable
fun TabNameChooser(
    viewModel: AddNewCategoryViewModel = hiltViewModel(),
) {
    Box(contentAlignment = Alignment.Center) {
        TabNameBackground(color = viewModel.getColorByIndex(viewModel.colorScheme.value))
        Text(
            text = "Category",//viewModel.tabNameText.value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(horizontal = 50.dp)
        )
    }
}

@Composable
fun CategoryTilePreview() {

}

@Composable
fun CategoryNameInput() {
    
}

@Composable
fun ImgPicker() {

}
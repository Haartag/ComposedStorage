package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowRight
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
                    TopBarOkIcon(onClick = {/*TODO*/ })
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
        TabNameBackground(color = viewModel.getColorByIndex())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 50.dp)
        ) {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopCenter)) {
                if (viewModel.tabsLoaded.value) {
                    DropdownItem(
                        text = viewModel.tabNames[viewModel.selectedIndex.value].tabName,
                        icon = {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Drop down",
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 50.dp)
                            .clickable {
                            viewModel.dropDownExpanded.value = true
                        })
                    DropdownMenu(
                        expanded = viewModel.dropDownExpanded.value,
                        onDismissRequest = { viewModel.dropDownExpanded.value = false }
                    ) {
                        viewModel.tabNames.forEachIndexed { index, item ->
                            DropdownMenuItem(onClick = {
                                viewModel.selectedIndex.value = index
                                viewModel.dropDownExpanded.value = false
                            }) {
                                DropdownItem(text = item.tabName)
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun DropdownItem(
    modifier: Modifier = Modifier,
    text: String,
    icon: @Composable () -> Unit = {}
) {
    Row(modifier = modifier) {
        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
        )
        icon()
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
package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.composedstorage.addnewtab.ColorBox
import com.valerytimofeev.composedstorage.common.CategoryEntry
import com.valerytimofeev.composedstorage.common.TabNameBackground
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.common.TopBarOkIcon
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.Constants

@Composable
fun AddNewCategoryScreen(
    viewModel: AddNewCategoryViewModel = hiltViewModel(),
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
                    TopBarOkIcon(onClick = {
                        if (viewModel.categoryName.value.isNotEmpty()) {
                            viewModel.addCategory()
                            navController.navigate("category_list_screen")
                        } else {
                            viewModel.isInputError.value = true
                            viewModel.focusRequester.requestFocus()
                        }
                     })
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
    val tabDataFlow = viewModel.getTabFlow().collectAsState(initial = emptyList())

    Box(contentAlignment = Alignment.Center) {
        if (tabDataFlow.value.isNotEmpty()) {
            TabNameBackground(color = viewModel.getCategoryTypeColor(tabDataFlow.value[viewModel.selectedTabIndex.value].colorScheme))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 50.dp)
        ) {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopCenter)) {
                if (tabDataFlow.value.isNotEmpty()) {
                    DropdownItem(
                        text = tabDataFlow.value[viewModel.selectedTabIndex.value].tabName,
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
                        tabDataFlow.value.forEachIndexed { index, item ->
                            DropdownMenuItem(onClick = {
                                viewModel.colorScheme.value = item.colorScheme
                                viewModel.selectedTabIndex.value = index
                                viewModel.selectedTabName = item.tabName
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
fun CategoryTilePreview(
    viewModel: AddNewCategoryViewModel = hiltViewModel(),
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(0.9f))
        CategoryEntry(
            categoryName = viewModel.categoryName.value,
            color = viewModel.getCategoryTypeColor(viewModel.colorScheme.value),
            img = viewModel.getCategoryImg(viewModel.buttonSelected.value),
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(0.9f))
    }
}

@Composable
fun CategoryNameInput(
    viewModel: AddNewCategoryViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Mint.copy(alpha = 0.3f))
            .padding(vertical = 9.dp)
            .padding(top = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .background(Color.White)
                .height(54.dp)
                .width(280.dp)
                .focusRequester(focusRequester = viewModel.focusRequester),
            value = viewModel.categoryName.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (viewModel.isInputError.value) {
                    Color.Red
                } else {
                    Color.Gray
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            onValueChange = {
                viewModel.categoryName.value = it
                viewModel.isInputError.value = false
            }

        )
    }
}

@Composable
fun ImgPicker(
    viewModel: AddNewCategoryViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(Mint.copy(alpha = 0.3f))
            .fillMaxWidth()
            .padding(vertical = 9.dp)
            .padding(bottom = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
        ) {
            repeat(viewModel.getImgPickerRows()) { row ->
                Spacer(modifier = Modifier.height(1.dp))
                Row() {
                    viewModel.getOneImgRow(row).forEach {
                        Spacer(modifier = Modifier.width(1.dp))
                        ImgBox(index = viewModel.getIndexByImg(it))
                    }
                }
            }
        }
    }
}

@Composable
fun ImgBox(
    index: Int,
    viewModel: AddNewCategoryViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(color = viewModel.imgPickerOutline(index))
            .requiredHeight(60.dp)
            .aspectRatio(1f)
            .offset(x = 2.dp, y = 2.dp)
            .clickable {
                viewModel.buttonSelected.value = index
            }
    ) {
        Box(
            modifier = Modifier.requiredSize(56.dp)
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = viewModel.getCategoryImg(img = index))
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "Category image",
            )
        }
    }
}
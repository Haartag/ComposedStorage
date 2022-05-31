package com.valerytimofeev.composedstorage.addnewcategory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.CategoryEntry
import com.valerytimofeev.composedstorage.common.TabNameBackground
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.common.TopBarOkIcon
import com.valerytimofeev.composedstorage.ui.theme.Mint

@Composable
fun AddNewCategoryScreen(
    viewModel: AddNewCategoryViewModel = hiltViewModel(),
    navController: NavController
) {
    val focusManager = LocalFocusManager.current
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {
            TopBar(
                title = stringResource(R.string.add_category_title),
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
            CategoryNameInput(focusManager = focusManager)
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
        //if (tabDataFlow.value.isNotEmpty()) {
        TabNameBackground(
            color = viewModel.getCategoryTypeColor(
                tabDataFlow.value.getOrElse(
                    viewModel.selectedTabIndex.value,
                    defaultValue = { viewModel.tabItemPlaceholder }).colorScheme
            ),
            sideIcons = false
        )
        // }
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
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
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
            textStyle = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.weight(0.9f))
    }
}

@Composable
fun CategoryNameInput(
    viewModel: AddNewCategoryViewModel = hiltViewModel(),
    focusManager: FocusManager
) {
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
            textStyle = MaterialTheme.typography.subtitle1.merge(TextStyle(fontSize = 18.sp)),
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
            .padding(vertical = 4.dp)
            .padding(bottom = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row() {
            Icon(
                Icons.Outlined.ArrowBack,
                contentDescription = "Scroll left",
                modifier = if (viewModel.leftButtonClickable.value) Modifier.clickable {
                    viewModel.setSelected.value--
                    viewModel.setButtons()
                } else {
                    Modifier
                }
                    .height(185.dp)
                    .padding(horizontal = 8.dp),
                tint = if (viewModel.leftButtonClickable.value) Color.Black else Color.LightGray
            )
            Column(
            ) {
                repeat(3) { row ->
                    Spacer(modifier = Modifier.height(1.dp))
                    Row() {
                        val spacerSize = ((4 - viewModel.getOneImgRow(row).size) * 61).dp
                        viewModel.getOneImgRow(row).forEach {
                            Spacer(modifier = Modifier.width(1.dp))
                            ImgBox(index = viewModel.getIndexByImg(it))
                        }
                        Spacer(modifier = Modifier.width(spacerSize))
                    }
                }
            }
            Icon(
                Icons.Outlined.ArrowForward,
                contentDescription = "Scroll right",
                modifier = if (viewModel.rightButtonClickable.value) Modifier.clickable {
                    viewModel.setSelected.value++
                    viewModel.setButtons()
                } else {
                    Modifier
                }
                    .height(185.dp)
                    .padding(horizontal = 8.dp),
                tint = if (viewModel.rightButtonClickable.value) Color.Black else Color.LightGray
            )
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
                    .scale(scale = Scale.FILL)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "Category image",
                contentScale = ContentScale.Crop
            )
        }
    }
}
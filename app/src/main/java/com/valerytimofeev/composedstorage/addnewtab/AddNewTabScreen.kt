package com.valerytimofeev.composedstorage.addnewtab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.TabNameBackground
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.common.TopBarOkIcon
import com.valerytimofeev.composedstorage.ui.theme.Mint

@Composable
fun AddNewTabScreen(
    navController: NavController,
    viewModel: AddNewTabViewModel = hiltViewModel(),
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            TopBar(
                title = stringResource(R.string.add_new_tab_title),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() },
                additionalInfo = {
                    TopBarOkIcon(onClick = {
                        if (viewModel.tabNameText.value.isNotEmpty()) {
                            viewModel.addTab()
                            navController.navigate("category_list_screen")
                        } else {
                            viewModel.isInputError.value = true
                            viewModel.focusRequester.requestFocus()
                        }
                    })
                },
            )
            TabNamePreview()
            CategoryPlaceholder()
            Spacer(modifier = Modifier.height(24.dp))
            TabNameTextInput(focusManager = focusManager)
            ColorPicker()
        }
    }
}

@Composable
fun TabNamePreview(
    viewModel: AddNewTabViewModel = hiltViewModel(),
) {
    Box(contentAlignment = Alignment.Center) {
        TabNameBackground(color = viewModel.getColorByIndex(viewModel.buttonSelected.value), sideIcons = false)
        Text(
            text = viewModel.tabNameText.value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(horizontal = 50.dp)
        )
    }
}

@Composable
fun CategoryPlaceholder(
    viewModel: AddNewTabViewModel = hiltViewModel()
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(0.9f))
        Box(
            modifier = Modifier
                .shadow(elevation = 4.dp, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .aspectRatio(1f)
                .weight(1f)
                .background(color = viewModel.getColorByIndex(viewModel.buttonSelected.value))
        )
        Spacer(modifier = Modifier.weight(0.9f))
    }
}

@Composable
fun TabNameTextInput(
    viewModel: AddNewTabViewModel = hiltViewModel(),
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
            value = viewModel.tabNameText.value,
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
                viewModel.tabNameText.value = it
                viewModel.isInputError.value = false
            }

        )
    }
}

@Composable
fun ColorPicker(
    viewModel: AddNewTabViewModel = hiltViewModel(),
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
            //modifier = Modifier.padding(16.dp)
        ) {
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
            .requiredHeight(40.dp)
            .aspectRatio(1f)
            .offset(x = 2.dp, y = 2.dp)
            .clickable {
                viewModel.buttonSelected.value = index
            }
    ) {
        Box(
            modifier = Modifier
                .background(color = color)
                .requiredHeight(36.dp)
                .aspectRatio(1f)
        )
    }
}
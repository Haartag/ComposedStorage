package com.valerytimofeev.composedstorage.categorydetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.valerytimofeev.composedstorage.data.database.StorageItem
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.HorizontalPickerState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun CategoryDetailScreen(
    categoryName: String,
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    viewModel.loadItemListForCategory(category = categoryName)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopMenu(categoryName = categoryName, modifier = Modifier.height(48.dp))
        ItemsList()
    }

}

@Composable
fun TopMenu(
    categoryName: String,
    modifier: Modifier = Modifier,
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = categoryName, fontSize = 24.sp)
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable {
                        viewModel.openChangeDialog.value = true
                    }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ItemsList(
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    //open change dialog
    if (viewModel.openChangeDialog.value) {
        //Change item ChangeDialog
        if (viewModel.isChangeDialog.value) {
            ChangeDialog(
                header = {
                    ItemContent(
                        name = viewModel.clickedStorage.name,
                        size = viewModel.getDecimalSize(),
                        sizeType = viewModel.clickedStorage.sizeType
                    )
                },
                leftButtonText = "Delete",
                onLeftClick = {
                    viewModel.deleteItem()
                    viewModel.openChangeDialog.value = false
                    viewModel.isChangeDialog.value = false
                },
                rightButtonText = "Add",
                onRightClick = {
                    if (!viewModel.isErrorInSize.value) {
                        viewModel.changeItem()
                        viewModel.openChangeDialog.value = false
                        viewModel.isChangeDialog.value = false
                    }
                }
            )
        } else {
            //Add new item ChangeDialog
            ChangeDialog(
                header = {},
                leftButtonText = "Add",
                onLeftClick = {
                    viewModel.deleteItem()
                    viewModel.openChangeDialog.value = false
                    viewModel.isChangeDialog.value = false
                },
                rightButtonText = "Cancel",
                onRightClick = {
                    viewModel.changeItem()
                    viewModel.openChangeDialog.value = false
                    viewModel.isChangeDialog.value = false
                }
            )
        }
    }

    //Main list
    LazyColumn(contentPadding = PaddingValues(12.dp)) {
        items(count = viewModel.getStorageListSize()) {
            ItemEntry(
                itemIndex = it,
                storageList = viewModel.storageList
            )
        }
    }
}

@Composable
fun ItemEntry(
    storageList: List<StorageItem>,
    itemIndex: Int,
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    ItemContent(
        name = storageList[itemIndex].name,
        size = viewModel.sizeToDecimalSize(storageList[itemIndex].size),
        sizeType = storageList[itemIndex].sizeType,
        modifier = Modifier.clickable {
            //save item data to viewmodel for dialog
            viewModel.saveClickedStorage(itemIndex = itemIndex)
            viewModel.openChangeDialog.value = true
            viewModel.isChangeDialog.value = true
        }
    )
    Spacer(modifier = Modifier.height(12.dp))
}


@Composable
fun ItemContent(
    modifier: Modifier = Modifier,
    name: String,
    size: String,
    sizeType: String
) {
    Box(
        modifier = modifier
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .background(color = Mint)
            .fillMaxWidth()
            .height(60.dp)
            .padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = name,
                textAlign = TextAlign.Center
            )
            Row(horizontalArrangement = Arrangement.End) {
                Text(
                    text = size,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = sizeType,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ChangeDialog(
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    header: @Composable () -> Unit,
    leftButtonText: String,
    onLeftClick: () -> Unit,
    rightButtonText: String,
    onRightClick: () -> Unit,
    ) {
    NoPaddingAlertDialog(
        title = {
            header()
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                //focus textfield on both text and icon click
                val focusRequester = FocusRequester()

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            focusRequester.requestFocus()
                        }
                ) {
                    var nameText by remember {
                        mutableStateOf(viewModel.clickedStorage.name)
                    }
                    BasicTextField(
                        value = nameText,
                        onValueChange = {
                            nameText = it
                            viewModel.clickedStorage.name = it
                        },
                        textStyle = TextStyle(fontSize = 20.sp),
                        modifier = Modifier.focusRequester(focusRequester = focusRequester)
                    )
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Decrease icon",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    var sizeText by remember {
                        mutableStateOf(viewModel.getDecimalSize())
                    }

                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Decrease icon",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(vertical = 8.dp)
                            .clickable {
                                if (!viewModel.isErrorInSize.value) {
                                    sizeText = viewModel.buttonChangeWeight(false)
                                }
                            }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .height(60.dp)
                            .width(120.dp),
                        //when data is wrong paint outline in red
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (viewModel.isErrorInSize.value) {
                                Color.Red
                            } else {
                                Color.Gray
                            }
                        ),
                        value = sizeText,
                        onValueChange = {
                            /** TODO need some rework:
                             * locale decimal separator support
                             * extra symbols in OnValueChange bug on some android versions
                             **/
                            if (viewModel.checkManualInput(it)) {
                                sizeText = viewModel.manualChangeWeight(it)
                            }
                            if (!viewModel.isErrorInSize.value) {
                                viewModel.saveIntegerSize(it)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Icon(
                        Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Increase icon",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(vertical = 8.dp)
                            .clickable {
                                if (!viewModel.isErrorInSize.value) {
                                    sizeText = viewModel.buttonChangeWeight(true)
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                SizeTypePicker()
                Spacer(modifier = Modifier.height(24.dp))
            }


        },
        onDismissRequest = {
            viewModel.openChangeDialog.value = false
            viewModel.isChangeDialog.value = false
        },
        confirmButton = {
            DialogButton(
                text = rightButtonText,
                leftIcon = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = onRightClick
            )
        },
        dismissButton = {
            DialogButton(
                text = leftButtonText,
                leftIcon = true,
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = onLeftClick
            )
        }
    )
}

@Composable
fun DialogButton(
    text: String,
    leftIcon: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            //.fillMaxWidth()
            .background(color = Mint)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                if (leftIcon) {
                    Icons.Filled.Delete
                } else {
                    Icons.Filled.Add
                },
                contentDescription = "Button",
            )
            Text(text = text)
        }
    }
}

//AlertDialog without unnecessary paddings
@Composable
fun NoPaddingAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                title?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.subtitle1
                        ProvideTextStyle(textStyle, it)
                    }
                }
                text?.let {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        val textStyle = MaterialTheme.typography.subtitle1
                        ProvideTextStyle(textStyle, it)
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        dismissButton()
                        Spacer(modifier = Modifier.width(1.dp))
                        confirmButton()
                    }
                }
            }
        }
    }
}

//Horizontal wheel picker.
@ExperimentalMaterialApi
@Composable
fun SizeTypePicker(
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    //Experimental API
    val swipeableState = rememberSwipeableState(initialValue = HorizontalPickerState.DEFAULT)
    val sizeInPx = with(LocalDensity.current) {
        (viewModel.width - viewModel.swipeLimiter).toPx()
    }
    val anchors = mapOf(
        -sizeInPx to HorizontalPickerState.NEGATIVE,
        0f to HorizontalPickerState.DEFAULT,
        sizeInPx to HorizontalPickerState.POSITIVE
    )
    val scope = rememberCoroutineScope()

    //Return to center position and change value after swipe
    when (swipeableState.currentValue) {
        HorizontalPickerState.NEGATIVE -> {
            LaunchedEffect(Unit) {
                viewModel.indexIncrease()
                scope.launch {
                    swipeableState.snapTo(HorizontalPickerState.DEFAULT)
                }
            }
        }
        HorizontalPickerState.POSITIVE -> {
            LaunchedEffect(Unit) {
                viewModel.indexDecrease()
                scope.launch {
                    swipeableState.snapTo(HorizontalPickerState.DEFAULT)
                }
            }
        }
        else -> {}
    }

    Box(
        modifier = Modifier
            .requiredHeight(75.dp)
            .requiredWidth(width = viewModel.width)
            //Experimental API
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(55.dp)
                .width(75.dp)
                //.clip(shape = RoundedCornerShape(12.dp))
                //.background(color = Mint)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(4.dp))
        )
        SwipeableTexts(swipeableState = swipeableState)
    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeableTexts(
    swipeableState: SwipeableState<HorizontalPickerState>,
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    Text(text = viewModel.pickerText[viewModel.getLeftIndex()],
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .offset(x = (-75).dp)
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }
    )
    Text(
        text = viewModel.pickerText[viewModel.getMidIndex()],
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }

    )
    Text(
        text = viewModel.pickerText[viewModel.getRightIndex()],
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .offset(x = 75.dp)
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }

    )
}

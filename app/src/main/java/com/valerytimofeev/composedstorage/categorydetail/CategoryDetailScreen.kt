package com.valerytimofeev.composedstorage.categorydetail

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.BaseInputConnection
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.ItemBox
import com.valerytimofeev.composedstorage.common.ItemContent
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.data.database.StorageItem
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.Constants
import com.valerytimofeev.composedstorage.utils.Constants.sizeTypeMap
import com.valerytimofeev.composedstorage.utils.HorizontalPickerState
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun CategoryDetailScreen(
    categoryName: String,
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        viewModel.currentCategory.value = categoryName
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(
                title = categoryName,
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() },
                additionalInfo = { TopBarAddIcon(categoryName = categoryName) }
            )
            ItemsList(categoryName = categoryName)
        }
    }
}

@Composable
fun TopBarAddIcon(
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    categoryName: String
) {
    Icon(
        Icons.Filled.AddCircle,
        contentDescription = "Add item icon",
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
            .aspectRatio(1f)
            .clickable {
                viewModel.addToClickedStorage(category = categoryName)
                viewModel.openChangeOrAddNewDialog.value = true
            }
    )
}

@ExperimentalMaterialApi
@Composable
fun ItemsList(
    categoryName: String,
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    //open change dialog
    if (viewModel.openChangeOrAddNewDialog.value) {
        //Change item ChangeDialog
        if (viewModel.isChangeDialog.value) {
            ChangeDialog(
                header = {
                    ItemContent(
                        name = viewModel.clickedStorage.name,
                        size = viewModel.getDecimalSize(),
                        sizeType = stringResource(
                            id = sizeTypeMap[viewModel.clickedStorage.sizeType]
                                ?: R.string.placeholder
                        )
                    )
                },
                leftButtonText = stringResource(R.string.button_delete),
                leftIcon = Icons.Filled.Delete,
                onLeftClick = {
                    viewModel.deleteItem()
                    viewModel.openChangeOrAddNewDialog.value = false
                    viewModel.isChangeDialog.value = false
                },
                rightButtonText = stringResource(R.string.button_edit),
                rightIcon = Icons.Filled.Add,
                onRightClick = {
                    if (!viewModel.isErrorInSize.value) {
                        viewModel.changeItem()
                        viewModel.openChangeOrAddNewDialog.value = false
                        viewModel.isChangeDialog.value = false
                    }
                }
            )
        } else {
            //Add new item ChangeDialog
            ChangeDialog(
                header = {
                    AddItemContent(
                        name = categoryName
                    )
                },
                leftButtonText = stringResource(R.string.button_cancel),
                leftIcon = Icons.Filled.Close,
                onLeftClick = {
                    viewModel.openChangeOrAddNewDialog.value = false
                    viewModel.isChangeDialog.value = false
                },
                rightButtonText = stringResource(R.string.button_add),
                rightIcon = Icons.Filled.Add,
                onRightClick = {
                    viewModel.addItem()
                    viewModel.openChangeOrAddNewDialog.value = false
                    viewModel.isChangeDialog.value = false
                }
            )
        }
    }
    val dataFlow = viewModel.getFlow(categoryName).collectAsState(initial = emptyList())
    //Main list
    LazyColumn(contentPadding = PaddingValues(12.dp)) {
        items(count = dataFlow.value.size) {
            ItemEntry(
                itemIndex = it,
                storageList = dataFlow.value
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
        sizeType = stringResource(
            id = sizeTypeMap[storageList[itemIndex].sizeType] ?: R.string.placeholder
        ),
        modifier = Modifier.clickable {
            //save item data to viewmodel for dialog
            viewModel.saveClickedItem(item = storageList[itemIndex])
            viewModel.openChangeOrAddNewDialog.value = true
            viewModel.isChangeDialog.value = true
        }
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun AddItemContent(
    name: String
) {
    ItemBox {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun ChangeDialog(
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    rightIcon: ImageVector,
    leftIcon: ImageVector,
    header: @Composable () -> Unit,
    leftButtonText: String,
    onLeftClick: () -> Unit,
    rightButtonText: String,
    onRightClick: () -> Unit,
) {
    var focusManager = LocalFocusManager.current

    NoPaddingAlertDialog(
        focusManager = focusManager,
        title = {
            header()
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var nameText by remember {
                        mutableStateOf(viewModel.clickedStorage.name)
                    }
                    focusManager = LocalFocusManager.current
                    OutlinedTextField(
                        value = nameText,
                        onValueChange = {
                                nameText = it
                                viewModel.clickedStorage.name = it
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                        textStyle = MaterialTheme.typography.subtitle1.merge(TextStyle(fontSize = 18.sp)),
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
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
                    //focusManager = LocalFocusManager.current

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
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                        textStyle = MaterialTheme.typography.h6.merge(
                            TextStyle(
                                textAlign = TextAlign.Center,
                            )
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
            viewModel.openChangeOrAddNewDialog.value = false
            viewModel.isChangeDialog.value = false
        },
        confirmButton = {
            DialogButton(
                text = rightButtonText,
                icon = rightIcon,
                modifier = Modifier.fillMaxWidth(),
                onClick = onRightClick
            )
        },
        dismissButton = {
            DialogButton(
                text = leftButtonText,
                icon = leftIcon,
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = onLeftClick
            )
        }
    )
}

@Composable
fun DialogButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colors.primary)
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
                imageVector = icon,
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
    properties: DialogProperties = DialogProperties(),
    focusManager: FocusManager
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
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
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
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
        (Constants.horizontalPickerWidth - Constants.horizontalPickerSwipeLimiter).toPx()
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
            .requiredWidth(width = Constants.horizontalPickerWidth)
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
    Text(text = stringResource(id = Constants.pickerText[viewModel.getLeftIndex()]),
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .offset(x = (-75).dp)
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }
    )
    Text(
        text = stringResource(id = Constants.pickerText[viewModel.getMidIndex()]),
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }
    )
    Text(
        text = stringResource(id = Constants.pickerText[viewModel.getRightIndex()]),
        fontSize = 14.sp,
        color = Color.Gray,
        modifier = Modifier
            .offset(x = 75.dp)
            .offset {
                IntOffset(swipeableState.offset.value.roundToInt(), 0)
            }
    )
}


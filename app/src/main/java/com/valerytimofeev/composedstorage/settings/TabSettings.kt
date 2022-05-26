package com.valerytimofeev.composedstorage.settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.common.TopBarOkIcon

@ExperimentalFoundationApi
@Composable
fun TabSettingsSubmenu(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            title = "Tab settings",
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
            additionalInfo = {
                TopBarOkIcon {
                    viewModel.saveNewOrderInDatabase(navController = navController)
                }
            }
        )
        DragAndDropColumn()
    }
}

// Based on Foundation LazyColumnDragAndDropDemo

@ExperimentalFoundationApi
@Composable
fun DragAndDropColumn(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val dataFlow = viewModel.getFlow().collectAsState(initial = emptyList())
    val listState = rememberLazyListState()


    if (dataFlow.value.isNotEmpty()) {

        var list by remember { mutableStateOf(dataFlow.value.map { it.tabName }) }

        viewModel.tabDatabaseOrder = dataFlow.value
        viewModel.saveCurrentOrder(list)

        val dragDropState = rememberDragDropState(listState) { fromIndex, toIndex ->
            list = list.toMutableList().apply {
                add(toIndex, removeAt(fromIndex))
            }
        }

        LazyColumn(
            modifier = Modifier.dragContainer(dragDropState),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(list, key = { _, item -> item }) { index, item ->
                DraggableItem(dragDropState, index) { isDragging ->
                    val elevation by animateDpAsState(if (isDragging) 12.dp else 1.dp)
                    Card(elevation = elevation) {
                        Box(contentAlignment = Alignment.CenterStart) {
                            Text(
                                item,
                                Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )
                            if (list.size > 1) {
                                Icon(Icons.Outlined.Delete,
                                    contentDescription = "Delete tab icon",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.CenterEnd)
                                        .requiredHeight(24.dp)
                                        .aspectRatio(1f)
                                        .clickable {
                                            viewModel.saveToDeleted(item)
                                            list = list
                                                .toMutableList()
                                                .apply {
                                                    remove(item)
                                                }
                                        })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun rememberDragDropState(
    lazyListState: LazyListState,
    onMove: (Int, Int) -> Unit
): DragDropState {
    val scope = rememberCoroutineScope()
    val state = remember(lazyListState) {
        DragDropState(
            state = lazyListState,
            onMove = onMove,
            scope = scope
        )
    }
    LaunchedEffect(state) {
        while (true) {
            val diff = state.scrollChannel.receive()
            lazyListState.scrollBy(diff)
        }
    }
    return state
}

@ExperimentalFoundationApi
@Composable
fun LazyItemScope.DraggableItem(
    dragDropState: DragDropState,
    index: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(isDragging: Boolean) -> Unit
) {
    val dragging = index == dragDropState.draggingItemIndex
    val draggingModifier = if (dragging) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationY = dragDropState.draggingItemOffset
            }
    } else if (index == dragDropState.previousIndexOfDraggedItem) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationY = dragDropState.previousItemOffset.value
            }
    } else {
        Modifier.animateItemPlacement()
    }
    Column(modifier = modifier.then(draggingModifier)) {
        content(dragging)
    }
}

fun Modifier.dragContainer(dragDropState: DragDropState): Modifier {
    return pointerInput(dragDropState) {
        detectDragGesturesAfterLongPress(
            onDrag = { change, offset ->
                change.consume()
                dragDropState.onDrag(offset = offset)
            },
            onDragStart = { offset -> dragDropState.onDragStart(offset) },
            onDragEnd = { dragDropState.onDragInterrupted() },
            onDragCancel = { dragDropState.onDragInterrupted() }
        )
    }
}



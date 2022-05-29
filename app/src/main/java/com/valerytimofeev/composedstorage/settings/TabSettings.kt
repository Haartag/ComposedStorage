package com.valerytimofeev.composedstorage.settings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.R
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
            title = stringResource(R.string.tab_settings),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
            additionalInfo = {
                TopBarOkIcon {
                    viewModel.saveNewTabOrderInDatabase(navController = navController)
                }
            }
        )
        TabDragAndDropColumn()
    }
}

// Based on Foundation LazyColumnDragAndDropDemo

@ExperimentalFoundationApi
@Composable
fun TabDragAndDropColumn(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val dataFlow = viewModel.getTabFlow().collectAsState(initial = emptyList())
    val listState = rememberLazyListState()


    if (dataFlow.value.isNotEmpty()) {

        var list by remember { mutableStateOf(dataFlow.value.map { it.tabName }) }

        viewModel.tabDatabaseOrder = dataFlow.value
        viewModel.saveCurrentTabOrder(list)

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
                                            viewModel.saveToDeletedTabs(item)
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




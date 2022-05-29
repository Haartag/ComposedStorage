package com.valerytimofeev.composedstorage.allitemslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.common.ItemContent
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.Constants.sizeTypeMap
import com.valerytimofeev.composedstorage.utils.ListForSearch

@Composable
fun ShowAllScreen(
    navController: NavController,
) {
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ) {
        TopBar(
            title = stringResource(R.string.show_all_title),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() },
        )
        SearchTab(focusManager = focusManager)
        AllItemsList(navController = navController)
    }
}

@Composable
fun SearchTab(
    focusManager: FocusManager,
    viewModel: ShowAllViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(color = Mint),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = viewModel.searchText.value,
            onValueChange = {
                viewModel.searchText.value = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            textStyle = TextStyle(
                fontSize = 18.sp
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(1.dp, RoundedCornerShape(4.dp))
                .background(Color.White, RoundedCornerShape(4.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .onFocusChanged {
                    viewModel.isHintDisplayed.value =
                        !it.isFocused && viewModel.searchText.value.isEmpty()
                }
        )
        if (viewModel.isHintDisplayed.value) {
            Text(
                text = stringResource(R.string.search_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 12.dp),
                color = Color.LightGray,
                fontSize = 18.sp,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun AllItemsList(
    viewModel: ShowAllViewModel = hiltViewModel(),
    navController: NavController
) {
    val dataFlow = viewModel.getSearchFlow().collectAsState(initial = emptyList())
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(count = viewModel.search(dataFlow.value, viewModel.searchText.value).size) {
            ExtendedItem(
                index = it,
                list = viewModel.search(dataFlow.value, viewModel.searchText.value),
                navController = navController
            )
        }
    }
}

@Composable
fun ExtendedItem(
    index: Int,
    list: List<ListForSearch>,
    viewModel: ShowAllViewModel = hiltViewModel(),
    navController: NavController
) {
    Box {
        val item = list[index]
        Column {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "${item.tabName} -> ${item.categoryName}"
            )
            ItemContent(
                name = item.itemName,
                size = viewModel.stringSizeToDecimalSize(item.size),
                sizeType = stringResource(id = sizeTypeMap[item.sizeType] ?: R.string.placeholder),
                modifier = Modifier.clickable {
                    /**
                     * ToDo think what to do here:
                     *  - open categoryDetail and open dialog in it
                     *  - or open dialog here?
                     * **/
                    navController.navigate("category_detail_screen/${item.categoryName}")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
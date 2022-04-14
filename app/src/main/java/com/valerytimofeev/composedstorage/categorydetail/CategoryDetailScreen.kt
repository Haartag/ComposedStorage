package com.valerytimofeev.composedstorage.categorydetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.DialogInputData


@Composable
fun CategoryDetailScreen(
    text: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopMenu(text = text, modifier = Modifier.height(48.dp))
        ItemsList()
    }

}

@Composable
fun TopMenu(
    text: String,
    modifier: Modifier = Modifier
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
            Text(text = text, fontSize = 24.sp)
            Icon(
                Icons.Filled.AddCircle,
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable {

                    }
            )
        }
    }
}

@Composable
fun ItemsList(
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    if (viewModel.openChangeDialog.value) {
        ChangeDialog()
    }
    LazyColumn(contentPadding = PaddingValues(12.dp)) {
        items(count = viewModel.getStorageListSize()) {
            ItemEntry(
                itemIndex = it,
                name = viewModel.getStorageNames(),
                size = viewModel.getStorageSizes(),
                sizeType = viewModel.getStorageSizeType(),
            )
        }
    }
}

@Composable
fun ItemEntry(
    name: List<String>,
    size: List<String>,
    sizeType: List<String>,
    itemIndex: Int,
    modifier: Modifier = Modifier,
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {

    Box(
        modifier = modifier
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .background(color = Mint)
            .fillMaxWidth()
            .height(60.dp)
            .padding(4.dp)
            .clickable {
                viewModel.saveClickedStorage(name[itemIndex], size[itemIndex], sizeType[itemIndex])
                viewModel.openChangeDialog.value = true
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = name[itemIndex],
                textAlign = TextAlign.Center
            )
            Row(horizontalArrangement = Arrangement.End) {
                Text(
                    text = size[itemIndex],
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = sizeType[itemIndex],
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun ChangeDialog(
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
    AlertDialog(
        onDismissRequest = { viewModel.openChangeDialog.value = false },
        title = {
            ItemEntry(
                name = viewModel.loadClickedStorage().name,
                size = viewModel.loadClickedStorage().size,
                sizeType = viewModel.loadClickedStorage().sizeType,
                itemIndex = 0
            )
        },
        text = { Text(text = "Text") },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.openChangeDialog.value = false
                }) {
                Text("This is the Confirm Button")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.openChangeDialog.value = false
                }) {
                Text("This is the Confirm Button")
            }
        },

        )
}

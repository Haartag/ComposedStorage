package com.valerytimofeev.composedstorage.categorydetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valerytimofeev.composedstorage.ui.theme.Mint


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
                Icons.Filled.ArrowForward,
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
fun ItemsList(
    viewModel: CategoryDetailViewModel = hiltViewModel()
) {
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
    modifier: Modifier = Modifier
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
                /*ToDo*/
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

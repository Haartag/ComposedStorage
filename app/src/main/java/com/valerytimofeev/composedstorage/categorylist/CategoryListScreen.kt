package com.valerytimofeev.composedstorage.categorylist

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.valerytimofeev.composedstorage.common.CategoryEntry
import com.valerytimofeev.composedstorage.common.TabNameBackground
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.data.database.TabItem
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.floorMod


@ExperimentalPagerApi
@Composable
fun CategoryListScreen(
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val tabDataFlow = viewModel.getTabFlow().collectAsState(initial = emptyList())
            if (tabDataFlow.value.isNotEmpty()) {
                TopBar(
                    title = "AppName",
                    buttonIcon = Icons.Filled.Menu,
                    onButtonClicked = { openDrawer() }
                )
                TabNameBackground(color = viewModel.getCategoryTypeColor(tabDataFlow.value[viewModel.currentPage.value].colorScheme))
                TabPager(navController = navController, tabData = tabDataFlow.value)
            } else {
                Placeholder()
            }
        }
    }
}

@Composable
fun Placeholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Mint, Color.LightGray)))
    )
}

@ExperimentalPagerApi
@Composable
fun TabPager(
    navController: NavController,
    tabData: List<TabItem>,
    viewModel: CategoryListViewModel = hiltViewModel(),
) {
    val pagerState = rememberPagerState(initialPage = viewModel.startIndex)
    HorizontalPager(
        count = Int.MAX_VALUE,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-50).dp)
    ) { index ->
        val page = (index - viewModel.startIndex).floorMod(tabData.size)
        viewModel.currentPage.value =
                //(pagerState.currentPage - viewModel.startIndex).floorMod(viewModel.tabCount.value)
            (pagerState.currentPage - viewModel.startIndex).floorMod(tabData.size)
        Column() {
            TabName(page = page)
                CategoryList(
                    navController = navController,
                    color = viewModel.getCategoryTypeColor(tabData[page].colorScheme),
                    tabName = tabData[page].tabName
                )
        }
    }
}

@Composable
fun TabName(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel = hiltViewModel(),
    page: Int,
) {
    val tabDataFlow = viewModel.getTabFlow().collectAsState(initial = emptyList())
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        contentAlignment = Alignment.Center
    ) {
        if (tabDataFlow.value.isNotEmpty()) {
            Text(
                text = tabDataFlow.value.map { it.tabName }[page],
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 50.dp)

            )
        }
    }

}

@Composable
fun CategoryList(
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    color: Color,
    tabName: String
) {
    val categoryDataFlow = viewModel.getCategoryFlow(tabName).collectAsState(initial = emptyList())

    val categoryListSize = viewModel.getCategoryRowCount(categoryDataFlow.value.size)
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(count = categoryListSize) { index ->
            CategoryRow(
                rowIndex = index,
                categoryNames = categoryDataFlow.value.map { it.category },
                navController = navController,
                color = color,
                imgIndex = categoryDataFlow.value.map { it.categoryImg }
            )
        }
    }
}

@Composable
fun CategoryRow(
    rowIndex: Int,
    categoryNames: List<String>,
    color: Color,
    imgIndex: List<Int>,
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
) {
    Column {
        Row {
            CategoryEntry(
                categoryName = categoryNames[rowIndex * 2],
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("category_detail_screen/${categoryNames[rowIndex * 2]}") },
                color = color,
                img = viewModel.getCategoryImg(
                    imgIndex = imgIndex[rowIndex * 2],
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            if (categoryNames.size >= rowIndex * 2 + 2) {
                CategoryEntry(
                    categoryName = categoryNames[rowIndex * 2 + 1],
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate("category_detail_screen/${categoryNames[rowIndex * 2 + 1]}") },
                    color = color,
                    img = viewModel.getCategoryImg(
                        imgIndex = imgIndex[rowIndex * 2 + 1],
                    )
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


package com.valerytimofeev.composedstorage.categorylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.valerytimofeev.composedstorage.common.TopBar
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.utils.floorMod


@ExperimentalPagerApi
@Composable
fun CategoryListScreen(
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    openDrawer: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (viewModel.tabListLoadEnded.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TopBar(
                    title = "AppName",
                    buttonIcon = Icons.Filled.Menu,
                    onButtonClicked = { openDrawer() }
                )
                TabNameBackground(color = viewModel.getCategoryTypeColor(viewModel.currentPage.value))
                TabPager(navController = navController)
            }
        } else {
            Placeholder()
        }
    }
}

@Composable
fun Placeholder() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(listOf(Mint, Color.LightGray))))
}

@ExperimentalPagerApi
@Composable
fun TabPager(
    navController: NavController,
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
        val page = (index - viewModel.startIndex).floorMod(viewModel.tabCount.value)
        viewModel.currentPage.value =
            (pagerState.currentPage - viewModel.startIndex).floorMod(viewModel.tabCount.value)
        Column() {
            TabName(page = page)
            CategoryList(
                navController = navController,
                color = viewModel.getCategoryTypeColor(page),
                page = page
            )
        }
    }
}

@Composable
fun TabName(
    modifier: Modifier = Modifier,
    viewModel: CategoryListViewModel = hiltViewModel(),
    page: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = viewModel.tabList[page].tabName,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
    }

}

@Composable
fun TabNameBackground(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = color.copy(alpha = 0.5f))
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = "Left arrow",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxHeight()
                )
                Icon(
                    Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "Right arrow",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun CategoryList(
    page: Int,
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel(),
    color: Color
) {
    val categoryListSize = viewModel.getCategoryRowCount(page)
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(count = categoryListSize) {
            CategoryRow(
                rowIndex = it,
                //categoryNames = viewModel.getCategoryByTab(page),
                categoryNames = viewModel.getCategoryNamesByTab(page),
                navController = navController,
                color = color
            )
        }
    }
}

@Composable
fun CategoryRow(
    rowIndex: Int,
    categoryNames: List<String>,
    color: Color,
    navController: NavController
) {
    Column {
        Row {
            CategoryEntry(
                categoryName = categoryNames[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f),
                color = color
            )
            Spacer(modifier = Modifier.width(20.dp))
            if (categoryNames.size >= rowIndex * 2 + 2) {
                CategoryEntry(
                    categoryName = categoryNames[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f),
                    color = color
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CategoryEntry(
    categoryName: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .aspectRatio(1f)
            .background(color = color)
            .clickable {
                navController.navigate("category_detail_screen/${categoryName}")
            }
    ) {
        Column(
        ) {
            Text(text = "Image")
            Text(
                text = categoryName,
                textAlign = TextAlign.Center
            )
        }
    }
}
package com.valerytimofeev.composedstorage.common

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.composedstorage.R
import com.valerytimofeev.composedstorage.addnewtab.AddNewTabViewModel
import com.valerytimofeev.composedstorage.ui.theme.Mint


@Composable
fun TopBar(
    title: String = "",
    buttonIcon: ImageVector,
    onButtonClicked: () -> Unit,
    additionalInfo: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        actions = {
            additionalInfo()
        },
        backgroundColor = Mint
    )
}

@Composable
fun TopBarOkIcon(
    onClick: () -> Unit
) {
    Icon(
        Icons.Filled.Done,
        contentDescription = "Add item icon",
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight()
            .aspectRatio(1f)
            .clickable {
                onClick()
            }
    )
}


@Composable
fun CategoryEntry(
    categoryName: String,
    modifier: Modifier = Modifier,
    color: Color,
    img: Int,
) {
    Box(
        contentAlignment = BottomCenter,
        modifier = modifier
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .aspectRatio(1f)
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = img)
                .build()
        )
        if (img != 0) {
            Image(
                painter = painter,
                contentDescription = categoryName,
                modifier = modifier
                    .fillMaxSize()
            )
        }
        Box(
            contentAlignment = BottomCenter,
            modifier = modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Transparent, color)
                    )
                )

        ) {
            //TODO change text
            Text(text = categoryName, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        }
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
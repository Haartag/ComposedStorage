package com.valerytimofeev.composedstorage.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
fun ItemBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
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
        content()
    }
}

@Composable
fun ItemContent(
    modifier: Modifier = Modifier,
    name: String,
    size: String,
    sizeType: String
) {
    ItemBox(
        modifier = modifier
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


@Composable
fun CategoryEntry(
    categoryName: String,
    modifier: Modifier = Modifier,
    color: Color,
    img: Int,
    fontWeight: FontWeight = FontWeight.SemiBold,
    fontSize: TextUnit = 18.sp
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
        Image(
            painter = painter,
            contentDescription = categoryName,
            modifier = modifier
                .fillMaxSize()
        )
        Box(
            contentAlignment = BottomCenter,
        ) {
            Column() {
                Box(modifier = Modifier
                    .weight(0.4f)
                    .fillMaxWidth())
                Box(
                    modifier = Modifier
                        .weight(0.55f)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color.Transparent, color)
                            )
                        )
                )
                Box(modifier = Modifier
                    .weight(0.05f)
                    .background(color = color)
                    .fillMaxWidth())
            }
            Text(text = categoryName, fontWeight = fontWeight, fontSize = fontSize)
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
package com.valerytimofeev.composedstorage.common

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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.valerytimofeev.composedstorage.ui.theme.Mint
import com.valerytimofeev.composedstorage.ui.theme.MintTest
import com.valerytimofeev.composedstorage.ui.theme.TopBarBlue

//TopBar of each page. Additional button can be in additionalInfo.
@Composable
fun TopBar(
    title: String = "",
    titleIcon: @Composable () -> Unit = {},
    buttonIcon: ImageVector,
    onButtonClicked: () -> Unit,
    titleOffset: Dp = 0.dp,
    textStyle: TextStyle = MaterialTheme.typography.h6,
    additionalInfo: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.offset(x = titleOffset),
                horizontalArrangement = Arrangement.Center
            ) {
                titleIcon()
                Text(
                    text = title,
                    style = textStyle,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        actions = {
            additionalInfo()
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

//Ok icon for TopBar
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

//Empty tile for CategoryDetail and dialog
@Composable
fun ItemBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(elevation = 4.dp, RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
            .background(color = MaterialTheme.colors.primary)
            .fillMaxWidth()
            .height(60.dp)
            .padding(4.dp),
        contentAlignment = Center
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
                style = MaterialTheme.typography.body1.merge(TextStyle(fontSize = 18.sp))
            )
            Row(horizontalArrangement = Arrangement.End) {
                Text(
                    text = size,
                    style = MaterialTheme.typography.body1.merge(TextStyle(fontSize = 18.sp))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = sizeType,
                    style = MaterialTheme.typography.body1.merge(TextStyle(fontSize = 18.sp))
                )
            }
        }
    }
}

//Empty tile for CategoryList and previews
@Composable
fun CategoryEntry(
    categoryName: String,
    modifier: Modifier = Modifier,
    color: Color,
    img: Int,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1
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
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            contentAlignment = BottomCenter,
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxWidth()
                )
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
                Box(
                    modifier = Modifier
                        .weight(0.05f)
                        .background(color = color)
                        .fillMaxWidth()
                )
            }
            Text(
                text = categoryName,
                style = textStyle,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TabNameBackground(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    sideIcons: Boolean = true
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = color.copy(alpha = 0.6f))
                .height(50.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (sideIcons) {
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
}
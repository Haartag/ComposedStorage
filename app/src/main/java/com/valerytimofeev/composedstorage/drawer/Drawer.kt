package com.valerytimofeev.composedstorage.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.valerytimofeev.composedstorage.R

sealed class DrawerScreens(val title: Int, val route: String) {
    object Tab : DrawerScreens(R.string.add_new_tab_title, "add_new_tab_screen")
    object Category : DrawerScreens(R.string.add_category_title, "add_new_category_screen")
    object About : DrawerScreens(R.string.about_title, "about_screen")
    object Settings : DrawerScreens(R.string.settings_title, "settings")
    object ShowAll : DrawerScreens(R.string.show_all_title, "show_all")
}

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "App icon"
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        DrawerItem(
            icon = Icons.Filled.AddCircle,
            text = stringResource(id = DrawerScreens.Tab.title),
            itemClick = { onDestinationClicked(DrawerScreens.Tab.route) }
        )
        DrawerItem(
            icon = Icons.Filled.Add,
            text = stringResource(id = DrawerScreens.Category.title),
            itemClick = { onDestinationClicked(DrawerScreens.Category.route) }
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(4.dp))

        DrawerItem(
            icon = Icons.Filled.List,
            text = stringResource(id = DrawerScreens.ShowAll.title),
            itemClick = { onDestinationClicked(DrawerScreens.ShowAll.route) }
        )

        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(4.dp))

        DrawerItem(
            icon = Icons.Filled.Settings,
            text = stringResource(id = DrawerScreens.Settings.title),
            itemClick = { onDestinationClicked(DrawerScreens.Settings.route) }
        )
        DrawerItem(
            icon = Icons.Filled.Info,
            text = stringResource(id = DrawerScreens.About.title),
            itemClick = { onDestinationClicked(DrawerScreens.About.route) }
        )
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    itemClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape = RoundedCornerShape(4.dp))
            .clickable {
                itemClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(imageVector = icon, contentDescription = text)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text)
        }
    }
}
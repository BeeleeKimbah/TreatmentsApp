package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.model.NavigationItem

@Composable
fun BottomBar(initialSelected: Int) = NavigationBar {
    val navItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_item_MySessions),
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List
        ),
        NavigationItem(
            title = stringResource(R.string.menu_item_Medics),
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        ),
        NavigationItem(
            title = stringResource(R.string.menu_item_Settings),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
        NavigationItem(
            title = stringResource(R.string.menu_item_Logout),
            selectedIcon = Icons.Filled.ExitToApp,
            unselectedIcon = Icons.Outlined.ExitToApp
        )
    )
    var selectedIndex by rememberSaveable {
        mutableStateOf(initialSelected)
    }

    navItems.forEachIndexed { index, item ->
        NavigationBarItem(
            selected = selectedIndex == index,
            label = { Text(item.title) },
            onClick = {
                selectedIndex = index
                /* TODO: Navigate */
            },
            icon = {
                Icon(
                    imageVector = if (index == selectedIndex) item.selectedIcon
                    else item.unselectedIcon,
                    contentDescription = null
                )
            }
        )
    }
}
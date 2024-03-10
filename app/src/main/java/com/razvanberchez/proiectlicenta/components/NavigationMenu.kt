package com.razvanberchez.proiectlicenta.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.razvanberchez.proiectlicenta.resources.navItems
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavMenu(modifier: Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedNavItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(7, 26, 78)
            ) {
                Spacer(modifier = modifier.height(16.dp))
                navItems.forEachIndexed { index, navigationItem ->
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(7, 26, 78),
                            selectedContainerColor = Color(24, 61, 164),
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White
                        ),
                        label = {
                            Text(text = navigationItem.title, color = Color.White)
                        },
                        selected = index == selectedNavItemIndex,
                        onClick = {
                            selectedNavItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector =
                                if (index == selectedNavItemIndex)
                                    navigationItem.selectedIcon
                                else navigationItem.unselectedIcon,
                                contentDescription = navigationItem.title,
                            )
                        },
                        modifier = modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        TopBar(modifier, selectedNavItemIndex) {
            scope.launch {
                drawerState.open()
            }
        }
    }
}
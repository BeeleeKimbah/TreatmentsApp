package com.razvanberchez.proiectlicenta.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings

val navItems = listOf(
    NavigationItem(
        title = "Sesiunile mele",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List
    ),
    NavigationItem(
        title = "Medici",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    ),
    NavigationItem(
        title = "Setari si Preferinte",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    ),
    NavigationItem(
        title = "Logout",
        selectedIcon = Icons.Filled.ExitToApp,
        unselectedIcon = Icons.Outlined.ExitToApp
    )
)
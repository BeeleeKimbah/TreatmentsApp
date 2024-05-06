package com.razvanberchez.proiectlicenta.data.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.razvanberchez.proiectlicenta.view.screen.destinations.DirectionDestination

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: DirectionDestination
)

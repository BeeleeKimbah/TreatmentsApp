package com.razvanberchez.proiectlicenta.view.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.view.screen.NavGraphs
import com.razvanberchez.proiectlicenta.view.screen.appCurrentDestinationAsState
import com.razvanberchez.proiectlicenta.view.screen.destinations.Destination
import com.razvanberchez.proiectlicenta.view.screen.destinations.MedicsScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.SessionsScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.SettingsScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.startAppDestination

val bottomNavScreenRoutes = listOf(
    MedicsScreenDestination.route,
    SessionsScreenDestination.route,
    SettingsScreenDestination.route
)

enum class BottomBarDestinations(
    val direction: DirectionDestinationSpec,
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    Sessions(
        direction = SessionsScreenDestination,
        title = R.string.menu_item_MySessions,
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List
    ),
    Medics(
        direction = MedicsScreenDestination,
        title = R.string.menu_item_Medics,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    ),
    Settings(
        direction = SettingsScreenDestination,
        title = R.string.menu_item_Settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
}

@Composable
fun BottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    if (currentDestination.route in bottomNavScreenRoutes) {
        NavigationBar {
            BottomBarDestinations.entries.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination == item.direction,
                    label = { Text(stringResource(item.title)) },
                    onClick = {
                        navController.navigate(direction = item.direction) {
                            launchSingleTop = true
                            popUpTo(route = item.direction.route) {
                                inclusive = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (currentDestination == item.direction)
                                item.selectedIcon
                            else
                                item.unselectedIcon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}
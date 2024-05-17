package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.view.screen.NavGraphs
import com.razvanberchez.proiectlicenta.view.screen.destinations.LoginScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    hasBackButton: Boolean = false,
    navigator: DestinationsNavigator,
    hasLogoutButton: Boolean = true
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
            Text(
                text = title,
                fontSize = dimensionResource(R.dimen.topbar_title_fontsize).value.sp
            )
        },
        navigationIcon = {
            if (hasBackButton)
                IconButton(
                    onClick = {
                        navigator.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
        },
        actions = {
            if (hasLogoutButton) {
                IconButton(
                    onClick = {
                        navigator.navigate(direction = LoginScreenDestination) {
                            popUpTo(route = NavGraphs.root.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ExitToApp,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
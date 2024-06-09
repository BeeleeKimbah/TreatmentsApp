package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.ui.theme.TopBarScheme
import com.razvanberchez.proiectlicenta.view.screen.NavGraphs
import com.razvanberchez.proiectlicenta.view.screen.destinations.LoginScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    hasBackButton: Boolean = false,
    navigator: DestinationsNavigator,
    hasLogoutButton: Boolean = true,
    colors: TopAppBarColors = TopBarScheme.topBarColors()
) {
    TopAppBar(
        colors = colors,
        title = {
            Text(
                text = title,
                fontSize = dimensionResource(R.dimen.topbar_title_fontsize).value.sp,
                fontWeight = FontWeight.Bold
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
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
        },
        actions = {
            if (hasLogoutButton) {
                IconButton(
                    onClick = {
                        Firebase.auth.signOut()
                        navigator.navigate(direction = LoginScreenDestination) {
                            popUpTo(route = NavGraphs.root.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                        contentDescription = null
                    )
                }
            }
        }
    )
}
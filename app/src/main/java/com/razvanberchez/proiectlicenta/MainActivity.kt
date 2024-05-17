package com.razvanberchez.proiectlicenta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.razvanberchez.proiectlicenta.ui.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.razvanberchez.proiectlicenta.view.components.BottomBar
import com.razvanberchez.proiectlicenta.view.screen.MedicsScreen
import com.razvanberchez.proiectlicenta.view.screen.NavGraphs
import com.razvanberchez.proiectlicenta.view.screen.SessionsScreen
import com.razvanberchez.proiectlicenta.view.screen.SettingsScreen
import com.razvanberchez.proiectlicenta.view.screen.destinations.MedicsScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.SessionsScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.SettingsScreenDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(
                    tonalElevation = 5.dp
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController)
                        }
                    ) { values ->
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            navController = navController
                        ) {
                            composable(SessionsScreenDestination) {
                                SessionsScreen(
                                    bottomBarPaddingValues = values,
                                    navigator = destinationsNavigator
                                )
                            }
                            composable(MedicsScreenDestination) {
                                MedicsScreen(
                                    bottomBarPaddingValues = values,
                                    navigator = destinationsNavigator
                                )
                            }
                            composable(SettingsScreenDestination) {
                                SettingsScreen(
                                    bottomBarPaddingValues = values,
                                    navigator = destinationsNavigator
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
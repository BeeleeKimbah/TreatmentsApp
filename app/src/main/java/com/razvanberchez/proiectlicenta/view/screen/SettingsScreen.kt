package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.intent.AppTheme
import com.razvanberchez.proiectlicenta.presentation.intent.SettingsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.SettingsScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.ui.theme.ThemeSelector
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.LoginScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.SettingsScreenViewState

@RootNavGraph
@Destination
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    bottomBarPaddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SettingsScreenContent(
        navigator = navigator,
        viewState = state,
        bottomBarPaddingValues = bottomBarPaddingValues,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: SettingsScreenViewState,
    bottomBarPaddingValues: PaddingValues,
    onIntent: (SettingsScreenIntent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(bottomBarPaddingValues),
        topBar = {
            TopBar(
                title = stringResource(R.string.menu_item_Settings),
                navigator = navigator
            )
        }
    ) { values ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(values)
        ) {
            Text(
                modifier = modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.details_text_padding)
                    )
                    .padding(
                        top = dimensionResource(R.dimen.details_text_padding)
                    ),
                text = stringResource(R.string.text_header_notifications),
                fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.card_padding)),
                colors = CardScheme.cardColors(),
                elevation = CardScheme.cardElevation()
            ) {
                Row(
                    modifier = modifier.padding(top = dimensionResource(R.dimen.details_text_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.text_settings_enable_notifications),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Switch(
                        checked = viewState.notificationsOn,
                        onCheckedChange = {
                            onIntent(SettingsScreenIntent.ToggleNotifications)
                        }
                    )
                }
                Row(
                    modifier = modifier.padding(top = dimensionResource(R.dimen.details_text_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.text_settings_treatment_notifications),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Switch(
                        checked = viewState.treatmentNotificationsOn,
                        onCheckedChange = {
                            onIntent(SettingsScreenIntent.ToggleTreatmentNotifications)
                        },
                        enabled = viewState.notificationsOn
                    )
                }
                Row(
                    modifier = modifier.padding(top = dimensionResource(R.dimen.details_text_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = modifier.padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.text_settings_consult_notifications),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Switch(
                        checked = viewState.appointmentNotificationsOn,
                        onCheckedChange = {
                            onIntent(SettingsScreenIntent.ToggleAppointmentNotifications)
                        },
                        enabled = viewState.notificationsOn
                    )
                }
            }

            Text(
                modifier = modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.details_text_padding)
                    )
                    .padding(
                        top = dimensionResource(R.dimen.details_text_padding)
                    ),
                text = stringResource(R.string.settings_header_theme),
                fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                fontWeight = FontWeight.Bold
            )

            val currentTheme = ThemeSelector.theme.collectAsStateWithLifecycle().value
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(dimensionResource(R.dimen.ui_elem_padding))
            ) {
                SingleChoiceSegmentedButtonRow (
                    modifier = modifier.fillMaxSize()
                ) {
                    SegmentedButton(
                        selected = currentTheme == AppTheme.LIGHT,
                        onClick = {
                            onIntent(SettingsScreenIntent.ModifyAppTheme(AppTheme.LIGHT))
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
                        icon = {
                            Icon(imageVector = Icons.Filled.LightMode, contentDescription = null)
                        }
                    ) {
                        Text(stringResource(R.string.theme_select_light))
                    }

                    SegmentedButton(
                        selected = currentTheme == AppTheme.DARK,
                        onClick = {
                            onIntent(SettingsScreenIntent.ModifyAppTheme(AppTheme.DARK))
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
                        icon = {
                            Icon(imageVector = Icons.Filled.DarkMode, contentDescription = null)
                        }
                    ) {
                        Text(stringResource(R.string.theme_select_dark))
                    }

                    SegmentedButton(
                        selected = currentTheme == AppTheme.SYSTEM,
                        onClick = {
                            onIntent(SettingsScreenIntent.ModifyAppTheme(AppTheme.SYSTEM))
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
                        icon = {
                            Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                        }
                    ) {
                        Text(stringResource(R.string.theme_select_system))
                    }
                }
            }

            Box (
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding)),
                contentAlignment = Alignment.BottomCenter
            ) {
                OutlinedButton(
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_size)),
                    onClick = {
                        Firebase.auth.signOut()
                        ThemeSelector.setTheme(AppTheme.SYSTEM)
                        navigator.navigate(direction = LoginScreenDestination) {
                            popUpTo(route = NavGraphs.root.route) {
                                inclusive = true
                            }
                        }
                    },
                ) {
                    Text(
                        text = stringResource(R.string.button_text_logout),
                        fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                    )
                }
            }
        }
    }
}
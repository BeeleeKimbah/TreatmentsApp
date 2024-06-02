package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.SettingsScreenViewState

@RootNavGraph
@Destination
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    bottomBarPaddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewState: SettingsScreenViewState = SettingsScreenViewState()
) {
    var notificationsEnabled by rememberSaveable {
        mutableStateOf(viewState.notificationsOn)
    }
    var consultNotificationsEnabled by rememberSaveable {
        mutableStateOf(viewState.consultNotificationsOn)
    }
    var treatmentNotificationsEnabled by rememberSaveable {
        mutableStateOf(viewState.treatmentNotificationsOn)
    }

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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(values)
        ) {
            item {
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
            }
            item {
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
                            checked = notificationsEnabled,
                            onCheckedChange = {
                                notificationsEnabled = !notificationsEnabled
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
                            checked = treatmentNotificationsEnabled,
                            onCheckedChange = {
                                treatmentNotificationsEnabled = !treatmentNotificationsEnabled
                            },
                            enabled = notificationsEnabled
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
                            checked = consultNotificationsEnabled,
                            onCheckedChange = {
                                consultNotificationsEnabled = !consultNotificationsEnabled
                            },
                            enabled = notificationsEnabled
                        )
                    }
                }
            }
        }
    }
}
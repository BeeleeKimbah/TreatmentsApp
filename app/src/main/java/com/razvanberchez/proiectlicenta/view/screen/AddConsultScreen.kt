package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.model.allSlots
import com.razvanberchez.proiectlicenta.presentation.intent.AddConsultScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.AddConsultScreenViewModel
import com.razvanberchez.proiectlicenta.view.components.ConsultDatePicker
import com.razvanberchez.proiectlicenta.view.components.MedicSelectDropdown
import com.razvanberchez.proiectlicenta.view.components.TimeSlotItem
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.AddConsultScreenViewState
import java.util.Date

@RootNavGraph
@Destination
@Composable
fun AddConsultScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    medicId: String? = null
) {
    val viewModel = hiltViewModel<AddConsultScreenViewModel, AddConsultScreenViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(medicId = medicId)
        }
    )

    val state by viewModel.viewState.collectAsStateWithLifecycle()

    AddConsultScreenContent(
        viewState = state,
        navigator = navigator,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddConsultScreenContent(
    modifier: Modifier = Modifier,
    viewState: AddConsultScreenViewState,
    navigator: DestinationsNavigator,
    onIntent: (AddConsultScreenIntent) -> Unit
) {
    val slotsList = allSlots.toList()

    if (viewState.addedSession) {
        navigator.navigateUp()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.button_NewSession),
                hasBackButton = true,
                navigator = navigator
            )
        }
    ) { values ->
        if (!viewState.loading) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                item {
                    MedicSelectDropdown(
                        allMedics = viewState.medics,
                        selectedMedic = viewState.selectedMedic,
                        onSelectMedic = { newMedicId ->
                            onIntent(AddConsultScreenIntent.ModifyMedic(newMedicId))
                        }
                    )
                }

                item {
                    ConsultDatePicker(
                        selectedDate = viewState.selectedDate,
                        onSelectDate = { newDateMillis ->
                            onIntent(
                                AddConsultScreenIntent.ModifyDate(Date(newDateMillis))
                            )
                        }
                    )
                }

                if (viewState.intervalPickEnabled) {
                    itemsIndexed(slotsList) { index, _ ->
                        if (index % 2 == 0) {
                            Row (
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(dimensionResource(R.dimen.ui_elem_padding))
                            ) {
                                TimeSlotItem(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    availableIntervals = viewState.availableIntervals,
                                    selectedInterval = viewState.selectedTime,
                                    interval = slotsList[index],
                                    onSelectInterval = {
                                        onIntent(
                                            AddConsultScreenIntent.ModifyTime(slotsList[index])
                                        )
                                    }
                                )

                                TimeSlotItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    availableIntervals = viewState.availableIntervals,
                                    selectedInterval = viewState.selectedTime,
                                    interval = slotsList[index + 1],
                                    onSelectInterval = {
                                        onIntent(
                                            AddConsultScreenIntent.ModifyTime(slotsList[index + 1])
                                        )
                                    }
                                )
                            }
                        }
                    }
                } else {
                    item {
                        Text(
                            text = stringResource(R.string.interval_pick_placeholder),
                            fontSize = dimensionResource(R.dimen.validation_error_fontsize).value.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = modifier.padding(dimensionResource(R.dimen.ui_elem_padding))
                        )
                    }
                }

                if (viewState.showErrorAddingSession) {
                    item {
                        Text(
                            text = stringResource(R.string.error_adding_session),
                            fontSize = dimensionResource(R.dimen.validation_error_fontsize).value.sp,
                            color = Color.Red,
                            modifier = modifier.padding(dimensionResource(R.dimen.ui_elem_padding))
                        )
                    }
                }

                item {
                    Button(
                        enabled = viewState.addSessionEnabled,
                        modifier = modifier
                            .padding(dimensionResource(R.dimen.ui_elem_padding))
                            .fillMaxWidth()
                            .height(dimensionResource(R.dimen.button_size)),
                        onClick = {
                            onIntent(AddConsultScreenIntent.AddSession)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.button_text_add_session),
                            fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                CircularProgressIndicator(
                    modifier = modifier
                        .padding(PullToRefreshDefaults.PositionalThreshold)
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}
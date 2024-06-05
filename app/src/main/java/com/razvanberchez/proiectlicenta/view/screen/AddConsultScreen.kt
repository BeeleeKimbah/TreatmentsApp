package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.format
import com.razvanberchez.proiectlicenta.presentation.intent.AddConsultScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.AddConsultScreenViewModel
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
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(values)
            ) {

                var datePickerOpen by remember {
                    mutableStateOf(false)
                }

                var medicSelectExpanded by remember {
                    mutableStateOf(false)
                }

                val state = rememberDatePickerState(
                    initialSelectedDateMillis = viewState.selectedDate.time
                )

                ExposedDropdownMenuBox(
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxWidth(),
                    expanded = medicSelectExpanded,
                    onExpandedChange = { medicSelectExpanded = it }
                ) {
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        placeholder = { Text(text = "Alegeți un medic") },
                        value = viewState.selectedMedic?.let {
                            viewState.selectedMedic.name +
                                    ", " + viewState.selectedMedic.mainSpecialty
                        } ?: "",
                        onValueChange = {},
                        label = {
                            Text(
                                text = "Medic",
                                fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                            )
                        },
                        shape = ShapeDefaults.Medium,
                        singleLine = true,
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = medicSelectExpanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = medicSelectExpanded,
                        onDismissRequest = { medicSelectExpanded = false },
                        modifier = modifier.fillMaxWidth()
                    ) {
                        viewState.medics.forEach { medic ->
                            DropdownMenuItem(
                                modifier = modifier
                                    .background(MaterialTheme.colorScheme.secondaryContainer),
                                text = {
                                    Text(text = medic.name + ", " + medic.mainSpecialty)
                                },
                                onClick = {
                                    onIntent(AddConsultScreenIntent.ModifyMedic(medic.medicId))
                                    medicSelectExpanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }

                OutlinedTextField(
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxWidth(),
                    value = viewState.selectedDate.format(),
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            text = "Dată consult",
                            fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { datePickerOpen = true }) {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = null
                            )
                        }
                    },
                    shape = ShapeDefaults.Medium,
                    singleLine = true
                )

                if (datePickerOpen) {
                    DatePickerDialog(
                        onDismissRequest = { datePickerOpen = false },
                        confirmButton = {
                            Button(
                                onClick = {
                                    onIntent(AddConsultScreenIntent.ModifyDate(
                                        state.selectedDateMillis?.let {
                                            Date(state.selectedDateMillis!!)
                                        } ?: Date()
                                    ))
                                    datePickerOpen = false
                                }
                            ) {
                                Text(text = "Ok")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    datePickerOpen = false
                                }
                            ) {
                                Text(text = "Anulează")
                            }
                        }
                    ) {
                        DatePicker(
                            state = state,
                            title = {
                                Text(
                                    modifier = modifier.padding(
                                        dimensionResource(R.dimen.ui_elem_padding)
                                    ),
                                    text = "Selectați o dată"
                                )
                            }
                        )
                    }
                }

                // TODO: Add input for time slot, containing only available intervals (live updates)
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
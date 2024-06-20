package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.format
import com.razvanberchez.proiectlicenta.presentation.intent.SessionDetailsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.SessionDetailsScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.view.components.PullDownToRefreshBox
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.AddConsultScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.AddTreatmentScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.SessionDetailsScreenViewState

@RootNavGraph
@Destination
@Composable
fun SessionDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<AddTreatmentScreenDestination, Boolean>,
    sessionId: String,
    medicSide: Boolean = false
) {
    val viewModel =
        hiltViewModel<SessionDetailsScreenViewModel, SessionDetailsScreenViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(sessionId = sessionId)
            }
        )

    val state by viewModel.viewState.collectAsStateWithLifecycle()

    resultRecipient.onNavResult {
        when (it) {
            is NavResult.Value -> {
                if (it.value) {
                    viewModel.onIntent(SessionDetailsScreenIntent.Refresh)
                }
            }

            else -> Unit
        }
    }

    SessionDetailsScreenContent(
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent,
        medicSide = medicSide
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailsScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: SessionDetailsScreenViewState,
    onIntent: (SessionDetailsScreenIntent) -> Unit,
    medicSide: Boolean
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.session_details_title),
                hasBackButton = true,
                navigator = navigator
            )
        },
        floatingActionButton = {
            if (!viewState.loading) {
                if (medicSide) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            navigator.navigate(
                                direction = AddTreatmentScreenDestination(
                                    sessionId = viewState.session?.sessionId ?: ""
                                )
                            )
                        },
                        modifier = modifier
                            .height(dimensionResource(R.dimen.button_size))
                    ) {
                        Text(
                            text = stringResource(R.string.button_text_add_treatment),
                            fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                        )
                    }
                } else {
                    ExtendedFloatingActionButton(
                        onClick = {
                            navigator.navigate(
                                direction = AddConsultScreenDestination(
                                    medicId = viewState.session?.medicId
                                )
                            )
                        },
                        modifier = modifier
                            .height(dimensionResource(R.dimen.button_size))
                    ) {
                        Text(
                            text = stringResource(R.string.button_NewSession),
                            fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { values ->
        PullDownToRefreshBox(
            onRefresh = { onIntent(SessionDetailsScreenIntent.Refresh) },
            loading = viewState.loading,
            paddingValues = values
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = modifier
                        .padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        )
                        .padding(
                            top = dimensionResource(R.dimen.details_text_padding)
                        ),
                    text = stringResource(R.string.details_general_info),
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
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = if (medicSide) viewState.session?.patientName ?: ""
                            else viewState.session?.medicName ?: "",
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = viewState.session?.consultDate?.format() ?: " - ",
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding),
                            bottom = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = viewState.session?.diagnostic ?: "-",
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                }

                Text(
                    modifier = modifier.padding(
                        horizontal = dimensionResource(R.dimen.details_text_padding)
                    ),
                    text = stringResource(R.string.session_details_treatment_scheme),
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
                    Text(
                        modifier = modifier.padding(
                            dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.treat_dosage_freq),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (viewState.session?.treatmentScheme?.size == 0) {
                        Text(
                            modifier = modifier
                                .padding(
                                    horizontal = dimensionResource(R.dimen.details_text_padding),
                                    vertical = dimensionResource(R.dimen.list_elem_padding)
                                )
                                .align(Alignment.CenterHorizontally),
                            text = "-",
                            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                        )
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                    viewState.session?.treatmentScheme?.forEach { treatment ->
                        Row (
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = dimensionResource(R.dimen.details_text_padding),
                                    vertical = dimensionResource(R.dimen.list_elem_padding)
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.treatment_scheme_item,
                                    treatment.treatmentName,
                                    treatment.dose,
                                    treatment.frequency
                                ),
                                fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp,
                            )
                            if (medicSide) {
                                Box (
                                    modifier = modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    IconButton(
                                        onClick = {
                                            onIntent(
                                                SessionDetailsScreenIntent.RemoveTreatment(
                                                    treatment
                                                )
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}
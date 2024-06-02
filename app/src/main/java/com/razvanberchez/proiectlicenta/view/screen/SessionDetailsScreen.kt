package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
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
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.intent.SessionDetailsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.SessionDetailsScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.SessionDetailsScreenViewState

@RootNavGraph
@Destination
@Composable
fun SessionDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    sessionId: Int
) {
    val viewModel =
        hiltViewModel<SessionDetailsScreenViewModel, SessionDetailsScreenViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(sessionId = sessionId)
            }
        )

    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SessionDetailsScreenContent(
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SessionDetailsScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: SessionDetailsScreenViewState,
    onIntent: (SessionDetailsScreenIntent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewState.loading,
        onRefresh = { onIntent(SessionDetailsScreenIntent.Refresh) })

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
                ExtendedFloatingActionButton(
                    onClick = {
                        /* TODO */
                    },
                    modifier = modifier
                        .height(dimensionResource(R.dimen.button_size))
                ) {
                    Text(
                        text = stringResource(R.string.button_text_add_consult),
                        fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { values ->
        if (!viewState.loading) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .pullRefresh(pullRefreshState)
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
                            text = stringResource(
                                R.string.session_list_Medic,
                                viewState.session?.medicName ?: ""
                            ),
                            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                        )
                        Text(
                            modifier = modifier.padding(
                                top = dimensionResource(R.dimen.details_text_padding),
                                start = dimensionResource(R.dimen.details_text_padding)
                            ),
                            text = stringResource(
                                R.string.session_list_consultDate,
                                viewState.session?.consultDate?.toLocalDate().toString()
                            ),
                            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                        )
                        Text(
                            modifier = modifier.padding(
                                top = dimensionResource(R.dimen.details_text_padding),
                                start = dimensionResource(R.dimen.details_text_padding),
                                bottom = dimensionResource(R.dimen.details_text_padding)
                            ),
                            text = stringResource(
                                R.string.session_list_Diagnostic,
                                (viewState.session?.diagnostic ?: "-")
                            ),
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
                        Divider(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        viewState.session?.treatmentScheme?.forEach { treatment ->

                            Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.details_text_padding)
                                ),
                                text = stringResource(
                                    R.string.treatment_scheme_item,
                                    treatment.treatmentName,
                                    treatment.dose,
                                    treatment.frequency
                                ),
                                fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                            )
                            Divider(
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = viewState.loading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        } else {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier.size(
                        dimensionResource(R.dimen.circular_progress_indicator_size)
                    )
                )
            }
        }
    }
}
package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.razvanberchez.proiectlicenta.presentation.intent.SessionsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.SessionsScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.view.components.PullDownToRefreshBox
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.SessionDetailsScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.SessionsScreenViewState

@RootNavGraph
@Destination
@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    bottomBarPaddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewModel: SessionsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    SessionsScreenContent(
        bottomBarPaddingValues = bottomBarPaddingValues,
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun SessionsScreenContent(
    modifier: Modifier = Modifier,
    bottomBarPaddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewState: SessionsScreenViewState,
    onIntent: (SessionsScreenIntent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(bottomBarPaddingValues),
        topBar = {
            TopBar(
                title = stringResource(R.string.menu_item_MySessions),
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
                        text = stringResource(R.string.button_NewSession),
                        fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { values ->
        PullDownToRefreshBox(
            onRefresh = { onIntent(SessionsScreenIntent.Refresh) },
            paddingValues = values,
            loading = viewState.loading
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(viewState.sessions) { session ->
                    Column(
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(R.dimen.card_padding)
                            )
                            .padding(
                                horizontal = dimensionResource(R.dimen.card_padding)
                            )
                            .clickable {
                                navigator.navigate(
                                    direction = SessionDetailsScreenDestination(
                                        sessionId = session.sessionId
                                    )
                                )
                            }
                            .fillMaxWidth()
                    ) {
                        Card(
                            modifier = modifier
                                .fillMaxWidth(),
                            colors = CardScheme.cardColors(),
                            elevation = CardScheme.cardElevation()
                        ) {
                            Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_text_padding)
                                ),
                                text = stringResource(
                                    R.string.session_list_Medic,
                                    session.medicName
                                ),
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            )
                            Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_text_padding)
                                ),
                                text = stringResource(
                                    R.string.session_list_consultDate,
                                    session.consultDate.format()
                                ),
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            )
                            if (session.diagnostic != null) {
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.card_text_padding)
                                    ),
                                    text = stringResource(
                                        R.string.session_list_Diagnostic,
                                        session.diagnostic
                                    ),
                                    fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
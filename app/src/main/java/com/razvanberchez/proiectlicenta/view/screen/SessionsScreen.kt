package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.razvanberchez.proiectlicenta.view.components.PullDownToRefreshBox
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.AddConsultScreenDestination
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

@OptIn(ExperimentalMaterial3Api::class)
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
                        navigator.navigate(
                            direction = AddConsultScreenDestination()
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
                            .clickable {
                                navigator.navigate(
                                    direction = SessionDetailsScreenDestination(
                                        sessionId = session.sessionId
                                    )
                                )
                            }
                            .fillMaxWidth()
                    ) {
                        ListItem(
                            shadowElevation = 10.dp,
                            tonalElevation = 10.dp,
                            headlineContent = { Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_text_padding)
                                ),
                                text = session.medicName,
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            ) },
                            overlineContent = { Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_text_padding)
                                ),
                                text = session.consultDate.format(),
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            ) },
                            supportingContent = { if (session.diagnostic != null) {
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.card_text_padding)
                                    ),
                                    text = session.diagnostic,
                                    fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                )
                            } },
                            trailingContent = {
                                Column (
                                    modifier = modifier.fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }

                item {
                    Spacer(
                        modifier = modifier.height(
                            dimensionResource(R.dimen.button_size)
                            + dimensionResource(R.dimen.ui_elem_padding)
                        )
                    )
                }
            }
        }
    }
}
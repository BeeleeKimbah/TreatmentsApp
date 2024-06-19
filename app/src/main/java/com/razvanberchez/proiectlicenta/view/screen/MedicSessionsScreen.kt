package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.format
import com.razvanberchez.proiectlicenta.presentation.intent.AppTheme
import com.razvanberchez.proiectlicenta.presentation.intent.MedicSessionsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.MedicSessionsScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.ThemeSelector
import com.razvanberchez.proiectlicenta.view.components.PullDownToRefreshBox
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.LoginScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.SessionDetailsScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.MedicSessionsScreenViewState

@RootNavGraph
@Destination
@Composable
fun MedicSessionsScreen(
    modifier: Modifier = Modifier,
    bottomBarPaddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewModel: MedicSessionsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    MedicSessionsScreenContent(
        bottomBarPaddingValues = bottomBarPaddingValues,
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicSessionsScreenContent(
    modifier: Modifier = Modifier,
    bottomBarPaddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewState: MedicSessionsScreenViewState,
    onIntent: (MedicSessionsScreenIntent) -> Unit
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
        }
    ) { values ->
        PullDownToRefreshBox(
            onRefresh = { onIntent(MedicSessionsScreenIntent.Refresh) },
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
                                        sessionId = session.sessionId,
                                        medicSide = true
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
                                text = session.patientName,
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
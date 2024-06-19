package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.razvanberchez.proiectlicenta.presentation.intent.MedicsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.MedicsScreenViewModel
import com.razvanberchez.proiectlicenta.view.components.PullDownToRefreshBox
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.MedicDetailsScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.MedicsScreenViewState

@RootNavGraph
@Destination
@Composable
fun MedicsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    bottomBarPaddingValues: PaddingValues,
    viewModel: MedicsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    MedicsScreenContent(
        navigator = navigator,
        bottomBarPaddingValues = bottomBarPaddingValues,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicsScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    bottomBarPaddingValues: PaddingValues,
    viewState: MedicsScreenViewState,
    onIntent: (MedicsScreenIntent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(bottomBarPaddingValues),
        topBar = {
            TopBar(
                title = stringResource(R.string.menu_item_Medics),
                navigator = navigator
            )
        }
    ) { values ->
        PullDownToRefreshBox(
            onRefresh = { onIntent(MedicsScreenIntent.Refresh) },
            loading = viewState.loading,
            paddingValues = values
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(viewState.medics) { medic ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                navigator.navigate(
                                    direction = MedicDetailsScreenDestination(
                                        medicId = medic.medicId
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
                                text = medic.name,
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            ) },
                            overlineContent = { Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_text_padding)
                                ),
                                text = medic.mainSpecialty,
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            ) },
                            supportingContent = { Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_text_padding)
                                ),
                                text = stringResource(
                                    R.string.medic_list_avg_score,
                                    if (medic.averageScore != null)
                                        "%.2f".format(medic.averageScore)
                                    else
                                        "-"
                                ),
                                fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                            ) },
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
            }
        }
    }
}
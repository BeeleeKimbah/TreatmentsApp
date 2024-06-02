package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.razvanberchez.proiectlicenta.presentation.intent.MedicDetailsScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.MedicDetailsScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.view.components.PullDownToRefreshBox
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.screen.destinations.AddReviewScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.MedicDetailsScreenViewState

@RootNavGraph
@Destination
@Composable
fun MedicDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<AddReviewScreenDestination, Boolean>,
    medicId: Int
) {
    val viewModel = hiltViewModel<MedicDetailsScreenViewModel, MedicDetailsScreenViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(medicId = medicId)
        }
    )

    val state by viewModel.viewState.collectAsStateWithLifecycle()

    resultRecipient.onNavResult {
        when (it) {
            is NavResult.Value -> {
                if (it.value) {
                    viewModel.onIntent(MedicDetailsScreenIntent.Refresh)
                }
            }

            else -> Unit
        }
    }

    MedicDetailsScreenContent(
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent,
        medicId = medicId
    )
}

@Composable
fun MedicDetailsScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: MedicDetailsScreenViewState,
    onIntent: (MedicDetailsScreenIntent) -> Unit,
    medicId: Int
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.medic_details_title),
                hasBackButton = true,
                navigator = navigator
            )
        },
        floatingActionButton = {
            if (!viewState.loading) {
                ExtendedFloatingActionButton(
                    onClick = {
                        navigator.navigate(
                            direction = AddReviewScreenDestination(
                                medicId = medicId
                            )
                        )
                    },
                    modifier = modifier
                        .height(dimensionResource(R.dimen.button_size))
                ) {
                    Text(
                        text = stringResource(R.string.text_add_review),
                        fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { values ->
        PullDownToRefreshBox(
            onRefresh = { onIntent(MedicDetailsScreenIntent.Refresh) },
            loading = viewState.loading,
            paddingValues = values
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        modifier = modifier
                            .padding(
                                top = dimensionResource(R.dimen.details_text_padding)
                            )
                            .padding(
                                horizontal = dimensionResource(R.dimen.details_text_padding)
                            ),
                        text = stringResource(R.string.details_general_info),
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
                        Text(
                            modifier = modifier.padding(
                                top = dimensionResource(R.dimen.details_text_padding),
                                start = dimensionResource(R.dimen.details_text_padding)
                            ),
                            text = stringResource(
                                R.string.medic_details_name,
                                viewState.medic?.name ?: ""
                            ),
                            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                        )
                        Text(
                            modifier = modifier.padding(
                                top = dimensionResource(R.dimen.details_text_padding),
                                start = dimensionResource(R.dimen.details_text_padding)
                            ),
                            text = stringResource(
                                R.string.medic_list_main_specialty,
                                viewState.medic?.mainSpecialty ?: ""
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
                                R.string.medic_list_avg_score,
                                if (viewState.medic?.averageScore != null)
                                    "%.2f".format(viewState.medic.averageScore)
                                else
                                    "-"
                            ),
                            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                        )
                    }
                }
                item {
                    Text(
                        modifier = modifier.padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.medic_secondary_specialties),
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
                        if (viewState.medic?.secondarySpecialties?.isNotEmpty() == true) {
                            viewState.medic.secondarySpecialties.forEach { spec ->
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.details_text_padding)
                                    ),
                                    text = spec,
                                    fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                                )
                                HorizontalDivider(
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        } else {
                            Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.details_text_padding)
                                ),
                                text = "-",
                                fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                            )
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
                item {
                    Text(
                        modifier = modifier.padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.medic_reviews),
                        fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                val reviews = viewState.medic?.reviews
                reviews?.let {
                    items(reviews) { review ->
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimensionResource(R.dimen.card_padding))
                                .padding(top = dimensionResource(R.dimen.card_padding)),
                            colors = CardScheme.cardColors(),
                            elevation = CardScheme.cardElevation()
                        ) {
                            Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.details_text_padding)
                                ),
                                text = "${review.score.value} / 5",
                                fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = modifier.padding(
                                    horizontal = dimensionResource(R.dimen.details_text_padding)
                                ),
                                text = review.text,
                                fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
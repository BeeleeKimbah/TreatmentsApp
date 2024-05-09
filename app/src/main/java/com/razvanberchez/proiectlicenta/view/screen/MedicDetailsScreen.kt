package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.repository.getMedic
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.MedicDetailsScreenViewState

@RootNavGraph
@Destination(navArgsDelegate = MedicDetailsScreenViewState::class)
@Composable
fun MedicDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: MedicDetailsScreenViewState
) {
    val medic = getMedic(viewState.medicId)
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
            ExtendedFloatingActionButton(
                onClick = {
                    /* TODO */
                },
                modifier = modifier
                    .height(dimensionResource(R.dimen.button_size))
            ) {
                Text(
                    text = stringResource(R.string.button_text_add_review),
                    fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { values ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(values)
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        modifier = modifier.padding(
                            dimensionResource(R.dimen.details_text_padding)
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
                            .padding(dimensionResource(R.dimen.list_elem_padding)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            modifier = modifier.padding(
                                top = dimensionResource(R.dimen.details_text_padding),
                                start = dimensionResource(R.dimen.details_text_padding)
                            ),
                            text = stringResource(
                                R.string.medic_details_name,
                                medic.name
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
                                medic.mainSpecialty
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
                                if (medic.averageScore != null)
                                    "%.2f".format(medic.averageScore)
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
                            dimensionResource(R.dimen.details_text_padding)
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
                            .padding(dimensionResource(R.dimen.list_elem_padding)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        if (medic.secondarySpecialties.isNotEmpty()) {
                            medic.secondarySpecialties.forEach { spec ->
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.details_text_padding)
                                    ),
                                    text = spec,
                                    fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                                )
                                Divider(
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
                            Divider(
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
                item {
                    Text(
                        modifier = modifier.padding(
                            dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(R.string.medic_reviews),
                        fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                medic.reviews.forEach { review ->
                    item {
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(R.dimen.list_elem_padding)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
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
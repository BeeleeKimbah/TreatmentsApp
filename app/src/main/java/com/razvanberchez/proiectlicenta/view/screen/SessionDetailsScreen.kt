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
import com.razvanberchez.proiectlicenta.data.repository.getSession
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.SessionDetailsScreenViewState

@RootNavGraph
@Destination(navArgsDelegate = SessionDetailsScreenViewState::class)
@Composable
fun SessionDetailsScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: SessionDetailsScreenViewState
) {
    val session = getSession(viewState.sessionId)
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
                                R.string.session_list_Medic,
                                session.medicName
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
                                session.consultDate.toLocalDate().toString()
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
                                (session.diagnostic ?: "-")
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
                        text = stringResource(R.string.session_details_treatment_scheme),
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
                                dimensionResource(R.dimen.details_text_padding)
                            ),
                            text = stringResource(R.string.treat_dosage_freq),
                            fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        session.treatmentScheme.forEach { treatment ->

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
            }
        }
    }
}
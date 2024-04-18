package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.repository.getSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailsScreen(modifier: Modifier) {
    val session = getSession()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = stringResource(R.string.session_details_title),
                        fontSize = dimensionResource(R.dimen.title_fontsize).value.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = modifier.height(dimensionResource(R.dimen.button_size)),
                        onClick = {
                            /* TODO */
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = modifier.size(dimensionResource(R.dimen.button_size)),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { values ->
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                Text(
                    modifier = modifier.padding(
                        start = dimensionResource(R.dimen.list_elem_padding)
                    ),
                    text = stringResource(R.string.session_list_Medic)
                            + ": " + session.medicName,
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp
                )
                Text(
                    modifier = modifier.padding(
                        top = dimensionResource(R.dimen.list_elem_padding),
                        start = dimensionResource(R.dimen.list_elem_padding)
                    ),
                    text = stringResource(R.string.session_list_StartDate)
                            + ": " + session.startDate,
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp
                )
                Text(
                    modifier = modifier.padding(dimensionResource(R.dimen.list_elem_padding)),
                    text = stringResource(R.string.session_lastConsult)
                            + ": " + session.lastConsult,
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp
                )

                Column(
                    modifier = modifier
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        modifier = modifier.padding(dimensionResource(R.dimen.list_elem_padding)),
                        text = stringResource(R.string.treat_dosage_freq),
                        fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.treatment_list_padding))
                            .height(dimensionResource(R.dimen.treatment_list_height))
                    ) {
                        session.treatmentScheme.forEach { treatment ->
                            item {
                                Text(
                                    text = stringResource(
                                        R.string.treatment_scheme_item,
                                        treatment.treatmentName,
                                        treatment.dose,
                                        treatment.frequency
                                    ),
                                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp
                                )
                                Divider(
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    modifier = modifier.padding(
                        top = dimensionResource(R.dimen.list_elem_padding),
                        start = dimensionResource(R.dimen.list_elem_padding)
                    ),
                    text = stringResource(R.string.session_list_Diagnostic)
                            + ": " + session.diagnostic,
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp
                )
            }

            Button(
                onClick = {
                    /* TODO */
                },
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.ui_elem_padding))
            ) {
                Text(
                    text = stringResource(R.string.button_text_add_consult),
                    fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                )
            }
        }
    }
}
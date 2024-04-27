package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.view.components.BottomBar
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.SessionsScreenViewState

@Composable
fun SessionsScreen(
    modifier: Modifier = Modifier,
    viewState: SessionsScreenViewState
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(R.string.menu_item_MySessions))
        },
        bottomBar = {
            BottomBar(0)
        }
    ) { values ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(values)
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                viewState.sessions.forEach { session ->
                    item {
                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = dimensionResource(R.dimen.ui_elem_padding),
                                    vertical = dimensionResource(R.dimen.list_elem_padding)
                                )
                                .clickable {
                                    /* TODO */
                                }
                                .fillMaxWidth()
                        ) {
                            Card(
                                modifier = modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.card_text_padding)
                                    ),
                                    text = stringResource(R.string.session_list_Medic)
                                            + ": " + session.medicName,
                                    fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                )
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.card_text_padding)
                                    ),
                                    text = stringResource(R.string.session_list_StartDate)
                                            + ": " + session.startDate.toLocalDate().toString(),
                                    fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                )
                                if (session.diagnostic != null) {
                                    Text(
                                        modifier = modifier.padding(
                                            horizontal = dimensionResource(R.dimen.card_text_padding)
                                        ),
                                        text = stringResource(R.string.session_list_Diagnostic)
                                                + ": " + session.diagnostic,
                                        fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            ExtendedFloatingActionButton(
                onClick = {
                    /* TODO */
                },
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(dimensionResource(R.dimen.ui_elem_padding))
                    .height(dimensionResource(R.dimen.button_size)),
                shape = FloatingActionButtonDefaults.extendedFabShape,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = stringResource(R.string.button_NewSession),
                    fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                )
            }
        }
    }
}

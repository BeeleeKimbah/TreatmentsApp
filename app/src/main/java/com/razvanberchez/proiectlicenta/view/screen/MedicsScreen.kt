package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.razvanberchez.proiectlicenta.view.viewstate.MedicsScreenViewState

@Composable
fun MedicsScreen(
    modifier: Modifier = Modifier,
    viewState: MedicsScreenViewState
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(title = stringResource(R.string.menu_item_Medics))
        },
        bottomBar = {
            BottomBar(1)
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
                viewState.medics.forEach { medic ->
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
                                            + ": " + medic.name,
                                    fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                )
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.card_text_padding)
                                    ),
                                    text = stringResource(R.string.medic_list_main_specialty)
                                            + ": " + medic.mainSpecialty,
                                    fontSize = dimensionResource(R.dimen.list_elem_fontsize).value.sp
                                )
                                Text(
                                    modifier = modifier.padding(
                                        horizontal = dimensionResource(R.dimen.card_text_padding)
                                    ),
                                    text = stringResource(R.string.medic_list_avg_score)
                                            + ": " + (if (medic.averageScore != null)
                                                "%.2f".format(medic.averageScore) else "-"),
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

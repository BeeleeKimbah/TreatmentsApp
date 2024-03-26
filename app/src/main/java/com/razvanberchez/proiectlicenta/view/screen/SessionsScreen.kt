package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.repository.getSessions

@Composable
fun SessionsScreen(values: PaddingValues, modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(values),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            getSessions().forEach { session ->
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable {
                                /* TODO */
                            }
                            .fillMaxWidth()
                    ) {
                        Card (
                            modifier = modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Text(
                                modifier = modifier.padding(horizontal = 16.dp),
                                text = stringResource(R.string.session_list_Medic)
                                        + ": " + session.medicName,
                                fontSize = 20.sp
                            )
                            Text(
                                modifier = modifier.padding(horizontal = 16.dp),
                                text = stringResource(R.string.session_list_StartDate)
                                        + ": " + session.startDate.toString(),
                                fontSize = 20.sp
                            )
                            if (session.diagnostic != null) {
                                Text(
                                    modifier = modifier.padding(horizontal = 16.dp),
                                    text = stringResource(R.string.session_list_Diagnostic)
                                            + ": " + session.diagnostic,
                                    fontSize = 20.sp
                                )
                            }
                        }
                        //Divider(color = Color.Black)
                    }
                }
            }
            item { Spacer(modifier = modifier.height(76.dp)) }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.button_NewSession),
                fontSize = 25.sp
            )
        }
    }
}

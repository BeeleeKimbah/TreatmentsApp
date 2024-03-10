package com.razvanberchez.proiectlicenta.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.resources.sessionList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionsScreen(values: PaddingValues, modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(values)
                .background(Color(255, 252, 245)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            sessionList.forEach { session ->
                item {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {

                            }
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Medic: " + session.medicName,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Text(
                            text = "Primul consult: " + session.startDate.toString(),
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        if (session.diagnostic != null) {
                            Text(
                                text = "Diagnostic: " + session.diagnostic,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                        Divider(color = Color.Black)
                    }
                }
            }
            item { Spacer(modifier = modifier.height(76.dp)) }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(31, 57, 129)
            )
        ) {
            Text(
                text = "Sesiune noua",
                fontSize = 25.sp,
                color = Color.White
            )
        }
    }
}
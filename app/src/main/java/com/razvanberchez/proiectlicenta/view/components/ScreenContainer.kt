package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.view.screen.SessionsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContainer(
    modifier: Modifier, selectedItem: String,
    openDrawer: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        text = selectedItem,
                        fontSize = dimensionResource(R.dimen.title_fontsize).value.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = modifier.height(dimensionResource(R.dimen.button_size)),
                        onClick = openDrawer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            modifier = modifier.size(dimensionResource(R.dimen.button_size))
                        )
                    }
                }
            )
        }
    ) { values ->
        SessionsScreen(values, modifier)
    }
}
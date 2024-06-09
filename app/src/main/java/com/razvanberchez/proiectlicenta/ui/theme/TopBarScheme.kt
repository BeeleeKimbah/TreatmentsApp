package com.razvanberchez.proiectlicenta.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object TopBarScheme {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun topBarColors(
        containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        navigationIconContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        actionIconContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
    ) : TopAppBarColors {
        return TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = navigationIconContentColor,
            actionIconContentColor = actionIconContentColor
        )
    }
}
package com.razvanberchez.proiectlicenta.ui.theme

import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object CardScheme {
    @Composable
    fun cardColors(): CardColors {
        return CardDefaults.cardColors(
            containerColor = colorScheme.secondaryContainer,
            contentColor = colorScheme.onSecondaryContainer
        )
    }

    @Composable
    fun cardElevation(): CardElevation {
        return CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    }
}
package com.razvanberchez.proiectlicenta.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class TimeSlotItemColors(
    val disabledContainerColor: Color,
    val enabledContainerColor: Color,
    val selectedContainerColor: Color,
    val disabledTextColor: Color,
    val enabledTextColor: Color,
    val selectedTextColor: Color
)

object TimeSlotItemDefaults {

    @Composable
    fun timeSlotItemColors(
        disabledContainerColor: Color = Color.Gray,
        enabledContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
        selectedContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        disabledTextColor: Color = Color.Black,
        enabledTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedTextColor: Color =MaterialTheme.colorScheme.onPrimaryContainer
    ): TimeSlotItemColors {
        return TimeSlotItemColors(
            disabledContainerColor,
            enabledContainerColor,
            selectedContainerColor,
            disabledTextColor,
            enabledTextColor,
            selectedTextColor
        )
    }
}
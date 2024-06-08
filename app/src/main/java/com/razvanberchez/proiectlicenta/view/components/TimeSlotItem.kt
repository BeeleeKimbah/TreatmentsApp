package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.model.format

@Composable
fun TimeSlotItem(
    modifier: Modifier = Modifier,
    availableIntervals: List<TimeSlot>,
    selectedInterval: TimeSlot,
    interval: TimeSlot,
    onSelectInterval: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.timeslot_padding))
            .background(
                if (availableIntervals.contains(interval))
                    if (selectedInterval == interval)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                else
                    Color.Gray,
                shape = ShapeDefaults.Small
            )
            .clickable {
                if (availableIntervals.contains(interval)) {
                    onSelectInterval()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = interval.format(),
            color = if (availableIntervals.contains(interval))
                        if (selectedInterval == interval)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSecondaryContainer
                    else
                        Color.Black,
            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
        )
    }
}
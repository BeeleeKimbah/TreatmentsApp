package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.model.TimeSlot
import com.razvanberchez.proiectlicenta.data.model.format
import com.razvanberchez.proiectlicenta.ui.theme.TimeSlotItemColors
import com.razvanberchez.proiectlicenta.ui.theme.TimeSlotItemDefaults

@Composable
fun TimeSlotItem(
    modifier: Modifier = Modifier,
    availableIntervals: List<TimeSlot>,
    selectedInterval: TimeSlot,
    interval: TimeSlot,
    onSelectInterval: () -> Unit,
    colors: TimeSlotItemColors = TimeSlotItemDefaults.timeSlotItemColors()
) {
    Box(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.timeslot_padding))
            .background(
                if (availableIntervals.contains(interval))
                    if (selectedInterval == interval)
                        colors.selectedContainerColor
                    else
                        colors.enabledContainerColor
                else
                    colors.disabledContainerColor,
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
                            colors.selectedTextColor
                        else
                            colors.enabledTextColor
                    else
                        colors.disabledTextColor,
            fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
        )
    }
}
package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.razvanberchez.proiectlicenta.data.model.allSlots
import com.razvanberchez.proiectlicenta.data.model.format

@Composable
fun TimeSlotGrid(
    modifier: Modifier = Modifier,
    availableIntervals: List<TimeSlot>,
    selectedInterval: TimeSlot,
    onIntervalSelected: (TimeSlot) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = dimensionResource(R.dimen.timeslot_grid_bottom_padding))
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.ui_elem_padding)),
            columns = GridCells.Fixed(2)
        ) {
            items(allSlots.toList()) { interval ->
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.timeslot_padding))
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
                                onIntervalSelected(interval)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = interval.format(),
                        color = if (availableIntervals.contains(
                                interval
                            )
                        )
                            if (selectedInterval == interval)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSecondaryContainer
                        else
                            Color.Black,
                        fontSize =
                        dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                }
            }
        }
    }
}
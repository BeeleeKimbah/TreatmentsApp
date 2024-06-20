package com.razvanberchez.proiectlicenta.view.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.format
import com.razvanberchez.proiectlicenta.presentation.getDateWithoutTime
import com.razvanberchez.proiectlicenta.presentation.getNextDay
import com.razvanberchez.proiectlicenta.presentation.getYearOfNextDay
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
class SelectableConsultDates: SelectableDates {
    override fun isSelectableYear(year: Int): Boolean {
        return year >= Date().getYearOfNextDay()
    }

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= Date().getNextDay().getDateWithoutTime().time
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    modifier: Modifier = Modifier,
    selectedDate: Date,
    onSelectDate: (Long) -> Unit,
    @StringRes title: Int
) {
    var datePickerOpen by remember {
        mutableStateOf(false)
    }

    val state = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.time,
        selectableDates = SelectableConsultDates()
    )

    OutlinedTextField(
        modifier = modifier
            .padding(dimensionResource(R.dimen.ui_elem_padding))
            .fillMaxWidth(),
        value = selectedDate.format(),
        onValueChange = {},
        readOnly = true,
        label = {
            Text(
                text = stringResource(title),
                fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
            )
        },
        trailingIcon = {
            IconButton(onClick = { datePickerOpen = true }) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null
                )
            }
        },
        shape = ShapeDefaults.Medium,
        singleLine = true
    )

    if (datePickerOpen) {
        DatePickerDialog(
            onDismissRequest = { datePickerOpen = false },
            confirmButton = {
                Button(
                    onClick = {
                        onSelectDate(state.selectedDateMillis!!)
                        datePickerOpen = false
                    }
                ) {
                    Text(text = stringResource(R.string.date_picker_ok))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        datePickerOpen = false
                    }
                ) {
                    Text(text = stringResource(R.string.date_picker_dismiss))
                }
            }
        ) {
            DatePicker(
                state = state,
                title = {
                    Text(
                        modifier = modifier.padding(
                            dimensionResource(R.dimen.ui_elem_padding)
                        ),
                        text = stringResource(R.string.date_picker_title)
                    )
                }
            )
        }
    }
}
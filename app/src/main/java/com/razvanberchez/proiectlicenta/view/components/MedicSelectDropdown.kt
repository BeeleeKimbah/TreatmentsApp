package com.razvanberchez.proiectlicenta.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
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
import com.razvanberchez.proiectlicenta.data.model.Medic
import com.razvanberchez.proiectlicenta.data.model.MedicListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicSelectDropdown(
    modifier: Modifier = Modifier,
    allMedics: List<MedicListItem>,
    selectedMedic: Medic?,
    onSelectMedic: (String) -> Unit
) {
    var medicSelectExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier
            .padding(dimensionResource(R.dimen.ui_elem_padding))
            .fillMaxWidth(),
        expanded = medicSelectExpanded,
        onExpandedChange = { medicSelectExpanded = it }
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            placeholder = {
                Text(
                    text = stringResource(R.string.medic_picker_placeholder)
                )
            },
            value = selectedMedic?.let {
                stringResource(
                    R.string.medic_picker_medicItem,
                    selectedMedic.name,
                    selectedMedic.mainSpecialty
                )
            } ?: "",
            onValueChange = {},
            label = {
                Text(
                    text = stringResource(R.string.medic_picker_label),
                    fontSize =
                    dimensionResource(R.dimen.textfield_fontsize).value.sp
                )
            },
            shape = ShapeDefaults.Medium,
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = medicSelectExpanded)
            }
        )
        ExposedDropdownMenu(
            expanded = medicSelectExpanded,
            onDismissRequest = { medicSelectExpanded = false },
            modifier = modifier.fillMaxWidth()
        ) {
            allMedics.forEach { medic ->
                DropdownMenuItem(
                    modifier = modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    text = {
                        Text(
                            text = stringResource(
                                R.string.medic_picker_medicItem,
                                medic.name,
                                medic.mainSpecialty
                            )
                        )
                    },
                    onClick = {
                        onSelectMedic(medic.medicId)
                        medicSelectExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}
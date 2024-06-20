package com.razvanberchez.proiectlicenta.view.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.intent.AddTreatmentScreenIntent
import com.razvanberchez.proiectlicenta.presentation.intent.LoginScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.AddTreatmentScreenViewModel
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.AddTreatmentScreenViewState

@RootNavGraph
@Destination
@Composable
fun AddTreatmentScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    sessionId: String
) {
    val viewModel = hiltViewModel<AddTreatmentScreenViewModel, AddTreatmentScreenViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(sessionId = sessionId)
        }
    )
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    AddTreatmentScreenContent(
        viewState = state,
        navigator = navigator,
        onIntent = viewModel::onIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTreatmentScreenContent(
    modifier: Modifier = Modifier,
    viewState: AddTreatmentScreenViewState,
    navigator: DestinationsNavigator,
    onIntent: (AddTreatmentScreenIntent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.button_text_add_treatment),
                hasBackButton = true,
                navigator = navigator
            )
        }
    ) { values ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(values)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth(),
                value = viewState.treatmentName,
                onValueChange = { onIntent(AddTreatmentScreenIntent.ModifyTreatment(it)) },
                label = {
                    Text(
                        text = stringResource(R.string.treatment_name_field),
                        fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                    )
                },
                shape = ShapeDefaults.Medium,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth(),
                value = if (viewState.dose == null || viewState.dose == 0) ""
                        else viewState.dose.toString(),
                onValueChange = {
                    try {
                        onIntent(AddTreatmentScreenIntent.ModifyDose(it.toIntOrNull()))
                    } catch (e: Exception) {
                        e.message?.let { error -> Log.w(TAG, error) }
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.text_dose_add),
                        fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                    )
                },
                shape = ShapeDefaults.Medium,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth(),
                value = if (viewState.frequency == null || viewState.frequency == 0) ""
                else viewState.frequency.toString(),
                onValueChange = {
                    try {
                        onIntent(AddTreatmentScreenIntent.ModifyFrequency(it.toIntOrNull()))
                    } catch (e: Exception) {
                        e.message?.let { error -> Log.w(TAG, error) }
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.frequency_text_add),
                        fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                    )
                },
                shape = ShapeDefaults.Medium,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                modifier = modifier
                    .padding(dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth(),
                value = if (viewState.applications == null || viewState.applications == 0) ""
                else viewState.applications.toString(),
                onValueChange = {
                    try {
                        onIntent(AddTreatmentScreenIntent.ModifyApplications(it.toIntOrNull()))
                    } catch (e: Exception) {
                        e.message?.let { error -> Log.w(TAG, error) }
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.applications_text_add),
                        fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                    )
                },
                shape = ShapeDefaults.Medium,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )

            FilledTonalButton(
                modifier = modifier
                    .padding(dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_size)),
                onClick = {
                    onIntent(AddTreatmentScreenIntent.AddTreatment)
                },
                enabled = viewState.addButtonEnabled
            ) {
                Text(
                    text = "Adaugă tratament",
                    fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                )
            }
        }
    }
}
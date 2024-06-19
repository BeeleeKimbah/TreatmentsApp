package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.intent.AddTreatmentScreenIntent
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
                title = stringResource(R.string.button_NewSession),
                hasBackButton = true,
                navigator = navigator
            )
        }
    ) { values ->
        values
    }
}
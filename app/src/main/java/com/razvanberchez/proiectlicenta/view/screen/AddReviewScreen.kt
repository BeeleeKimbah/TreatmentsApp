package com.razvanberchez.proiectlicenta.view.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.model.Score
import com.razvanberchez.proiectlicenta.presentation.intent.AddReviewScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.AddReviewScreenViewModel
import com.razvanberchez.proiectlicenta.ui.theme.CardScheme
import com.razvanberchez.proiectlicenta.view.components.StarRatingBar
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.AddReviewScreenViewState

@RootNavGraph
@Destination
@Composable
fun AddReviewScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    medicId: Int
) {
    BackHandler {
        resultNavigator.navigateBack(result = false)
    }

    val viewModel = hiltViewModel<AddReviewScreenViewModel, AddReviewScreenViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(medicId = medicId)
        }
    )

    val state by viewModel.viewState.collectAsStateWithLifecycle()

    AddReviewScreenContent(
        navigator = navigator,
        resultNavigator = resultNavigator,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun AddReviewScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    viewState: AddReviewScreenViewState,
    onIntent: (AddReviewScreenIntent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(R.string.text_add_review),
                hasBackButton = true,
                navigator = navigator
            )
        }
    ) { values ->
        if (!viewState.loading) {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(values)
                    .fillMaxSize()
            ) {
                Text(
                    modifier = modifier
                        .padding(
                            top = dimensionResource(R.dimen.details_text_padding)
                        )
                        .padding(
                            horizontal = dimensionResource(R.dimen.details_text_padding)
                        ),
                    text = stringResource(R.string.details_general_info),
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                    fontWeight = FontWeight.Bold
                )

                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.card_padding)),
                    colors = CardScheme.cardColors(),
                    elevation = CardScheme.cardElevation()
                ) {
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(
                            R.string.medic_details_name,
                            viewState.medic?.name ?: ""
                        ),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(
                            R.string.medic_list_main_specialty,
                            viewState.medic?.mainSpecialty ?: ""
                        ),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding),
                            bottom = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(
                            R.string.medic_list_avg_score,
                            if (viewState.medic?.averageScore != null)
                                "%.2f".format(viewState.medic.averageScore)
                            else
                                "-"
                        ),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                }

                Text(
                    modifier = modifier.padding(
                        horizontal = dimensionResource(R.dimen.details_text_padding)
                    ),
                    text = stringResource(R.string.text_header_score),
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                    fontWeight = FontWeight.Bold
                )

                StarRatingBar(
                    maxStars = 5,
                    rating = viewState.score.value,
                    onRatingChanged = {
                        onIntent(
                            AddReviewScreenIntent
                                .ModifyRating(Score.getScore(it) ?: Score.ONE)
                        )
                    }
                )

                Text(
                    modifier = modifier.padding(
                        horizontal = dimensionResource(R.dimen.details_text_padding)
                    ),
                    text = stringResource(R.string.text_header_review),
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                    fontWeight = FontWeight.Bold
                )

                BasicTextField(
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.secondaryContainer),
                    value = viewState.reviewBody,
                    onValueChange = {
                        onIntent(AddReviewScreenIntent.ModifyReviewBody(it))
                    },
                    minLines = 5,
                    maxLines = 16,
                    textStyle = TextStyle(
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(
                        onGo = {
                            /* TODO: add review to medic entity */
                            resultNavigator.navigateBack(result = true)
                        }
                    )
                ) { innerTextField ->
                    Row(
                        Modifier
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                RoundedCornerShape(percent = 80)
                            )
                            .padding(dimensionResource(R.dimen.ui_elem_padding))
                    ) {
                        innerTextField()
                    }
                }

                Button(
                    modifier = modifier
                        .padding(dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_size)),
                    onClick = {
                        /* TODO: add review to medic entity */
                        resultNavigator.navigateBack(result = true)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_text_add_review),
                        fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                    )
                }
            }
        } else {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier.size(
                        dimensionResource(R.dimen.circular_progress_indicator_size)
                    )
                )
            }
        }
    }
}
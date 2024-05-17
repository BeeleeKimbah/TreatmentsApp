package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.data.repository.getMedic
import com.razvanberchez.proiectlicenta.view.components.StarRatingBar
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.AddReviewScreenViewState

@RootNavGraph
@Destination(navArgsDelegate = AddReviewScreenViewState::class)
@Composable
fun AddReviewScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: AddReviewScreenViewState
) {
    val medic = getMedic(viewState.medicId)
    var reviewBody by rememberSaveable {
        mutableStateOf(viewState.reviewBody)
    }

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
        LazyColumn (
            modifier = modifier
                .padding(values)
                .fillMaxSize()
        ) {
            item {
                Text(
                    modifier = modifier.padding(
                        dimensionResource(R.dimen.details_text_padding)
                    ),
                    text = stringResource(R.string.details_general_info),
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.list_elem_padding)),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        modifier = modifier.padding(
                            top = dimensionResource(R.dimen.details_text_padding),
                            start = dimensionResource(R.dimen.details_text_padding)
                        ),
                        text = stringResource(
                            R.string.medic_details_name,
                            medic.name
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
                            medic.mainSpecialty
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
                            if (medic.averageScore != null)
                                "%.2f".format(medic.averageScore)
                            else
                                "-"
                        ),
                        fontSize = dimensionResource(R.dimen.details_list_fontsize).value.sp
                    )
                }
            }
            item {
                Text(
                    modifier = modifier.padding(
                        dimensionResource(R.dimen.details_text_padding)
                    ),
                    text = stringResource(R.string.text_header_score),
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                var rating by rememberSaveable {
                    mutableStateOf(1)
                }
                StarRatingBar(
                    maxStars = 5,
                    rating = rating,
                    onRatingChanged = {
                        rating = it
                    }
                )
            }
            item {
                Text(
                    modifier = modifier.padding(
                        dimensionResource(R.dimen.details_text_padding)
                    ),
                    text = stringResource(R.string.text_header_review),
                    fontSize = dimensionResource(R.dimen.details_text_fontsize).value.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                BasicTextField(
                    modifier = modifier
                        .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                        .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.secondaryContainer),
                    value = reviewBody,
                    onValueChange = { reviewBody = it },
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
                            navigator.popBackStack()
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
            }
            item {
                Button(
                    modifier = modifier
                        .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                        .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.button_size)),
                    onClick = {
                        /* TODO: add review to medic entity */
                        navigator.popBackStack()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_text_add_review),
                        fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                    )
                }
            }
        }
    }
}
package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.presentation.intent.LoginScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.LoginScreenViewModel
import com.razvanberchez.proiectlicenta.view.screen.destinations.LoginScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.RegisterScreenDestination
import com.razvanberchez.proiectlicenta.view.screen.destinations.SessionsScreenDestination
import com.razvanberchez.proiectlicenta.view.viewstate.LoginScreenViewState

@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LoginScreenContent(
        modifier = modifier,
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier,
    navigator: DestinationsNavigator,
    viewState: LoginScreenViewState,
    onIntent: (LoginScreenIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var showPassword by remember {
            mutableStateOf(value = false)
        }

        Text(
            text = stringResource(R.string.text_welcome_back),
            fontSize = dimensionResource(R.dimen.title_fontsize).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
        )
        Text(
            text = stringResource(R.string.text_login_message),
            fontSize = dimensionResource(R.dimen.subtitle_fontsize).value.sp,
            modifier = modifier.padding(bottom = dimensionResource(R.dimen.title_uielems_sep))
        )
        OutlinedTextField(
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth(),
            value = viewState.email,
            onValueChange = { onIntent(LoginScreenIntent.ModifyEmail(it)) },
            label = {
                Text(
                    text = stringResource(R.string.input_text_email),
                    fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                )
            },
            shape = ShapeDefaults.Medium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            maxLines = 1
        )
        OutlinedTextField(
            modifier = modifier
                .padding(dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth(),
            value = viewState.password,
            onValueChange = { onIntent(LoginScreenIntent.ModifyPassword(it)) },
            label = {
                Text(
                    text = stringResource(R.string.input_text_password),
                    fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                )
            },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    if (viewState.loginButtonEnabled) {
                        navigator.navigate(SessionsScreenDestination) {
                            popUpTo(LoginScreenDestination.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            ),
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            },
            shape = ShapeDefaults.Medium,
            maxLines = 1
        )

        FilledTonalButton(
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_size)),
            onClick = {
                onIntent(LoginScreenIntent.Login)
                navigator.navigate(direction = SessionsScreenDestination) {
                    popUpTo(route = LoginScreenDestination.route) {
                        inclusive = true
                    }
                }
            },
            enabled = viewState.loginButtonEnabled
        ) {
            Text(
                text = stringResource(R.string.button_text_login),
                fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
            )
        }

        Button(
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_size)),
            onClick = {
                navigator.navigate(direction = RegisterScreenDestination)
            }
        ) {
            Text(
                text = stringResource(R.string.button_text_register),
                fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
            )
        }
    }
}
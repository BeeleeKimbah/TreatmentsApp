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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
import com.razvanberchez.proiectlicenta.presentation.intent.RegisterScreenIntent
import com.razvanberchez.proiectlicenta.presentation.viewmodel.RegisterScreenViewModel
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.RegisterScreenViewState

@RootNavGraph
@Destination
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    RegisterScreenContent(
        navigator = navigator,
        viewState = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: RegisterScreenViewState,
    onIntent: (RegisterScreenIntent) -> Unit
) {
    if (viewState.registered)
        navigator.navigateUp()

    val localFocusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.button_text_register),
                hasBackButton = true,
                navigator = navigator,
                hasLogoutButton = false
            )
        }
    ) { values ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(values)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var showPassword by remember {
                mutableStateOf(value = false)
            }
            var showPasswordConfirm by remember {
                mutableStateOf(value = false)
            }

            Text(
                text = stringResource(R.string.text_welcome),
                fontSize = dimensionResource(R.dimen.title_fontsize).value.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.text_register_message),
                fontSize = dimensionResource(R.dimen.subtitle_fontsize).value.sp,
                modifier = modifier.padding(bottom = dimensionResource(R.dimen.title_uielems_sep))
            )
            OutlinedTextField(
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth(),
                value = viewState.email,
                onValueChange = { onIntent(RegisterScreenIntent.ModifyEmail(it)) },
                label = {
                    Text(
                        text = stringResource(R.string.input_text_email),
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
                value = viewState.password,
                onValueChange = { onIntent(RegisterScreenIntent.ModifyPassword(it)) },
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
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
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
            OutlinedTextField(
                modifier = modifier
                    .padding(dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth(),
                value = viewState.confirmPassword,
                onValueChange = { onIntent(RegisterScreenIntent.ModifyConfirmPassword(it)) },
                label = {
                    Text(
                        text = stringResource(R.string.input_text_confirm_password),
                        fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                    )
                },
                visualTransformation = if (showPasswordConfirm)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        if (viewState.registerButtonEnabled) {
                            onIntent(RegisterScreenIntent.Register)
                        }
                    }
                ),
                trailingIcon = {
                    if (showPasswordConfirm) {
                        IconButton(onClick = { showPasswordConfirm = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPasswordConfirm = true }) {
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

            viewState.errorMessage?.let {
                Text(
                    text = viewState.errorMessage,
                    color = Color.Red,
                    fontSize = dimensionResource(R.dimen.validation_error_fontsize).value.sp,
                    modifier = modifier.padding(dimensionResource(R.dimen.ui_elem_padding))
                )
            }

            Button(
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_size)),
                onClick = {
                    onIntent(RegisterScreenIntent.Register)
                },
                enabled = viewState.registerButtonEnabled
            ) {
                Text(
                    text = stringResource(R.string.button_text_register),
                    fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
                )
            }
        }
    }
}
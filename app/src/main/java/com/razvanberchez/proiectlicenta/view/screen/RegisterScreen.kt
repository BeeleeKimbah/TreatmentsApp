package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.razvanberchez.proiectlicenta.R
import com.razvanberchez.proiectlicenta.view.components.TopBar
import com.razvanberchez.proiectlicenta.view.viewstate.RegisterScreenViewState

@RootNavGraph
@Destination
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewState: RegisterScreenViewState = RegisterScreenViewState()
) {
    val localFocusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopBar(
                stringResource(R.string.button_text_register),
                true,
                navigator,
                false
            )
        }
    ) { values ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(values),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var username by rememberSaveable {
                mutableStateOf("")
            }
            var email by rememberSaveable {
                mutableStateOf("")
            }
            var password by rememberSaveable {
                mutableStateOf("")
            }
            var passwordConfirm by rememberSaveable {
                mutableStateOf("")
            }
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
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding)),
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        text = stringResource(R.string.input_text_username),
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
                value = email,
                onValueChange = { email = it },
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
                value = password,
                onValueChange = { password = it },
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
                    onSend = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
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
                value = passwordConfirm,
                onValueChange = { passwordConfirm = it },
                label = {
                    Text(
                        text = stringResource(R.string.input_text_confirm_password),
                        fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                    )
                },
                visualTransformation = if (showPasswordConfirm) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
//                        if (viewState.registerButtonEnabled) {
//                            /* TODO: Implement Register button and call it here too */
//                        }
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

            Button(
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                    .padding(horizontal = dimensionResource(R.dimen.ui_elem_padding))
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.button_size)),
                onClick = {
                    /* TODO */
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
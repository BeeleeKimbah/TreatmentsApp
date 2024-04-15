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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.razvanberchez.proiectlicenta.R

@Composable
fun LoginScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var username by rememberSaveable {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var showPassword by remember {
            mutableStateOf(value = false)
        }

        Text(
            text = stringResource(R.string.text_welcome_back),
            fontSize = dimensionResource(R.dimen.title_fontsize).value.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = dimensionResource(R.dimen.ui_elem_padding))
        )
        Text(
            text = stringResource(R.string.text_login_message),
            fontSize = dimensionResource(R.dimen.subtitle_fontsize).value.sp,
            modifier = modifier.padding(bottom = dimensionResource(R.dimen.title_uielems_sep))
        )
        OutlinedTextField(
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = stringResource(R.string.input_text_username),
                    fontSize = dimensionResource(R.dimen.textfield_fontsize).value.sp
                )
            },
            shape = ShapeDefaults.Medium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            modifier = modifier
                .padding(vertical = dimensionResource(R.dimen.ui_elem_padding))
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
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    /* TODO: Implement Login button and call it here too */
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
            shape = ShapeDefaults.Medium
        )

        FilledTonalButton(
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_size)),
            onClick = {
                /* TODO */
            }
        ) {
            Text(
                text = stringResource(R.string.button_text_login),
                fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
            )
        }

        Button(
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.ui_elem_padding))
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.button_size)),
            onClick = {
                /* TODO */
            }
        ) {
            Text(
                text = stringResource(R.string.button_text_register),
                fontSize = dimensionResource(R.dimen.button_text_fontsize).value.sp
            )
        }
    }
}
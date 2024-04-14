package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
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
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = 20.dp)
        )
        Text(
            text = stringResource(R.string.text_login_message),
            fontSize = 25.sp,
            modifier = modifier.padding(bottom = 80.dp)
        )
        OutlinedTextField(
            modifier = modifier.padding(vertical = 10.dp).fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(text = stringResource(R.string.input_text_username), fontSize = 16.sp)
            },
            shape = ShapeDefaults.Medium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            modifier = modifier.padding(top = 10.dp).fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = stringResource(R.string.input_text_password), fontSize = 16.sp)
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
            modifier = modifier.padding(top = 16.dp).fillMaxWidth(),
            onClick = {
                /* TODO */
            },
        ) {
            Text(text = stringResource(R.string.button_text_login), fontSize = 25.sp)
        }

        Button(
            modifier = modifier.padding(top = 16.dp).fillMaxWidth(),
            onClick = {
                /* TODO */
            },
        ) {
            Text(text = stringResource(R.string.button_text_register), fontSize = 25.sp)
        }
    }
}
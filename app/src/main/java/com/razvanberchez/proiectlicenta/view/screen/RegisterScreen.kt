package com.razvanberchez.proiectlicenta.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.razvanberchez.proiectlicenta.R

@Composable
fun RegisterScreen(modifier: Modifier) {
    Box (
        modifier = Modifier.padding(16.dp)
    ) {
        IconButton(onClick = { /*TODO*/ }, modifier = modifier.size(60.dp)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = modifier.size(50.dp)
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
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
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.text_register_message),
            fontSize = 22.sp,
            modifier = modifier.padding(bottom = 50.dp)
        )
        OutlinedTextField(
            modifier = modifier.padding(top = 10.dp),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(text = stringResource(R.string.input_text_username), fontSize = 16.sp)
            },
            shape = ShapeDefaults.Medium
        )
        OutlinedTextField(
            modifier = modifier.padding(top = 10.dp),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(R.string.input_text_email), fontSize = 16.sp)
            },
            shape = ShapeDefaults.Medium
        )
        OutlinedTextField(
            modifier = modifier.padding(top = 10.dp),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
        OutlinedTextField(
            modifier = modifier.padding(top = 10.dp),
            value = passwordConfirm,
            onValueChange = { passwordConfirm = it },
            label = {
                Text(text = stringResource(R.string.input_text_confirm_password), fontSize = 16.sp)
            },
            visualTransformation = if (showPasswordConfirm) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
            shape = ShapeDefaults.Medium
        )

        Button(
            modifier = modifier
                .padding(top = 50.dp),
            onClick = {},
        ) {
            Text(text = stringResource(R.string.button_text_register), fontSize = 25.sp)
        }
    }
}
package com.razvanberchez.proiectlicenta.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.razvanberchez.proiectlicenta.presentation.intent.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeSelector {
    private val themeState = MutableStateFlow(AppTheme.SYSTEM)
    val theme = themeState.asStateFlow()
    @Composable
    fun isDarkTheme(): Boolean {
        val t by theme.collectAsStateWithLifecycle()

        return when (t) {
            AppTheme.SYSTEM -> isSystemInDarkTheme()
            AppTheme.DARK -> true
            AppTheme.LIGHT -> false
        }
    }

    fun setTheme(newTheme: AppTheme) {
        themeState.value = newTheme
    }
}
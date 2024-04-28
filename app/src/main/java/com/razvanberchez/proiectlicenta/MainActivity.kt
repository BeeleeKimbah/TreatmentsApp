package com.razvanberchez.proiectlicenta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.razvanberchez.proiectlicenta.ui.theme.AppTheme
import com.razvanberchez.proiectlicenta.view.screen.LoginScreen
import com.razvanberchez.proiectlicenta.view.screen.MedicsScreen
import com.razvanberchez.proiectlicenta.view.screen.RegisterScreen
import com.razvanberchez.proiectlicenta.view.screen.SessionDetailsScreen
import com.razvanberchez.proiectlicenta.view.screen.SessionsScreen
import com.razvanberchez.proiectlicenta.view.viewstate.LoginScreenViewState
import com.razvanberchez.proiectlicenta.view.viewstate.MedicsScreenViewState
import com.razvanberchez.proiectlicenta.view.viewstate.RegisterScreenViewState
import com.razvanberchez.proiectlicenta.view.viewstate.SessionDetailsScreenViewState
import com.razvanberchez.proiectlicenta.view.viewstate.SessionsScreenViewState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    tonalElevation = 5.dp
                ) {
                    //RegisterScreen(viewState = RegisterScreenViewState())
                    //LoginScreen(viewState = LoginScreenViewState())
                    //SessionsScreen(viewState = SessionsScreenViewState())
                    //SessionDetailsScreen(viewState = SessionDetailsScreenViewState())
                    MedicsScreen(viewState = MedicsScreenViewState())
                }
            }
        }
    }
}
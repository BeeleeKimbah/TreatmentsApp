package com.razvanberchez.proiectlicenta

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.razvanberchez.proiectlicenta.ui.theme.AppTheme
import com.razvanberchez.proiectlicenta.view.components.NavMenu
import com.razvanberchez.proiectlicenta.view.screen.AddConsultScreen
import com.razvanberchez.proiectlicenta.view.screen.LoginScreen
import com.razvanberchez.proiectlicenta.view.screen.RegisterScreen
import com.razvanberchez.proiectlicenta.view.screen.SessionDetailsScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    tonalElevation = 5.dp
                ) {
                    //RegisterScreen(modifier = Modifier)
                    //LoginScreen(modifier = Modifier)
                    //NavMenu(modifier = Modifier)
                    SessionDetailsScreen(modifier = Modifier)
                    //AddConsultScreen(modifier = Modifier)
                }
            }
        }
    }
}
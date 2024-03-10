package com.razvanberchez.proiectlicenta

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import com.razvanberchez.proiectlicenta.ui.theme.MyApplicationTheme
import com.razvanberchez.proiectlicenta.view.components.NavMenu

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                NavMenu(modifier = Modifier)
            }
        }
    }
}
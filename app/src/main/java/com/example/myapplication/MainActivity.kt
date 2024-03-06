package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

data class Treatment (
    val treatmentName: String,
    val startDate: LocalDate,
    val usages: Int,
    val frequency: Int,
    val applications: Int
)

data class Session(
    val startDate: LocalDate,
    val lastConsult: LocalDate,
    val medicName: String,
    val treatmentScheme: List<Treatment>,
    val diagnostic: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navItems = listOf(
                    NavigationItem(
                        title = "Sesiunile mele",
                        selectedIcon = Icons.Filled.List,
                        unselectedIcon = Icons.Outlined.List
                    ),
                    NavigationItem(
                        title = "Medici",
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person
                    ),
                    NavigationItem(
                        title = "Setari si Preferinte",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings
                    ),
                    NavigationItem(
                        title = "Logout",
                        selectedIcon = Icons.Filled.ExitToApp,
                        unselectedIcon = Icons.Outlined.ExitToApp
                    )
                )

                val backgroundGradientBrush = Brush.linearGradient(
                    colors = listOf(
                        Color(104, 123, 216),
                        Color(78, 164, 215),
                        Color(150, 243, 243)
                    )
                )

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var selectedNavItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet (
                            drawerContainerColor = Color(7, 26, 78)
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            navItems.forEachIndexed { index, navigationItem ->
                                NavigationDrawerItem(
                                    colors = NavigationDrawerItemDefaults.colors(
                                        unselectedContainerColor = Color(7, 26, 78),
                                        selectedContainerColor = Color(24, 61, 164),
                                        selectedIconColor = Color.White,
                                        unselectedIconColor = Color.White
                                    ),
                                    label = {
                                        Text(text = navigationItem.title, color = Color.White)
                                    },
                                    selected = index == selectedNavItemIndex,
                                    onClick = {
                                        selectedNavItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector =
                                                if (index == selectedNavItemIndex)
                                                    navigationItem.selectedIcon
                                                else navigationItem.unselectedIcon,
                                            contentDescription = navigationItem.title,
                                        )
                                    },
                                    modifier = Modifier
                                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
                    },
                    drawerState = drawerState
                ) {
                    Scaffold (
                        modifier = Modifier
                            .fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = navItems[selectedNavItemIndex].title)
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Open menu"
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color(31, 57, 129),
                                    navigationIconContentColor = Color.White,
                                    titleContentColor = Color.White,
                                    actionIconContentColor = Color.White
                                )
                            )
                        }
                    ) {
                        values -> SessionsScreen(backgroundGradientBrush, values, Modifier)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionsScreen(brush: Brush, values: PaddingValues, modifier: Modifier) {
    val sessionList by remember {
        mutableStateOf(
            listOf(
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Marian Paun",
                    listOf(
                        Treatment(
                            "Paramociotol",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    ),
                    "Raceala"
                ),
                Session(
                    LocalDate.of(2024, 2, 12),
                    LocalDate.of(2024, 3, 19),
                    "Gigel Samsaru",
                    listOf(
                        Treatment(
                            "Paramociotol",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    )
                ),
                Session(
                    LocalDate.of(2024, 3, 17),
                    LocalDate.of(2024, 4, 9),
                    "Eusebiu Manea",
                    listOf(
                        Treatment(
                            "Panadol",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    ),
                    "Migrena"
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Marian Paun",
                    listOf(
                        Treatment(
                            "Extraveral",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    )
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Ileana Sharap",
                    listOf(
                        Treatment(
                            "Aspacardin",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    )
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Maria Geangu",
                    listOf(
                        Treatment(
                            "Aspacardin",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    )
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Vasile Copot",
                    listOf(
                        Treatment(
                            "Aspacardin",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    ),
                    "Hipertensiune"
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Irina Popa",
                    listOf(
                        Treatment(
                            "Aspacardin",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    )
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Giani Vasiliu",
                    listOf(
                        Treatment(
                            "Aspacardin",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    )
                ),
                Session(
                    LocalDate.of(2024, 4, 17),
                    LocalDate.of(2024, 4, 17),
                    "Bogdan Ciurea",
                    listOf(
                        Treatment(
                            "Aspacardin",
                            LocalDate.of(2024, 4, 23),
                            2,
                            8,
                            15
                        )
                    ),
                    "Valoare"
                )
            )
        )
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(values)
                .background(brush),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            sessionList.forEach {
                session -> item{
                    Column (
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {

                            }
                            .fillMaxWidth()
                    ) {
                        Text (
                            text = "Medic: "+ session.medicName,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Text (
                            text = "Primul consult: " + session.startDate.toString(),
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        if (session.diagnostic != null) {
                            Text (
                                text = "Diagnostic: " + session.diagnostic,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                        Divider(color = Color.Black)
                    }
                }
            }
            item { Spacer(modifier = modifier.height(76.dp)) }
        }

        Button(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(31, 57, 129)
            )
        ) {
            Text(
                text = "Sesiune noua",
                fontSize = 25.sp,
                color = Color.White
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
    }
}
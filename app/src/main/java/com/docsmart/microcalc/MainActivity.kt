package com.docsmart.microcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.docsmart.microcalc.ui.screens.HistoryScreen
import com.docsmart.microcalc.ui.screens.HomeScreen
import com.docsmart.microcalc.ui.theme.MicroCalcTheme
import com.docsmart.microcalc.ui.screens.AboutScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MicroCalcTheme {
                MicroCalcApp()
            }
        }
    }
}

@Composable
fun MicroCalcApp() {
    var currentScreen by remember { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> HomeScreen(
            onNavigateToHistory = { currentScreen = "history" },
            onNavigateToAbout = { currentScreen = "about" }
        )
        "history" -> HistoryScreen(
            onBack = { currentScreen = "home" }
        )
        "about" -> AboutScreen(
            onBack = { currentScreen = "home" }
        )
    }
}
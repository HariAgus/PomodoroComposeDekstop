package com.haw.pomodoro

import PomodoroApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import utils.appContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    PomodoroApp()
}
 
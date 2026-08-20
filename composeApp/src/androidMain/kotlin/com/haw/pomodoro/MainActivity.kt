package com.haw.pomodoro

import PomodoroApp
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import utils.appContext
import utils.currentActivity

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
        // Handle permission result if needed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        currentActivity = this
        
        checkNotificationPermission()
        
        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        utils.isAppVisible = true
        updateServiceVisibility(true)
    }

    override fun onStop() {
        super.onStop()
        utils.isAppVisible = false
        updateServiceVisibility(false)
    }

    private fun updateServiceVisibility(isVisible: Boolean) {
        val intent = Intent(this, PomodoroService::class.java).apply {
            action = PomodoroService.ACTION_SET_VISIBILITY
            putExtra(PomodoroService.EXTRA_VISIBLE, isVisible)
        }
        try {
            startService(intent)
        } catch (e: Exception) {
            // Service might not be running or background start restrictions
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (currentActivity == this) {
            currentActivity = null
        }
    }
}

@Composable
fun App() {
    PomodoroApp()
}

package utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.WindowManager
import com.haw.pomodoro.PomodoroService

actual fun platform(): Platform {
    return object : Platform {
        override val isDesktop: Boolean
            get() = false
    }
}

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

var appContext: Context? = null
var currentActivity: Activity? = null
var isAppVisible = true

actual fun toggleBackgroundTimer(isEnabled: Boolean) {
    appContext?.let { context ->
        val intent = Intent(context, PomodoroService::class.java)
        if (isEnabled) {
            if (!isAppVisible && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        } else {
            context.stopService(intent)
        }
    }
}

actual fun toggleKeepScreenOn(isEnabled: Boolean) {
    currentActivity?.let { activity ->
        activity.runOnUiThread {
            if (isEnabled) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }
}

actual fun updateNotification(title: String, content: String) {
    appContext?.let { context ->
        val intent = Intent(context, PomodoroService::class.java).apply {
            action = "UPDATE_NOTIFICATION"
            putExtra("EXTRA_TITLE", title)
            putExtra("EXTRA_CONTENT", content)
        }
        context.startService(intent)
    }
}
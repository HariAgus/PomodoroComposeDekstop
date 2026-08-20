package utils

import android.content.Context
import android.content.Intent
import android.os.Build
import com.haw.pomodoro.PomodoroService

actual fun platform(): Platform {
    return object : Platform {
        override val isDesktop: Boolean
            get() = false
    }
}

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

var appContext: Context? = null

actual fun toggleBackgroundTimer(isEnabled: Boolean) {
    appContext?.let { context ->
        val intent = Intent(context, PomodoroService::class.java)
        if (isEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        } else {
            context.stopService(intent)
        }
    }
}
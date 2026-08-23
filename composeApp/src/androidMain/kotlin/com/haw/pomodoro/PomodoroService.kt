package com.haw.pomodoro

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat

class PomodoroService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private lateinit var notificationManager: NotificationManager
    private var isAppVisible = true
    private var lastTitle = "Pomodoro Desktop"
    private var lastContent = ""

    companion object {
        const val CHANNEL_ID = "pomodoro_timer_channel"
        const val ALERT_CHANNEL_ID = "pomodoro_alert_channel"
        const val NOTIFICATION_ID = 1
        const val ALERT_NOTIFICATION_ID = 2
        const val ACTION_UPDATE = "UPDATE_NOTIFICATION"
        const val ACTION_SESSION_FINISHED = "SESSION_FINISHED"
        const val ACTION_SET_VISIBILITY = "SET_VISIBILITY"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_CONTENT = "EXTRA_CONTENT"
        const val EXTRA_VISIBLE = "EXTRA_VISIBLE"
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Pomodoro::TimerWakeLock")
        wakeLock?.acquire(10 * 60 * 1000L /*10 minutes*/)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_UPDATE -> {
                lastTitle = intent.getStringExtra(EXTRA_TITLE) ?: lastTitle
                lastContent = intent.getStringExtra(EXTRA_CONTENT) ?: lastContent
                if (!isAppVisible) {
                    updateNotification(lastTitle, lastContent)
                }
            }
            ACTION_SESSION_FINISHED -> {
                val title = intent.getStringExtra(EXTRA_TITLE) ?: "Pomodoro"
                val content = intent.getStringExtra(EXTRA_CONTENT) ?: "Session finished!"
                showFinishedNotification(title, content)
            }
            ACTION_SET_VISIBILITY -> {
                isAppVisible = intent.getBooleanExtra(EXTRA_VISIBLE, true)
                handleForegroundTransition()
            }
            else -> {
                // Initial start, check visibility
                handleForegroundTransition()
            }
        }
        return START_STICKY
    }

    private fun handleForegroundTransition() {
        if (isAppVisible) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(STOP_FOREGROUND_REMOVE)
            } else {
                stopForeground(true)
            }
        } else {
            val notification = createNotification(lastTitle, lastContent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }
        }
    }

    override fun onDestroy() {
        wakeLock?.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun updateNotification(title: String, content: String) {
        if (!isAppVisible) {
            val notification = createNotification(title, content)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun showFinishedNotification(title: String, content: String) {
        val notification = NotificationCompat.Builder(this, ALERT_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(ALERT_NOTIFICATION_ID, notification)
    }

    private fun createNotification(title: String, contentText: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Pomodoro Timer Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(serviceChannel)

            val alertChannel = NotificationChannel(
                ALERT_CHANNEL_ID,
                "Pomodoro Alert Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Used for session finish alerts"
            }
            notificationManager.createNotificationChannel(alertChannel)
        }
    }
}

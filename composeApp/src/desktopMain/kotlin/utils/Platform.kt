package utils

import java.awt.Image
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon

actual fun platform(): Platform {
    return object : Platform {
        override val isDesktop: Boolean
            get() = true
    }
}

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual fun toggleBackgroundTimer(isEnabled: Boolean) {
    // No-op for desktop
}

actual fun toggleKeepScreenOn(isEnabled: Boolean) {
    // No-op for desktop
}

actual fun updateNotification(title: String, content: String) {
    // No-op for desktop
}

actual fun notifySessionFinished(title: String, content: String) {
    if (SystemTray.isSupported()) {
        val tray = SystemTray.getSystemTray()
        val image: Image = Toolkit.getDefaultToolkit().createImage("")
        val trayIcon = TrayIcon(image, "Pomodoro")
        trayIcon.isImageAutoSize = true
        try {
            tray.add(trayIcon)
            trayIcon.displayMessage(title, content, TrayIcon.MessageType.INFO)
            // Remove icon after a delay to clean up
            java.util.Timer().schedule(object : java.util.TimerTask() {
                override fun run() {
                    tray.remove(trayIcon)
                }
            }, 5000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

actual fun vibrate() {
    // No-op for desktop
}
package utils

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual fun platform(): Platform {
    return object : Platform {
        override val isDesktop: Boolean
            get() = false
    }
}

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun toggleBackgroundTimer(isEnabled: Boolean) {
    // Implement using background tasks if needed
}

actual fun toggleKeepScreenOn(isEnabled: Boolean) {
    // Implement using UIApplication.sharedApplication.isIdleTimerDisabled
}

actual fun updateNotification(title: String, content: String) {
    // Implement using UserNotifications framework
}

actual fun notifySessionFinished(title: String, content: String) {
    // Implement using UserNotifications framework
}

actual fun vibrate() {
    // Implement using AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
}

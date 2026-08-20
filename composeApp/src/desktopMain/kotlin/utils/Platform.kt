package utils

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
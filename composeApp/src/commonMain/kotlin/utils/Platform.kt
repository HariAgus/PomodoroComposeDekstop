package utils

interface Platform {
    val isDesktop: Boolean
}

expect fun platform(): Platform

expect fun currentTimeMillis(): Long

expect fun toggleBackgroundTimer(isEnabled: Boolean)

expect fun toggleKeepScreenOn(isEnabled: Boolean)

expect fun updateNotification(title: String, content: String)

expect fun notifySessionFinished(title: String, content: String)

expect fun vibrate()
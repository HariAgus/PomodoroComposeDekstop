package utils

interface Platform {
    val isDesktop: Boolean
}

expect fun platform(): Platform

expect fun currentTimeMillis(): Long

expect fun toggleBackgroundTimer(isEnabled: Boolean)

expect fun toggleKeepScreenOn(isEnabled: Boolean)
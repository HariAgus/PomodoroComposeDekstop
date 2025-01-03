package utils

interface Platform {
    val isDesktop: Boolean
}

expect fun platform(): Platform
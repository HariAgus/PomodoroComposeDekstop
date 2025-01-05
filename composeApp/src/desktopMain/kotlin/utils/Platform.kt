package utils

actual fun platform(): Platform {
    return object : Platform {
        override val isDesktop: Boolean
            get() = true
    }
}
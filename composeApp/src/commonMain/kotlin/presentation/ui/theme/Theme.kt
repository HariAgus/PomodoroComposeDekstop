package presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

enum class Theme(val title: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System")
}

@Composable
fun AppTheme(
    theme: Theme = Theme.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDark = when (theme) {
        Theme.LIGHT -> false
        Theme.DARK -> true
        Theme.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        content = content
    )
}

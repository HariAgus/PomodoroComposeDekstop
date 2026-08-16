package data

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import pomodorodesktop.composeapp.generated.resources.Res
import pomodorodesktop.composeapp.generated.resources.ic_break
import pomodorodesktop.composeapp.generated.resources.ic_focus

enum class Pomodoro(
    val title: String,
    var timer: Int,
    val icon: DrawableResource,
) {

    FOCUS(
        title = "Focus",
        timer = 1500,
        icon = Res.drawable.ic_focus,
    ),

    BREAK(
        title = "Short Break",
        timer = 300,
        icon = Res.drawable.ic_break,
    ),

    LONG_BREAK(
        title = "Long Break",
        timer = 900,
        icon = Res.drawable.ic_break,
    );

    fun getTextColor(isDark: Boolean): Color = when (this) {
        FOCUS -> if (isDark) Color(0xFFFFD9D9) else Color(0xFF471515)
        BREAK -> if (isDark) Color(0xFFDAFAE0) else Color(0xFF14401D)
        LONG_BREAK -> if (isDark) Color(0xFFD9E9FF) else Color(0xFF153047)
    }

    fun getBackgroundColor(isDark: Boolean): Color = when (this) {
        FOCUS -> if (isDark) Color(0xFF2B1A1A) else Color(0xFFFFF2F2)
        BREAK -> if (isDark) Color(0xFF1A2B1F) else Color(0xFFF2FFF5)
        LONG_BREAK -> if (isDark) Color(0xFF1A232B) else Color(0xFFF2F9FF)
    }

    fun getButtonColorPrimary(isDark: Boolean): Color = when (this) {
        FOCUS -> if (isDark) Color(0xFF471515) else Color(0xFFFF7C7C)
        BREAK -> if (isDark) Color(0xFF14401D) else Color(0xFF8CE8A1)
        LONG_BREAK -> if (isDark) Color(0xFF153047) else Color(0xFF7CB9FF)
    }

    fun getButtonColorSecond(isDark: Boolean): Color = when (this) {
        FOCUS -> if (isDark) Color(0xFF3D2A2A) else Color(0xFFFFD9D9)
        BREAK -> if (isDark) Color(0xFF2A3D30) else Color(0xFFDAFAE0)
        LONG_BREAK -> if (isDark) Color(0xFF2A343D) else Color(0xFFD9E9FF)
    }
}

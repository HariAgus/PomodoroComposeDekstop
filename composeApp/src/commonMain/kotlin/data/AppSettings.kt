package data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import presentation.ui.theme.Theme

class AppSettings(private val settings: Settings = Settings()) {
    companion object {
        private const val KEY_THEME = "selected_theme"
        private const val KEY_MUSIC = "is_music_enabled"
    }

    fun saveTheme(theme: Theme) {
        settings[KEY_THEME] = theme.name
    }

    fun getTheme(): Theme {
        val themeName = settings.getString(KEY_THEME, Theme.SYSTEM.name)
        return try {
            Theme.valueOf(themeName)
        } catch (e: Exception) {
            Theme.SYSTEM
        }
    }

    fun saveMusicEnabled(isEnabled: Boolean) {
        settings[KEY_MUSIC] = isEnabled
    }

    fun isMusicEnabled(): Boolean {
        return settings.getBoolean(KEY_MUSIC, true)
    }
}

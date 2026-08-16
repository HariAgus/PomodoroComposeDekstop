
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data.Pomodoro
import data.Speed
import data.ThemeSettings
import kotlinx.coroutines.delay
import presentation.ui.theme.AppTheme
import presentation.ui.theme.Theme
import presentation.view.desktop.PomodoroDesktopLayout
import presentation.view.mobile.PomodoroMobileLayout
import utils.platform
import utils.rememberAudioPlayer

@Composable
fun PomodoroApp() {
    val audioPlayer = rememberAudioPlayer("window_seat.mp3")
    val themeSettings = remember { ThemeSettings() }
    var pomodoro by remember { mutableStateOf(Pomodoro.FOCUS) }
    var isPlayPomodoro by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var timerLeft by remember { mutableStateOf(pomodoro.timer) }
    var speedTime by remember { mutableStateOf(Speed.NORMAL) }
    var pomodoroCount by remember { mutableStateOf(0) }
    var selectedTheme by remember { mutableStateOf(themeSettings.getTheme()) }

    val isDark = when (selectedTheme) {
        Theme.LIGHT -> false
        Theme.DARK -> true
        Theme.SYSTEM -> isSystemInDarkTheme()
    }

    LaunchedEffect(isPlayPomodoro, pomodoro) {
        if (isPlayPomodoro && pomodoro == Pomodoro.FOCUS) {
            audioPlayer.setLooping(true)
            audioPlayer.play()
        } else {
            audioPlayer.pause()
        }
    }

    LaunchedEffect(key1 = isPlayPomodoro) {
        while (isPlayPomodoro && timerLeft > 0) {
            delay(speedTime.speed)
            timerLeft--

            if (timerLeft <= 0) {
                isPlayPomodoro = false
                when (pomodoro) {
                    Pomodoro.FOCUS -> {
                        pomodoroCount++
                        if (pomodoroCount % 4 == 0) {
                            pomodoro = Pomodoro.LONG_BREAK
                        } else {
                            pomodoro = Pomodoro.BREAK
                        }
                    }
                    Pomodoro.BREAK, Pomodoro.LONG_BREAK -> {
                        pomodoro = Pomodoro.FOCUS
                    }
                }
                timerLeft = pomodoro.timer
            }
        }
    }

    val platform = platform()

    AppTheme(theme = selectedTheme) {
        if (platform.isDesktop) {
            PomodoroDesktopLayout(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                isShowDialog = isShowDialog,
                selectedTheme = selectedTheme,
                isDark = isDark,
                onPlayPause = { isPlayPomodoro = it },
                onSpeedChange = { speedTime = it },
                onThemeSelected = {
                    selectedTheme = it
                    themeSettings.saveTheme(it)
                },
                onDialogToggle = { isShowDialog = it }
            ) { time ->
                Pomodoro.FOCUS.timer = time
                if (!isPlayPomodoro && pomodoro == Pomodoro.FOCUS) {
                    timerLeft = time
                }
            }
        } else {
            PomodoroMobileLayout(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                isShowDialog = isShowDialog,
                selectedTheme = selectedTheme,
                isDark = isDark,
                onPlayPause = { isPlayPomodoro = it },
                onSpeedChange = { speedTime = it },
                onThemeSelected = {
                    selectedTheme = it
                    themeSettings.saveTheme(it)
                },
                onDialogToggle = { isShowDialog = it }
            ) { time ->
                Pomodoro.FOCUS.timer = time
                if (!isPlayPomodoro && pomodoro == Pomodoro.FOCUS) {
                    timerLeft = time
                }
            }
        }
    }
}

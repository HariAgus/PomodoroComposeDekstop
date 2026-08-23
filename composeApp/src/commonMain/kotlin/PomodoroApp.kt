
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data.AppSettings
import data.Pomodoro
import data.Speed
import kotlinx.coroutines.delay
import presentation.ui.theme.AppTheme
import presentation.ui.theme.Theme
import presentation.view.desktop.PomodoroDesktopLayout
import presentation.view.mobile.PomodoroMobileLayout
import utils.currentTimeMillis
import utils.notifySessionFinished
import utils.platform
import utils.rememberAudioPlayer
import utils.toggleBackgroundTimer
import utils.toggleKeepScreenOn
import utils.updateNotification
import utils.vibrate
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun PomodoroApp() {
    val audioPlayer = rememberAudioPlayer("window_seat.mp3")
    val notifyPlayer = rememberAudioPlayer("warm_landing.mp3")
    val themeSettings = remember { AppSettings() }
    var pomodoro by remember { mutableStateOf(Pomodoro.FOCUS) }
    var isPlayPomodoro by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var timerLeft by remember { mutableStateOf(pomodoro.timer) }
    var speedTime by remember { mutableStateOf(Speed.NORMAL) }
    var pomodoroCount by remember { mutableStateOf(0) }
    var selectedTheme by remember { mutableStateOf(themeSettings.getTheme()) }
    var isMusicEnabled by remember { mutableStateOf(themeSettings.isMusicEnabled()) }
    var isFinishedNotificationActive by remember { mutableStateOf(false) }

    val isDark = when (selectedTheme) {
        Theme.LIGHT -> false
        Theme.DARK -> true
        Theme.SYSTEM -> isSystemInDarkTheme()
    }

    LaunchedEffect(isPlayPomodoro) {
        toggleBackgroundTimer(isPlayPomodoro)
        toggleKeepScreenOn(isPlayPomodoro)
        if (isPlayPomodoro) {
            isFinishedNotificationActive = false
        }
    }

    LaunchedEffect(isFinishedNotificationActive) {
        if (isFinishedNotificationActive) {
            notifyPlayer.play()
            delay(30000.milliseconds)
            if (isFinishedNotificationActive) {
                notifyPlayer.stop()
                isFinishedNotificationActive = false
            }
        } else {
            notifyPlayer.stop()
        }
    }

    LaunchedEffect(isPlayPomodoro, pomodoro, isMusicEnabled) {
        if (isPlayPomodoro && pomodoro == Pomodoro.FOCUS && isMusicEnabled) {
            audioPlayer.setLooping(true)
            audioPlayer.play()
        } else {
            audioPlayer.pause()
        }
    }

    LaunchedEffect(isPlayPomodoro, pomodoro, speedTime) {
        if (isPlayPomodoro) {
            val startTime = currentTimeMillis()
            val startTimerValue = timerLeft
            while (isPlayPomodoro && timerLeft > 0) {
                delay(100.milliseconds)
                val current = currentTimeMillis()
                val elapsed = ((current - startTime) / speedTime.speed).toInt()
                val newTimerLeft = (startTimerValue - elapsed).coerceAtLeast(0)

                if (newTimerLeft != timerLeft) {
                    timerLeft = newTimerLeft
                    val minutes = (timerLeft / 60).toString().padStart(2, '0')
                    val seconds = (timerLeft % 60).toString().padStart(2, '0')
                    updateNotification(pomodoro.title, "$minutes:$seconds remaining")
                }

                if (timerLeft <= 0) {
                    isPlayPomodoro = false
                    isFinishedNotificationActive = true
                    notifySessionFinished(
                        pomodoro.title + " Finished",
                        if (pomodoro == Pomodoro.FOCUS) "Time to take a break!" else "Time to focus!"
                    )
                    vibrate()

                    pomodoro = when (pomodoro) {
                        Pomodoro.FOCUS -> {
                            pomodoroCount++
                            if (pomodoroCount % 4 == 0) {
                                Pomodoro.LONG_BREAK
                            } else {
                                Pomodoro.BREAK
                            }
                        }

                        Pomodoro.BREAK, Pomodoro.LONG_BREAK -> {
                            Pomodoro.FOCUS
                        }
                    }
                    timerLeft = pomodoro.timer
                }
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
                isMusicEnabled = isMusicEnabled,
                onPlayPause = { isPlayPomodoro = it },
                onSpeedChange = { speedTime = it },
                onThemeSelected = {
                    selectedTheme = it
                    themeSettings.saveTheme(it)
                },
                onMusicToggled = {
                    isMusicEnabled = it
                    themeSettings.saveMusicEnabled(it)
                },
                onDialogToggle = { isShowDialog = it },
                onTimeSelected = { time ->
                    Pomodoro.FOCUS.timer = time
                    if (!isPlayPomodoro && pomodoro == Pomodoro.FOCUS) {
                        timerLeft = time
                    }
                },
                onReset = {
                    isPlayPomodoro = false
                    timerLeft = pomodoro.timer
                }
            )
        } else {
            PomodoroMobileLayout(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                isShowDialog = isShowDialog,
                selectedTheme = selectedTheme,
                isDark = isDark,
                isMusicEnabled = isMusicEnabled,
                onPlayPause = { isPlayPomodoro = it },
                onSpeedChange = { speedTime = it },
                onThemeSelected = {
                    selectedTheme = it
                    themeSettings.saveTheme(it)
                },
                onMusicToggled = {
                    isMusicEnabled = it
                    themeSettings.saveMusicEnabled(it)
                },
                onDialogToggle = { isShowDialog = it },
                onTimeSelected = { time ->
                    Pomodoro.FOCUS.timer = time
                    if (!isPlayPomodoro && pomodoro == Pomodoro.FOCUS) {
                        timerLeft = time
                    }
                },
                onReset = {
                    isPlayPomodoro = false
                    timerLeft = pomodoro.timer
                }
            )
        }
    }
}

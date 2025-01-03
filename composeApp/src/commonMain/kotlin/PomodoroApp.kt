
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import data.Pomodoro
import data.Speed
import kotlinx.coroutines.delay
import presentation.view.desktop.PomodoroDesktopLayout
import presentation.view.mobile.PomodoroMobileLayout
import utils.platform

@Composable
fun PomodoroApp() {
    var pomodoro by remember { mutableStateOf(Pomodoro.FOCUS) }
    var isPlayPomodoro by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var timerLeft by remember { mutableStateOf(pomodoro.timer) }
    var speedTime by remember { mutableStateOf(Speed.NORMAL) }

    LaunchedEffect(key1 = isPlayPomodoro) {
        while (isPlayPomodoro && timerLeft > 0) {
            delay(speedTime.speed)
            timerLeft--

            if (timerLeft <= 0 && pomodoro == Pomodoro.FOCUS) {
                pomodoro = Pomodoro.BREAK
                timerLeft = pomodoro.timer
                isPlayPomodoro = false
            } else if (timerLeft <= 0 && pomodoro == Pomodoro.BREAK) {
                pomodoro = Pomodoro.FOCUS
                timerLeft = pomodoro.timer
                isPlayPomodoro = false
            }
        }
    }

    val platform = platform()

        MaterialTheme {
            if (platform.isDesktop) {
                PomodoroDesktopLayout(
                    pomodoro = pomodoro,
                    isPlayPomodoro = isPlayPomodoro,
                    timerLeft = timerLeft,
                    speedTime = speedTime,
                    isShowDialog = isShowDialog,
                    onPlayPause = { isPlayPomodoro = it },
                    onSpeedChange = { speedTime = it },
                    onDialogToggle = { isShowDialog = it }
                )
            } else {
                PomodoroMobileLayout(
                    pomodoro = pomodoro,
                    isPlayPomodoro = isPlayPomodoro,
                    timerLeft = timerLeft,
                    speedTime = speedTime,
                    isShowDialog = isShowDialog,
                    onPlayPause = { isPlayPomodoro = it },
                    onSpeedChange = { speedTime = it },
                    onDialogToggle = { isShowDialog = it }
                )
            }
        }
}
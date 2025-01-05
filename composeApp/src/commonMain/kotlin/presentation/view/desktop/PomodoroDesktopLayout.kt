package presentation.view.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.Pomodoro
import data.Speed
import presentation.components.PomodoroContent
import utils.CustomDialog

@Composable
fun PomodoroDesktopLayout(
    modifier: Modifier = Modifier,
    pomodoro: Pomodoro,
    isPlayPomodoro: Boolean,
    timerLeft: Int,
    speedTime: Speed,
    isShowDialog: Boolean,
    onPlayPause: (Boolean) -> Unit,
    onSpeedChange: (Speed) -> Unit,
    onDialogToggle: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = pomodoro.backgroundColor),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PomodoroContent(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                onPlayPause = onPlayPause,
                onSpeedChange = onSpeedChange,
                onDialogToggle = onDialogToggle
            )
        }

        if (isShowDialog) {
            CustomDialog(
                textColor = pomodoro.textColor,
                backgroundColor = pomodoro.backgroundColor,
                onCloseDialog = { onDialogToggle(false) }
            )
        }
    }
}
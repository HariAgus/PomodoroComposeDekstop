package presentation.view.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Pomodoro
import data.Speed
import presentation.components.PomodoroContent
import presentation.ui.theme.Theme
import utils.CustomDialog

@Composable
fun PomodoroMobileLayout(
    pomodoro: Pomodoro,
    isPlayPomodoro: Boolean,
    timerLeft: Int,
    speedTime: Speed,
    isShowDialog: Boolean,
    selectedTheme: Theme,
    isDark: Boolean,
    onPlayPause: (Boolean) -> Unit,
    onSpeedChange: (Speed) -> Unit,
    onThemeSelected: (Theme) -> Unit,
    onDialogToggle: (Boolean) -> Unit,
    onTimeSelected: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = pomodoro.getBackgroundColor(isDark)),
    ) {
        Column(
            modifier = Modifier
                .width(289.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PomodoroContent(
                pomodoro = pomodoro,
                isPlayPomodoro = isPlayPomodoro,
                timerLeft = timerLeft,
                speedTime = speedTime,
                isDark = isDark,
                onPlayPause = onPlayPause,
                onSpeedChange = onSpeedChange,
                onDialogToggle = onDialogToggle,
                onTimeSelected = onTimeSelected
            )
        }

        if (isShowDialog) {
            CustomDialog(
                textColor = pomodoro.getTextColor(isDark),
                backgroundColor = pomodoro.getBackgroundColor(isDark),
                selectedTheme = selectedTheme,
                onThemeSelected = onThemeSelected,
                onCloseDialog = { onDialogToggle(false) }
            )
        }
    }
}

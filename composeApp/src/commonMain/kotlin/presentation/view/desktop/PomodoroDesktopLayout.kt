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
import presentation.ui.theme.Theme
import utils.CustomDialog

@Composable
fun PomodoroDesktopLayout(
    modifier: Modifier = Modifier,
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
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = pomodoro.getBackgroundColor(isDark)),
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

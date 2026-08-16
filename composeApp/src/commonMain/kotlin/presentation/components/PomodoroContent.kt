package presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Pomodoro
import data.Speed
import org.jetbrains.compose.resources.painterResource
import pomodorodesktop.composeapp.generated.resources.Res
import pomodorodesktop.composeapp.generated.resources.ic_fast_foward
import pomodorodesktop.composeapp.generated.resources.ic_menu
import pomodorodesktop.composeapp.generated.resources.ic_pause
import pomodorodesktop.composeapp.generated.resources.ic_play
import presentation.ui.theme.GetFontPoppinsBold
import presentation.ui.theme.GetFontPoppinsMedium
import presentation.ui.theme.GetFontPoppinsSemiBold

@Composable
fun PomodoroContent(
    pomodoro: Pomodoro,
    isPlayPomodoro: Boolean,
    timerLeft: Int,
    speedTime: Speed,
    isDark: Boolean,
    onPlayPause: (Boolean) -> Unit,
    onSpeedChange: (Speed) -> Unit,
    onDialogToggle: (Boolean) -> Unit,
) {
    val textColor = pomodoro.getTextColor(isDark)
    val buttonColorPrimary = pomodoro.getButtonColorPrimary(isDark)
    val buttonColorSecond = pomodoro.getButtonColorSecond(isDark)

    Surface(
        color = buttonColorSecond,
        shape = RoundedCornerShape(100.dp),
        border = BorderStroke(width = 1.dp, color = textColor),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(22.dp),
                painter = painterResource(pomodoro.icon),
                colorFilter = ColorFilter.tint(textColor),
                contentDescription = "Icon Pomodoro"
            )

            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = pomodoro.title,
                fontFamily = GetFontPoppinsSemiBold(),
                fontSize = 14.sp,
                color = textColor
            )
        }
    }

    Text(
        text = String.format("%02d\n%02d", timerLeft / 60, timerLeft % 60),
        fontFamily = GetFontPoppinsBold(),
        fontSize = 168.sp,
        textAlign = TextAlign.Center,
        lineHeight = 148.sp,
        color = textColor
    )

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColorSecond,
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = { onDialogToggle(!isPlayPomodoro) }
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(Res.drawable.ic_menu),
                colorFilter = ColorFilter.tint(textColor),
                contentDescription = ""
            )
        }

        Button(
            modifier = Modifier.size(width = 120.dp, height = 80.dp)
                .padding(horizontal = 14.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColorPrimary
            ),
            onClick = { onPlayPause(!isPlayPomodoro) }
        ) {
            Image(
                painter = painterResource(
                    if (!isPlayPomodoro)
                        Res.drawable.ic_play
                    else
                        Res.drawable.ic_pause
                ),
                colorFilter = ColorFilter.tint(textColor),
                contentDescription = ""
            )
        }

        Button(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColorSecond
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onSpeedChange(if (speedTime == Speed.NORMAL) Speed.FAST else Speed.NORMAL)
            }
        ) {
            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(Res.drawable.ic_fast_foward),
                colorFilter = ColorFilter.tint(textColor),
                contentDescription = ""
            )
        }
    }

    AnimatedVisibility(
        visible = speedTime == Speed.FAST
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Speed is Fast",
            fontFamily = GetFontPoppinsMedium(),
            color = textColor.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
    }

}

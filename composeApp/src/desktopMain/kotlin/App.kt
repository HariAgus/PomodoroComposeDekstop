import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Pomodoro
import data.Speed
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pomodorodesktop.composeapp.generated.resources.Res
import pomodorodesktop.composeapp.generated.resources.ic_fast_foward
import pomodorodesktop.composeapp.generated.resources.ic_menu
import pomodorodesktop.composeapp.generated.resources.ic_pause
import pomodorodesktop.composeapp.generated.resources.ic_play
import ui.theme.GetFontPoppinsBold
import ui.theme.GetFontPoppinsMedium
import ui.theme.GetFontPoppinsSemiBold
import utils.CustomDialog

@Composable
@Preview
fun App() {

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

    var timerText = String.format("%02d %02d", timerLeft / 60, timerLeft % 60)

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = pomodoro.backgroundColor),
        ) {
            Column(
                modifier = Modifier
                    .width(289.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = pomodoro.buttonColorSecond,
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(width = 1.dp, color = pomodoro.textColor),
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(22.dp),
                            painter = painterResource(pomodoro.icon),
                            contentDescription = "Icon Pomodoro"
                        )

                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = pomodoro.title,
                            fontFamily = GetFontPoppinsSemiBold(),
                            fontSize = 14.sp,
                            color = pomodoro.textColor
                        )
                    }
                }

                Text(
                    text = timerText,
                    fontFamily = GetFontPoppinsBold(),
                    fontSize = 168.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 148.sp,
                    color = pomodoro.textColor
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.size(45.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = pomodoro.buttonColorSecond
                        ),
                        onClick = {
                            // Show Custom Dialog
                            isShowDialog = !isShowDialog
                        }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_menu),
                            colorFilter = ColorFilter.tint(color = pomodoro.iconColor),
                            contentDescription = ""
                        )
                    }

                    Button(
                        modifier = Modifier.size(width = 100.dp, height = 72.dp)
                            .padding(horizontal = 14.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = pomodoro.buttonColorPrimary
                        ),
                        onClick = {
                            isPlayPomodoro = !isPlayPomodoro
                        }
                    ) {
                        Image(
                            painter = painterResource(if (isPlayPomodoro.not()) Res.drawable.ic_play else Res.drawable.ic_pause),
                            colorFilter = ColorFilter.tint(color = pomodoro.iconColor),
                            contentDescription = ""
                        )
                    }

                    Button(
                        modifier = Modifier.size(45.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = pomodoro.buttonColorSecond
                        ),
                        onClick = {
                            speedTime = when (speedTime) {
                                Speed.NORMAL -> Speed.FAST
                                Speed.FAST -> Speed.NORMAL
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.ic_fast_foward),
                            colorFilter = ColorFilter.tint(color = pomodoro.iconColor),
                            contentDescription = ""
                        )
                    }
                }

                if(speedTime == Speed.FAST) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Speed is Fast",
                        fontFamily = GetFontPoppinsMedium(),
                        color = pomodoro.textColor.copy(alpha = 0.5f),
                        fontSize = 12.sp
                    )
                }
            }

            if (isShowDialog) {
                CustomDialog(
                    textColor = pomodoro.textColor,
                    backgroundColor = pomodoro.backgroundColor,
                    onCloseDialog = {
                        isShowDialog = false
                    }
                )
            }
        }
    }
}
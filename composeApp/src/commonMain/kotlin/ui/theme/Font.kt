package ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import pomodorodesktop.composeapp.generated.resources.Res
import pomodorodesktop.composeapp.generated.resources.poppins_bold
import pomodorodesktop.composeapp.generated.resources.poppins_light
import pomodorodesktop.composeapp.generated.resources.poppins_medium
import pomodorodesktop.composeapp.generated.resources.poppins_semibold

@Composable
fun GetFontPoppinsBold() = FontFamily(Font(Res.font.poppins_bold))

@Composable
fun GetFontPoppinsSemiBold() = FontFamily(Font(Res.font.poppins_semibold))

@Composable
fun GetFontPoppinsMedium() = FontFamily(Font(Res.font.poppins_medium))

@Composable
fun GetFontPoppinsLight() = FontFamily(Font(Res.font.poppins_light))
package utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.haw.pomodoro.BuildKonfig
import presentation.ui.theme.GetFontPoppinsMedium
import presentation.ui.theme.GetFontPoppinsSemiBold
import presentation.ui.theme.Theme

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    textColor: Color,
    backgroundColor: Color,
    selectedTheme: Theme,
    onThemeSelected: (Theme) -> Unit,
    onCloseDialog: () -> Unit
) {
    val isDark = when (selectedTheme) {
        Theme.LIGHT -> false
        Theme.DARK -> true
        Theme.SYSTEM -> isSystemInDarkTheme()
    }

    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = modifier
                .width(320.dp)
                .wrapContentHeight(),
            color = backgroundColor,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Settings",
                        fontSize = 16.sp,
                        fontFamily = GetFontPoppinsSemiBold(),
                        color = textColor
                    )

                    Image(
                        modifier = Modifier
                            .size(14.dp)
                            .clickable {
                                onCloseDialog()
                            },
                        imageVector = Icons.Default.Close,
                        colorFilter = ColorFilter.tint(color = textColor.copy(alpha = 0.5f)),
                        contentDescription = ""
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = textColor.copy(alpha = 0.1f)
                )

                Text(
                    text = "Theme",
                    fontFamily = GetFontPoppinsSemiBold(),
                    fontSize = 12.sp,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Theme.entries.forEach { theme ->
                        val isSelected = selectedTheme == theme
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { onThemeSelected(theme) }
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) textColor else textColor.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = theme.title,
                                fontFamily = GetFontPoppinsMedium(),
                                fontSize = 10.sp,
                                color = if (isSelected) textColor else textColor.copy(alpha = 0.5f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = textColor.copy(alpha = 0.1f)
                )

                ContentAboutApp(
                    title = "Developer",
                    content = "Hari Agus W",
                    textColor = textColor
                )

                ContentAboutApp(
                    title = "Version",
                    content = BuildKonfig.VERSION_NAME,
                    textColor = textColor
                )
            }
        }
    }
}

@Composable
private fun ContentAboutApp(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    textColor: Color
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontFamily = GetFontPoppinsMedium(),
            fontSize = 12.sp,
            color = textColor
        )

        Text(
            text = content,
            fontFamily = GetFontPoppinsMedium(),
            fontSize = 10.sp,
            color = textColor
        )
    }
}
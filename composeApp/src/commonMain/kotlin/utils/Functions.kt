package utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.theme.GetFontPoppinsMedium
import ui.theme.GetFontPoppinsSemiBold

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    textColor: Color,
    backgroundColor: Color,
    onCloseDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = modifier
                .width(280.dp)
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
                        text = "About",
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
                        colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.5f)),
                        contentDescription = ""
                    )
                }

                Divider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp),
                    color = Color(0xFF471515).copy(alpha = 0.1f)
                )

                ContentAboutApp(
                    title = "Developer",
                    content = "Hari Agus W",
                    textColor = textColor
                )

                ContentAboutApp(
                    title = "Version",
                    content = "1.0.0",
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
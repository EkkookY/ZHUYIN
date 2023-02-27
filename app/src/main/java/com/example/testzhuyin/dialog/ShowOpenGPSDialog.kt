package com.melody.map.myapplication.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
internal fun ShowOpenGPSDialog(onPositiveClick: () -> Unit, onDismiss: () -> Unit) {
    SimpleDialog(
        positiveButtonText = "开启定位",
        negativeButtonText = "取消",
        content = "定位失败，打开定位服务来获取位置信息",
        onPositiveClick = onPositiveClick,
        onNegativeClick = onDismiss,
        onDismiss = onDismiss
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SimpleDialog(
    positiveButtonText: String,
    negativeButtonText: String,
    content: String,
    onPositiveClick:()->Unit,
    onNegativeClick:()->Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .width(283.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp),
            color = Color(0XFFFFFFFF)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(19.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = content,
                        style = TextStyle(
                            color = Color(0XFF333333),
                            fontSize = 13.sp
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 19.dp, end = 19.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .height(42.dp)
                ) {

                    SubmitButton(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth(),
                        buttonHeight = 42.dp,
                        background = Color(0XFFF0F2F5),
                        textColor = Color(0xFF668EF7),
                        buttonText = negativeButtonText,
                        onClick = onNegativeClick
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    SubmitButton(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth(),
                        background = Color(0xFF668EF7),
                        textColor = Color(0XFFFFFFFF),
                        buttonHeight = 42.dp,
                        buttonText = positiveButtonText,
                        onClick = onPositiveClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SubmitButton(
    modifier: Modifier,
    enabled: Boolean = true,
    @StringRes buttonTextRes: Int? = null,
    buttonText: String? = null,
    buttonHeight: Dp,
    shapeRadius: Dp = 4.dp,
    fontSize: TextUnit = 14.sp,
    background:Color = Color(0xFF668EF7),
    textColor:Color = Color(0XFFFFFFFF),
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberRipple()
    Box(modifier = modifier
        .height(buttonHeight)
        .background(
            color = background,
            shape = RoundedCornerShape(shapeRadius)
        )
        .clip(RoundedCornerShape(shapeRadius))
        .clickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = indication
        ) {
            onClick.invoke()
        }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = if (null != buttonTextRes) stringResource(id = buttonTextRes) else buttonText
                ?: "",
            style = TextStyle(
                color = textColor,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium
            )
        )
    }
}
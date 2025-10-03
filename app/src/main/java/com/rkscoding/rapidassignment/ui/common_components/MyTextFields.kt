package com.rkscoding.rapidassignment.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkscoding.rapidassignment.ui.theme.indigo

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors().copy(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedPlaceholderColor = Color.Gray.copy(.7f),
        unfocusedPlaceholderColor = Color.Gray,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White
    ),
) {
    Column(Modifier.padding(vertical = 8.dp)) {
        label?.let {
            Row {
//                Spacer(modifier = Modifier.size(4.dp))
                it()
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(56.dp)
                .border(width = 1.dp, color = indigo, shape = RoundedCornerShape(12.dp))
                .dropShadow(
                    shape = RoundedCornerShape(14.dp),
                    shadow = Shadow(
                        radius = 0.dp,
                        color = indigo,
                        offset = DpOffset(x = 0.dp, y = 4.dp)
                    )
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle.copy(fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = indigo),
            label = null,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = RoundedCornerShape(12.dp),
            colors = colors
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
) {
    var visible by remember { mutableStateOf(true) }
    AppTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        placeholder = {
            Text(text = placeholder, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        },
        singleLine = true,
        trailingIcon = {
            Box(
                modifier = modifier
                    .padding(end = 8.dp)
                    .height(28.dp)
                    .width(80.dp)
                    .padding(end = 2.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .clickable(
                        onClick = { visible = !visible }
                    )
                    .background(indigo),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (visible) "Show" else "Hide",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }
        },
        visualTransformation = if (!visible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    countdown: Int,
    isLoading: Boolean,
) {
    AppTextField(
        value = value,
        onValueChange = { onValueChange.invoke(it) },
        placeholder = {
            Text(text = "Otp", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        },
        textStyle = TextStyle(letterSpacing = 6.sp, fontWeight = FontWeight.Bold),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Box(
                modifier = modifier
                    .padding(end = 8.dp)
                    .height(28.dp)
                    .width(80.dp)
                    .padding(end = 2.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .clickable(
                        enabled = countdown == 0
                    ) {
                        onClick.invoke()
                    }
                    .background(indigo),
                contentAlignment = Alignment.Center
            ) {
                if (countdown > 0) {
                    Text(
                        text = countdown.toString(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                } else {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = Color.White,
                            trackColor = Color.Transparent
                        )
                    } else {
                        Text(
                            text = "Send",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    )
}
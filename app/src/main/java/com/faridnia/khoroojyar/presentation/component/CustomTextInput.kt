package com.faridnia.khoroojyar.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@LightAndDarkPreview
@Composable
fun PreviewMyTextInput() {
    KhoroojYarTheme {
        Column {
            CustomTextInput(
                onTextChanged = {},
                hasError = true,
                labelText = "text 1",
                supportingText = "error message",
                iconRes = R.drawable.ic_setting,
            )

            CustomTextInput(
                onTextChanged = {},
                labelText = "text 2",
                supportingText = "supportingText",
                iconRes = R.drawable.ic_setting,
            )
        }
    }
}

@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    maxChar: Int = 70,
    hasError: Boolean = false,
    labelText: String = "",
    supportingText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    @DrawableRes iconRes: Int? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    val iconsColor = MaterialTheme.colorScheme.surfaceTint
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                onValueChange(newText)
            }
        },
        leadingIcon = {
            iconRes?.let {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "text",
                    tint = iconsColor
                )
            }
        },
        singleLine = true,
        isError = hasError && value.isNotEmpty(),
        shape = RoundedCornerShape(size = 18.dp),
        supportingText = if (value.isNotEmpty() && supportingText.isNotEmpty() && hasError) {
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor),
                    text = supportingText,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.iran_sans_mobile_fa_num)),
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.error,
                    )
                )
            }
        } else null,
        label = { CustomText(text = labelText) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            cursorColor = focusedColor,
            focusedLabelColor = focusedColor,
            unfocusedLabelColor = iconsColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedBorderColor = focusedColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTrailingIconColor = iconsColor,
            unfocusedTrailingIconColor = iconsColor,
            focusedLeadingIconColor = iconsColor,
            unfocusedLeadingIconColor = iconsColor,
            errorContainerColor = backgroundColor,
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Center,
            textDirection = TextDirection.Ltr,
        ),
        trailingIcon = {
            if (value.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable {
                        onValueChange("")
                    },
                    painter = painterResource(id = R.drawable.ic_close_square),
                    contentDescription = "clear"
                )
            }
        }

    )
}

@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    maxChar: Int = 70,
    hasError: Boolean = false,
    labelText: String,
    supportingText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    @DrawableRes iconRes: Int? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    val textInput = remember {
        mutableStateOf("")
    }

    val iconsColor = MaterialTheme.colorScheme.surfaceTint
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        value = textInput.value,
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                textInput.value = newText
                onTextChanged(textInput.value)
            }
        },
        leadingIcon = {
            iconRes?.let {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "text",
                    tint = iconsColor
                )
            }
        },
        singleLine = true,
        isError = hasError && textInput.value.isNotEmpty(),
        shape = RoundedCornerShape(size = 18.dp),
        supportingText = if (textInput.value.isNotEmpty() && supportingText.isNotEmpty() && hasError) {
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor),
                    text = supportingText,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.iran_sans_mobile_fa_num)),
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.error,
                    )
                )
            }
        } else null,
        label = { CustomText(text = labelText) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            cursorColor = focusedColor,
            focusedLabelColor = focusedColor,
            unfocusedLabelColor = iconsColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedBorderColor = focusedColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTrailingIconColor = iconsColor,
            unfocusedTrailingIconColor = iconsColor,
            focusedLeadingIconColor = iconsColor,
            unfocusedLeadingIconColor = iconsColor,
            errorContainerColor = backgroundColor,
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Center,
            textDirection = TextDirection.Ltr,
        ),
        trailingIcon = {
            if (textInput.value.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable {
                        textInput.value = ""
                        onTextChanged(textInput.value)
                    },
                    painter = painterResource(id = R.drawable.ic_close_square),
                    contentDescription = "clear"
                )
            }
        }
    )
}
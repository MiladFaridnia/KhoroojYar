package com.faridnia.khoroojyar.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    colors: ButtonColors? = null,
    @DrawableRes iconId: Int? = null,
    iconColor: Color? = null,
    isSpacerNeeded: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        colors = colors ?: ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterVertically),
                color = colors?.contentColor ?: MaterialTheme.colorScheme.onSurfaceVariant,
                strokeWidth = 2.dp
            )
        } else {
            CustomText(
                text = text,
                style = textStyle,
                color = colors?.contentColor ?: MaterialTheme.colorScheme.onSurfaceVariant
            )
            iconId?.let { iconResourceId ->
                if (isSpacerNeeded) {
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Icon(
                    tint = iconColor ?: Color.Unspecified,
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = iconResourceId),
                    contentDescription = text
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
private fun PreviewCustomButton() {
    KhoroojYarTheme {
        CustomButton(
            text = stringResource(id = R.string.app_name),
            onClick = {},
            isEnabled = true
        )
    }
}
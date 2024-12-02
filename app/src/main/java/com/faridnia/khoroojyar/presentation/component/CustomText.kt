package com.faridnia.khoroojyar.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign? = TextAlign.Right,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        letterSpacing = letterSpacing
    )
}

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textAlign: TextAlign? = TextAlign.Right,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        letterSpacing = letterSpacing
    )
}

@LightAndDarkPreview
@Composable
private fun PreviewCustomText() {
    CustomText(text = "Preview")
}

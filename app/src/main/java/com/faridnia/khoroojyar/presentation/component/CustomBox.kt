package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun CustomBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        content()
    }
}

@LightAndDarkPreview
@Composable
fun PreviewCustomBox(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        CustomBox(modifier = modifier.fillMaxSize()) {}
    }
}
package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun ExtraRoundCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier
            .clip(RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(top = 16.dp)
    ) {
        content()
    }
}

@LightAndDarkPreview
@Composable
fun PreviewExtraRoundCard() {
    KhoroojYarTheme {
        ExtraRoundCard(modifier = Modifier.fillMaxSize()) {
        }
    }
}

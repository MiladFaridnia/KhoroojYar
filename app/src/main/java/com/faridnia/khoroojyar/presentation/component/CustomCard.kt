package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ),
    content: @Composable ColumnScope.() -> Unit,
) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        colors = colors
    ) {
        content()
    }
}

@LightAndDarkPreview
@Composable
fun PreviewCustomCard(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        CustomCard(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {}
    }
}
package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.theme.Caribbean_Green
import com.faridnia.khoroojyar.presentation.theme.Fence_Green
import com.faridnia.khoroojyar.presentation.theme.Honeydew
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun CustomProgressBar(percentage: Int, amount: String) {
    val backgroundColor = Caribbean_Green
    val progressColor = Fence_Green

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
    ) {
        // Progress fill
        Box(
            modifier = Modifier
                .fillMaxWidth(percentage / 100f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(15.dp))
                .background(progressColor)
        )

        // Overlay content
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$percentage%",
                color = Honeydew,
                style = MaterialTheme.typography.bodyMedium
            )

            // Amount text
            Text(
                text = amount,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewCustomProgressbar() {
    KhoroojYarTheme {
        CustomProgressBar(percentage = 70, amount = "1000")
    }
}

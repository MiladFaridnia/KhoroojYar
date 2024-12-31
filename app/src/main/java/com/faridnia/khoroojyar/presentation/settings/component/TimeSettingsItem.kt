package com.faridnia.khoroojyar.presentation.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun TimeSettingsItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    title: String,
    time: String? = null
) {
    val containerColor = colorScheme.outlineVariant
    val contentColor = colorScheme.onBackground
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .padding(16.dp)
    ) {
        CustomText(
            text = title,
            color = contentColor
        )
        time?.let {
            CustomText(text = it)
        } ?: Icon(
            imageVector = Icons.Outlined.Create,
            tint = contentColor,
            contentDescription = null
        )
    }
}

@LightAndDarkPreview
@Composable
private fun TimeSettingsItemPreview() {
    KhoroojYarTheme {
        TimeSettingsItem(title = "Earliest Start", time = "09:00")
    }
}
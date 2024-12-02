package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.theme.Fence_Green
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun DateButton(
    modifier: Modifier = Modifier,
    label: String,
    dateButtonType: DateButtonType,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(1.dp),
            painter = painterResource(dateButtonType.icon),
            contentDescription = "Calendar Icon",
            tint = Fence_Green
        )
    }
}

@LightAndDarkPreview
@Composable
private fun PreviewDateButton() {
    KhoroojYarTheme {
        DateButton(
            label = "April 30, 2024",
            dateButtonType = DateButtonType.DATE
        )
    }

}

package com.faridnia.khoroojyar.presentation.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun GreetingWithIcon(
    greetingText: String,
    subText: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            CustomText(
                text = greetingText,
                style = MaterialTheme.typography.bodyLarge
            )

            CustomText(
                text = subText,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "Notification Icon",
                tint = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewGreetingWithIcon() {
    KhoroojYarTheme {
        GreetingWithIcon("Hi Agha Milad", subText = "Good Morning", painterResource(R.drawable.ic_sun) )
    }
}

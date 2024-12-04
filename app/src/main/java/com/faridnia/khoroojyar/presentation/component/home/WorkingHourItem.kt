package com.faridnia.khoroojyar.presentation.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun WorkingHourItem(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_sun),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSecondary,
        )

        Column(Modifier.padding(horizontal = 16.dp)) {
            CustomText(text = "Saturday", style = MaterialTheme.typography.titleMedium)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    modifier = Modifier.weight(1f),
                    text = "9:00 - 14:00", style = MaterialTheme.typography.bodySmall
                )
                VerticalDivider(
                    modifier = Modifier
                        .height(10.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    thickness = 1.dp
                )
                CustomText(
                    modifier = Modifier.weight(1f),
                    text = "14:47 - 18:00", style = MaterialTheme.typography.bodySmall
                )

                VerticalDivider(
                    modifier = Modifier
                        .height(10.dp)
                        .padding(horizontal = 8.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    thickness = 1.dp
                )
                CustomText(
                    modifier = Modifier.weight(1f),
                    text = "14:47 - 18:00", style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }
}


@LightAndDarkPreview
@Composable
fun PreviewWorkingHourItem(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        WorkingHourItem()
    }
}
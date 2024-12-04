package com.faridnia.khoroojyar.presentation.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    dayOfMonth: String,
    dayOfWeek: String
) {
    Column(modifier = modifier.padding(8.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calendar),
                contentDescription = "Today",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(4.dp))

            CustomText(text = dayOfWeek)
        }

        Spacer(modifier = Modifier.height(4.dp))

        CustomText(
            text = dayOfMonth, style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily(Font(R.font.iran_sans_mobile_fa_num))
            )
        )

    }
}

@LightAndDarkPreview
@Composable
fun PreviewDateItem(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        DateItem(modifier, "27 September", "Saturday")
    }
}
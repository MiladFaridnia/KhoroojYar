package com.faridnia.khoroojyar.presentation.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun DateTimeHeader(
    modifier: Modifier = Modifier,
    firstDate: DateItem,
    secondDate: DateItem
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateItem(
            modifier = Modifier.weight(1f),
            dayOfMonth = firstDate.dayOfMonth,
            dayOfWeek = firstDate.dayOfWeek
        )

        VerticalDivider(
            modifier = Modifier
                .heightIn(min = 25.dp, max = 30.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface
        )

        DateItem(
            modifier = Modifier.weight(1f),
            dayOfMonth = secondDate.dayOfMonth,
            dayOfWeek = secondDate.dayOfWeek
        )
    }
}

data class DateItem(val dayOfMonth: String, val dayOfWeek: String)

@LightAndDarkPreview
@Composable
fun PreviewDateTimeHeader() {
    KhoroojYarTheme {
        DateTimeHeader(
            firstDate = DateItem("27 September", "Saturday"),
            secondDate = DateItem("27", "Saturday")
        )
    }
}
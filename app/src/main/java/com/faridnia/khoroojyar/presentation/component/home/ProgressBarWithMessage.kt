package com.faridnia.khoroojyar.presentation.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.faridnia.khoroojyar.presentation.component.CustomProgressBar
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun ProgressBarWithMessage(
    modifier: Modifier = Modifier,
    percentage: Int,
    amount: String
) {
    Column(modifier = modifier) {
        CustomProgressBar(
            percentage = percentage,
            amount = amount
        )

        PercentagePassedMessage(percentage)
    }
}

@Composable
private fun PercentagePassedMessage(percentage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = true, onCheckedChange = {})
        CustomText(text = "$percentage % Of Month Passed, Looks Good")
    }
}

@LightAndDarkPreview
@Composable
fun PreviewProgressBarWithMessage() {
    KhoroojYarTheme {
        ProgressBarWithMessage(
            percentage = 37,
            amount = "0f 31 days"
        )
    }
}
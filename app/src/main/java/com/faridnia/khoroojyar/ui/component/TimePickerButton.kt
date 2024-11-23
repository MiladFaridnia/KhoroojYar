package com.faridnia.khoroojyar.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.faridnia.khoroojyar.ui.theme.KhoroojYarTheme

@Composable
fun TimePickerButton(label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimePickerButton(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        TimePickerButton(label = "Select Time", onClick = {})
    }
}

package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun InputWithTitleItem(title: String, keyboardType: KeyboardType = KeyboardType.Text) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        CustomText(modifier = Modifier.padding(0.dp), text = title)
        CustomTextInput(
            modifier = Modifier.padding(0.dp),
            value = "",
            onValueChange = {},
            keyboardType = keyboardType
        )
    }
}

@LightAndDarkPreview
@Composable
private fun InputWithTitlePreview() {
    KhoroojYarTheme {
        InputWithTitleItem(title = "Name", keyboardType = KeyboardType.Text)
    }
}
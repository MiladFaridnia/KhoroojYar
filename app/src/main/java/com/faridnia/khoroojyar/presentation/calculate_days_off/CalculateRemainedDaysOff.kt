package com.faridnia.khoroojyar.presentation.calculate_days_off

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.ui.theme.KhoroojYarTheme

@Composable
fun CalculateRemainedDaysOff(
    viewModel: CalculateRemainedDaysOffViewModel = viewModel(),
    onDismiss: () -> Unit
) {
    val hoursInput = viewModel.hoursInput.value
    val calculatedDaysOff = viewModel.calculatedDaysOff.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.calculate_remained_days_off),
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = hoursInput,
            onValueChange = { viewModel.onHoursInputChange(it) },
            label = { Text(stringResource(R.string.enter_total_hours)) },
            placeholder = { Text(stringResource(R.string.e_g_35)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (calculatedDaysOff.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(), // Ensures the Box takes up the entire available space
                contentAlignment = Alignment.Center // Centers the content both horizontally and vertically
            ) {
                Text(
                    text = stringResource(R.string.you_have_days_off_remaining, calculatedDaysOff),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF35BD69),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculateRemainedDaysOff(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        CalculateRemainedDaysOff(onDismiss = {})
    }
}

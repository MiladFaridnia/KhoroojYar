package com.faridnia.khoroojyar.presentation.calculate_days_off

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
            text = "Calculate Remained Days Off",
            style = MaterialTheme.typography.titleMedium
        )

        // Input field for hours
        OutlinedTextField(
            value = hoursInput,
            onValueChange = { viewModel.onHoursInputChange(it) },
            label = { Text("Enter total hours") },
            placeholder = { Text("e.g., 35") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Display the calculated days off
        if (calculatedDaysOff.isNotEmpty()) {
            Text(
                text = stringResource(R.string.you_have_days_off_remaining, calculatedDaysOff),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = { onDismiss() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.close))
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

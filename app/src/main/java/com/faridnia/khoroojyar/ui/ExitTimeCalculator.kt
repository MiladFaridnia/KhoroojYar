package com.faridnia.khoroojyar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.ui.theme.KhoroojYarTheme

@Composable
fun ExitTimeCalculator(viewModel: ExitTimeViewModel = viewModel()) {
    val enterTimeInput = viewModel.enterTimeInput.value
    val exitTime = viewModel.exitTime.value
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.enter_your_entry_time_hh_mm),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = enterTimeInput,
            onValueChange = { viewModel.onEnterTimeChange(it) },
            placeholder = { Text(stringResource(R.string.e_g_09_15)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                keyboardController?.hide() // Close the keyboard
                viewModel.calculateExitTime()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.calculate_exit_time))
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (exitTime.isNotEmpty()) {
            Text(
                text = stringResource(R.string.exit_time, exitTime),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExitTimeCalc(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        ExitTimeCalculator()
    }
}

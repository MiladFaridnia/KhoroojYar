package com.faridnia.khoroojyar.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.ui.theme.KhoroojYarTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun ExitTimeCalculator() {
    var enterTimeInput by remember { mutableStateOf("") }
    var exitTime by remember { mutableStateOf("") }
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Your Entry Time (HH:mm)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = enterTimeInput,
            onValueChange = { enterTimeInput = it },
            placeholder = { Text("e.g., 09:15") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                keyboardController?.hide() // Close the keyboard
                try {
                    val enterTime = LocalTime.parse(enterTimeInput, timeFormatter)
                    val exitTimeCalculated = enterTime.plusHours(8).plusMinutes(45)
                    exitTime = exitTimeCalculated.format(timeFormatter)
                } catch (e: DateTimeParseException) {
                    exitTime = "Invalid time format"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Exit Time")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (exitTime.isNotEmpty()) {
            Text(
                text = "Exit Time: $exitTime",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExitTimeCalc(modifier: Modifier = Modifier) {
    KhoroojYarTheme{
        ExitTimeCalculator()
    }
}

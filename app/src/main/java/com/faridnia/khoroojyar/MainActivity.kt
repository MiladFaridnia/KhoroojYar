package com.faridnia.khoroojyar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.faridnia.khoroojyar.ui.ExitTimeCalculator
import com.jrg.app.ui.component.snackbar.CustomScaffold

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomScaffold {
                KhoroojYar()
            }
        }
    }
}

@Composable
fun KhoroojYar() {
    MaterialTheme {
        ExitTimeCalculator()
    }
}
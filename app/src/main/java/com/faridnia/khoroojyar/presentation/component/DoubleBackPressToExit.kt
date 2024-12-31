package com.faridnia.khoroojyar.presentation.component

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import com.faridnia.khoroojyar.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DoubleBackPressToExit() {
    val isPreview = LocalInspectionMode.current
    val pressBackAgainToExit = stringResource(id = R.string.press_back_again_to_exit)
    if (!isPreview) {
        var exit by rememberSaveable {
            mutableStateOf(false)
        }

        val activity = LocalContext.current as Activity

        val scope = rememberCoroutineScope()

        LaunchedEffect(exit) {
            if (exit) {
                delay(2000)
                exit = false
            }
        }

        BackHandler(enabled = true) {
            if (exit) {
                activity.finish()
            } else {
                exit = true
                scope.launch {
                    Toast.makeText(activity, pressBackAgainToExit, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
package com.jrg.app.ui.component.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.faridnia.khoroojyar.presentation.component.snackbar.ObserveAsEvents
import com.faridnia.khoroojyar.presentation.component.snackbar.SnackbarController
import com.faridnia.khoroojyar.presentation.component.snackbar.SnackbarType
import kotlinx.coroutines.launch

@Composable
fun CustomScaffold(
    snackbarType: SnackbarType = SnackbarType.ERROR,
    snackbarDuration: SnackbarDuration = SnackbarDuration.Short,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val containerColor = snackbarType.backgroundColor
    val contentColor = snackbarType.contentColor
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            if (event.message.isNotEmpty()) {
                val result = snackbarHostState.showSnackbar(
                    message = event.message,
                    actionLabel = event.action?.name,
                    duration = snackbarDuration
                )
                if (result == SnackbarResult.ActionPerformed) {
                    event.action?.action?.invoke()
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = bottomBar,
        snackbarHost = {
            Box(modifier = Modifier.fillMaxSize()) {
                SnackbarHost(
                    modifier = Modifier.align(Alignment.TopCenter),
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = containerColor,
                            contentColor = contentColor
                        )
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}
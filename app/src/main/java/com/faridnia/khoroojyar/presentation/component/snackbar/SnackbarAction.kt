package com.faridnia.khoroojyar.presentation.component.snackbar

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)
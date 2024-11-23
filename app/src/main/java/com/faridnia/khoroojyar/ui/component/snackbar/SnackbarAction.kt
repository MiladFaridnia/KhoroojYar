package com.faridnia.khoroojyar.ui.component.snackbar

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)
package com.faridnia.khoroojyar.presentation.component.snackbar

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)
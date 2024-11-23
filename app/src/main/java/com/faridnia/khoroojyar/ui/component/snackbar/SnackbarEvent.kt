package com.faridnia.khoroojyar.ui.component.snackbar

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null
)
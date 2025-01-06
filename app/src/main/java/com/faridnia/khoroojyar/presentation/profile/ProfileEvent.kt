package com.faridnia.khoroojyar.presentation.profile

sealed class ProfileEvent {
    data class UpdateProfileClicked(
        val userName: String,
        val userEmail: String
    ) : ProfileEvent()

    data object EditPhotoClicked : ProfileEvent()
}
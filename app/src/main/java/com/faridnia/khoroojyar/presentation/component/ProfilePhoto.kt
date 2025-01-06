package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileComponent(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    onEditClick: () -> Unit,
    photoSize: Dp = 150.dp
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
            .padding(0.dp)
            .size(photoSize)
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .size(photoSize)
        ) {
            GlideImage(
                model = profileImageUrl,
                contentDescription = "Profile Image",
                transition = CrossFade,
                loading = placeholder(R.drawable.ic_profile_tick),
                failure = placeholder(R.drawable.ic_profile_tick)
            )
        }

        IconButton(
            onClick = onEditClick,
            modifier = Modifier
                .background(
                    color = colorScheme.primary,
                    shape = CircleShape
                )
                .padding(10.dp)
                .size(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Edit Profile Icon",
                tint = colorScheme.onPrimary
            )
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewProfileScreen() {
    KhoroojYarTheme {
        ProfileComponent(
            profileImageUrl = "https://avatar.iran.liara.run/public",
            onEditClick = { }
        )
    }

}
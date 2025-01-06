package com.faridnia.khoroojyar.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomBox
import com.faridnia.khoroojyar.presentation.component.CustomButton
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.ExtraRoundCard
import com.faridnia.khoroojyar.presentation.component.InputWithTitleItem
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.component.ProfileComponent
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileContent(state = state, onEvent = viewModel::onEvent)

}

@Composable
fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {

    val scrollState = rememberScrollState()

    CustomBox(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.scrollable(scrollState, orientation = Orientation.Vertical)) {

            Column(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection()
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter,
            ) {
                ExtraRoundCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 75.dp)
                ) {
                    Spacer(modifier = Modifier.height(120.dp)) //Trust me you'll need this

                    InputWithTitleItem(title = "Name", keyboardType = KeyboardType.Text)

                    Spacer(modifier = Modifier.height(8.dp))

                    InputWithTitleItem(title = "Phone", keyboardType = KeyboardType.Phone)

                    Spacer(modifier = Modifier.height(8.dp))

                    InputWithTitleItem(title = "Email Address", keyboardType = KeyboardType.Email)

                    Spacer(modifier = Modifier.height(8.dp))

                    SelectGenderSection()

                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButton(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.save),
                        onClick = {})

                    Spacer(modifier = Modifier.height(12.dp))

                }

                ProfilePhotoSection(circleSize = 130.dp, state = state, onEvent = onEvent)
            }
        }
    }
}

@Composable
private fun SelectGenderSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {

        CustomText(text = "Gender")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { },
            modifier = Modifier
                .background(
                    color = colorScheme.primary,
                    shape = CircleShape
                )
                .padding(10.dp)
                .size(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_man),
                contentDescription = "Edit Profile Icon",
                tint = colorScheme.onPrimary
            )
        }

        IconButton(
            onClick = { },
            modifier = Modifier
                .background(
                    color = colorScheme.primary,
                    shape = CircleShape
                )
                .padding(10.dp)
                .size(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_woman),
                contentDescription = "Edit Profile Icon",
                tint = colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun ProfilePhotoSection(
    circleSize: Dp = 120.dp,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileComponent(
            modifier = Modifier,
            profileImageUrl = state.profileUrl,
            photoSize = circleSize,
            onEditClick = { onEvent(ProfileEvent.EditPhotoClicked) }
        )
        CustomText(
            text = state.username,
            style = MaterialTheme.typography.bodyLarge
        )
        CustomText(
            text = state.phoneNumber, style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_left),
            contentDescription = null,
            tint = colorScheme.onPrimaryContainer,
        )

        CustomText(text = "Profile")

        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(colorScheme.onSurfaceVariant),

            contentAlignment = Alignment.Center
        ) {
            Icon(

                painter = painterResource(R.drawable.ic_profile_tick),
                contentDescription = "Notification Icon",
                tint = colorScheme.surfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@LightAndDarkPreview
@Composable
private fun ProfileScreenPreview() {
    KhoroojYarTheme {
        ProfileContent(
            state = ProfileState(username = "Farshad", phoneNumber = "09123456789"),
            onEvent = {})


    }
}
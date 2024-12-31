package com.faridnia.khoroojyar.presentation.component.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Analytics,
        BottomNavItem.Settings
    )
    NavigationBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clip(RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp)),
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = {
                    if (!item.selected) {
                        navController.navigate(item.route)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                ),
                icon = {
                    item.icon?.let { navIcon ->
                        when (navIcon) {
                            is NavIcon.PainterId -> Icon(
                                painter = painterResource(navIcon.iconId),
                                contentDescription = stringResource(item.titleId)
                            )

                            is NavIcon.Vector -> Icon(
                                imageVector = navIcon.icon,
                                contentDescription = stringResource(item.titleId)
                            )
                        }
                    }
                },
                label = {
                    if (item.selected) {
                        CustomText(text = stringResource(item.titleId), color = Color.Unspecified)
                    }
                },
            )
        }
    }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        items.forEach { item ->
            item.selected = item.route == destination.route
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewBottomNavigation() {
    KhoroojYarTheme {
        BottomNavigationBar(rememberNavController())
    }
}
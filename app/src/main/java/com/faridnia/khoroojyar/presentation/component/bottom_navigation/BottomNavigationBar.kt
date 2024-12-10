package com.faridnia.khoroojyar.presentation.component.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }

    val unselectedItems = listOf(
        BottomNavItem("Home", painterResource(R.drawable.ic_home)),
        BottomNavItem("Analytics", painterResource(R.drawable.ic_cube)),
        BottomNavItem("Profile", painterResource(R.drawable.ic_setting))
    )

    val selectedItems = listOf(
        BottomNavItem("Home", painterResource(R.drawable.ic_home_fill)),
        BottomNavItem("Analytics", painterResource(R.drawable.ic_cube_fill)),
        BottomNavItem("Profile", painterResource(R.drawable.ic_setting_fill))
    )

    NavigationBar(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .clip(RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp)),
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 8.dp
    ) {
        selectedItems.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    unselectedTextColor = MaterialTheme.colorScheme.outline,
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Icon(
                        painter = if (selectedItem == index) selectedItems[index].icon else unselectedItems[index].icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }

}

data class BottomNavItem(val title: String, val icon: Painter)

@LightAndDarkPreview
@Composable
fun PreviewBottomNavigation() {
    KhoroojYarTheme {
        BottomNavigationBar()
    }
}
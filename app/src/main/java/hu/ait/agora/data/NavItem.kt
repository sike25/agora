package hu.ait.agora.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import hu.ait.agora.ui.navigation.Screen

val navItems = listOf (
    NavItemState (
        screen = Screen.ListProduct.route,
        icon = Icons.Filled.AddCircle,
    ),
    NavItemState (
        screen = Screen.Feed.route,
        icon = Icons.Filled.ShoppingCart,
    ),
    NavItemState (
        screen = Screen.Profile.route,
        icon = Icons.Filled.Person,
    ),
)
data class NavItemState(
    val screen : String,
    val icon   : ImageVector,
)

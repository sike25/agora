package hu.ait.agora.ui.navigation

sealed class Screen (val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register: Screen("register")
    data object Feed : Screen("feed")
    data object ListProduct : Screen ("list_product")
    data object SearchResults : Screen("search_results")
    data object Product : Screen("product")
    data object Profile : Screen("profile")
}
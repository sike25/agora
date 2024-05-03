package hu.ait.agora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.ait.agora.ui.navigation.Screen
import hu.ait.agora.ui.screen.feed.FeedScreen
import hu.ait.agora.ui.screen.list_product.ListProductScreen
import hu.ait.agora.ui.screen.login.LoginScreen
import hu.ait.agora.ui.screen.login.RegisterScreen
import hu.ait.agora.ui.screen.product.ProductScreen
import hu.ait.agora.ui.screen.search_results.SearchResultsScreen
import hu.ait.agora.ui.screen.splash.SplashScreen
import hu.ait.agora.ui.theme.AgoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgoraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShopNavHost()
                }
            }
        }
    }
}

@Composable
fun ShopNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.ListProduct.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen()
        }
        composable(Screen.Register.route) {
            RegisterScreen()
        }
        composable(Screen.Feed.route) {
            FeedScreen()
        }
        composable(Screen.ListProduct.route) {
            ListProductScreen()
        }
        composable(Screen.SearchResults.route) {
            SearchResultsScreen()
        }
        composable(Screen.Product.route) {
            ProductScreen()
        }
    }
}

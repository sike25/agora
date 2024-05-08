package hu.ait.agora.ui.screen.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import hu.ait.agora.R
import hu.ait.agora.data.Product
import kotlinx.coroutines.flow.flowOf

class FeedViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    val productFlow = flowOf(
        listOf(
            Product (
                image = R.drawable.ramen,
                title = "Ramen",
                description = "Yummy yummy and artisanal",
                price = 0.99,
                owner = "Barbie Mattel"

            ),
            Product (
                image = R.drawable.lamp,
                title = "Lava Lamp",
                description = "My grandmother;s lamp needs a new home",
                price = 10.0,
                owner = "Dwight Schrute"
            ),
            Product (
                image = R.drawable.bicycle,
                title = "Bicycle",
                description = "Who wants my bicycle when I graduate?",
                price = 200.0,
                owner = "Lynca Kaminka"
            ),
        )
    )


}



sealed interface ProductsUIState {
    object Init : ProductsUIState
    object Loading : ProductsUIState
    data class Success(val postList: List<Product>) : ProductsUIState
    data class Error(val error: String?) : ProductsUIState
}
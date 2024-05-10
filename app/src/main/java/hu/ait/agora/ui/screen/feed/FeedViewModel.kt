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

    val tempList: List<Product> = emptyList()
    val productFlow = flowOf( tempList
//        listOf(
//            Product (
//                image = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/ambika.jpg?alt=media&token=a65d8e65-81c9-4617-8306-39b7080d2f6d",
//                title = "Ramen",
//                description = "Yummy yummy and artisanal",
//                price = 0.99,
//                owner = "Barbie Mattel",
//                category = "Food"
//
//            ),
//            Product (
//                image = R.drawable.lamp,
//                title = "Lava Lamp",
//                description = "My grandmother;s lamp needs a new home",
//                price = 10.0,
//                owner = "Dwight Schrute"
//            ),
//            Product (
//                image = R.drawable.bicycle,
//                title = "Bicycle",
//                description = "Who wants my bicycle when I graduate?",
//                price = 200.0,
//                owner = "Lynca Kaminka"
//            ),
//        )
    )


}



sealed interface ProductsUIState {
    object Init : ProductsUIState
    object Loading : ProductsUIState
    data class Success(val postList: List<Product>) : ProductsUIState
    data class Error(val error: String?) : ProductsUIState
}
package hu.ait.agora.ui.screen.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.agora.data.Product
import hu.ait.agora.data.ProductWithId
import hu.ait.agora.data.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FeedViewModel : ViewModel() {
//    var productsUiState: ProductsUIState by mutableStateOf(ProductsUIState.Init)

    fun productsList() = callbackFlow {
        val snapshotListener =
            FirebaseFirestore.getInstance().collection("products")
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val productList = snapshot.toObjects(Product::class.java)
                        val productWithIdList = mutableListOf<ProductWithId>()
                        productList.forEachIndexed { index, product ->
                            productWithIdList.add(ProductWithId(snapshot.documents[index].id, product))
                        }
                        ProductsUIState.Success(productWithIdList)
                    } else {
                        Log.d("MY_TEST", "error somewhere")
                        ProductsUIState.Error(e?.message.toString())
                    }

                    trySend(response)
                }
        awaitClose {
            snapshotListener.remove()
        }
    }


    // for product screen
    var product: ProductWithId = ProductWithId(
        product = Product (
            image = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/ambika_mod.jpg?alt=media&token=d3df85eb-f7f1-41f7-9ced-ee79b944fcc6",
            title = "This is Ambika Mod",
            description = "She is here because something went wrong with the product that should have been displayed.",
            price = 0.99,
            category = "Food",
            owner = User (
                name = "Ambika Mod",
                email = "ambikamod@amherst.edu",
                firebaseUID = ""
            ),
        ),
        productId = ""
    )
    fun setProductToShow( productToShow: ProductWithId) {
        product = productToShow
    }
    fun getProductToShow(): ProductWithId {
        return product
    }
}



sealed interface ProductsUIState {
    data object Init : ProductsUIState
    data object Loading : ProductsUIState
    data class Success(val productList: List<ProductWithId>) : ProductsUIState
    data class Error(val error: String?) : ProductsUIState
}
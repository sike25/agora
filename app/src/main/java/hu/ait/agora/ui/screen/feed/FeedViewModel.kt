package hu.ait.agora.ui.screen.feed

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.agora.data.Product
import hu.ait.agora.data.ProductWithId
import hu.ait.agora.data.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FeedViewModel : ViewModel() {
    // var productsUiState: ProductsUIState by mutableStateOf(ProductsUIState.Init)

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
    var product: ProductWithId by mutableStateOf(
        ProductWithId(
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
    )


    fun setProductToShow( productToShow: ProductWithId) {
        product = productToShow
    }
    fun getProductToShow(): ProductWithId {
        return product
    }


    // for acquisition
    fun sendEmail(context: Context) {
        val itemName:    String = product.product.title
        val sellerName:  String = product.product.owner.name
        val sellerEmail: String = product.product.owner.email
        val buyerName:   String = FirebaseAuth.getInstance().currentUser?.displayName ?: "Error"
        val buyerEmail:  String = FirebaseAuth.getInstance().currentUser?.email ?: "Error"

        val title = "Inquiry to Purchase Your Listed Item on Agora"
        val body: String =
            "Dear $sellerName,\n" + "\n" +
                    "I hope this message finds you well. " +
                    "I am contacting you through Agora regarding the '$itemName' " +
                    "you have listed for sale as I am very interested in this item. " + "\n" + "\n" +
                    "Please let me know the best way to proceed with the purchase. " + "\n" + "\n" +
                    "Thank you for your time. I look forward to your prompt response " +
                    "and hope we can finalize the transaction smoothly.\n" + "\n" +
                    "Best regards,\n" +
                    "\n" +
                    "$buyerName\n" +
                    buyerEmail

        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(sellerEmail))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        mIntent.putExtra(Intent.EXTRA_TEXT, body)
        try {
            context.startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}



sealed interface ProductsUIState {
    data object Init : ProductsUIState
    data object Loading : ProductsUIState
    data class Success(val productList: List<ProductWithId>) : ProductsUIState
    data class Error(val error: String?) : ProductsUIState
}
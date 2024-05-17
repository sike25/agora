package hu.ait.agora.ui.screen.list_product

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import hu.ait.agora.data.Product
import hu.ait.agora.data.User
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.UUID

class ListScreenViewModel : ViewModel() {

    var listProductUiState: ListProductUiState by mutableStateOf(ListProductUiState.Init)

    var imageUri: Uri by mutableStateOf(Uri.EMPTY)
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var price by mutableStateOf("")
    var category by mutableStateOf("Food")
    var tags = mutableStateListOf<String>()
        private set

    fun allInputsValid(): Boolean {
        val priceFieldIsValid = try {
            price.toDouble()
            true
        }
        catch (e: Exception) {
            false
        }

        return title.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty() &&
                imageUri != Uri.EMPTY && priceFieldIsValid
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun uploadProductImage(
        contentResolver: ContentResolver,
    ) {
        viewModelScope.launch {
            listProductUiState = ListProductUiState.LoadingImageUpload

            val source = ImageDecoder.createSource(contentResolver, imageUri)
            val bitmap = ImageDecoder.decodeBitmap(source)

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageInBytes = baos.toByteArray()

            val storageRef = FirebaseStorage.getInstance().getReference()
            val newImage = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
            val newImagesRef = storageRef.child("images/$newImage")

            newImagesRef.putBytes(imageInBytes)
                .addOnFailureListener { e ->
                    listProductUiState = ListProductUiState.ErrorDuringImageUpload(e.message)
                }.addOnSuccessListener { _ ->
                    listProductUiState = ListProductUiState.ImageUploadSuccess
                    newImagesRef.downloadUrl.addOnCompleteListener { task ->
                        uploadProduct(
                            productImage = task.result.toString(),
                            productTitle = title,
                            productDescription = description,
                            productPrice = price.toDouble(),
                            productCategory = category,
                            productTags = tags
                        )
                    }
                }
        }
    }


    private fun uploadProduct(
        productImage: String,
        productTitle: String,
        productDescription: String,
        productPrice: Double,
        productCategory: String,
        productTags: List<String>,
    ) {
        listProductUiState = ListProductUiState.LoadingProductUpload

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val newProduct = Product(
                image = productImage,
                title = productTitle,
                description = productDescription,
                price = productPrice,
                category = productCategory,
                tags = productTags,
                owner = User(
                    name = currentUser.displayName!!,
                    email = currentUser.email!!,
                    firebaseUID = currentUser.uid
                ),
            )
            addProductToFirebaseCollection(newProduct)

        } else {
            listProductUiState = ListProductUiState.ErrorDuringProductUpload("No authenticated user found")
        }

    }

    private fun addProductToFirebaseCollection(newProduct: Product) {
        val productCollection = FirebaseFirestore.getInstance().collection("products")
        productCollection.add(newProduct)
            .addOnSuccessListener { documentReference ->
                updateUserWithListedItem(productId = documentReference.id)
                listProductUiState = ListProductUiState.ProductUploadSuccess
            }
            .addOnFailureListener {
                listProductUiState = ListProductUiState.ErrorDuringProductUpload("Upload failed")
            }
    }

    private fun updateUserWithListedItem(productId: String) {
        val userCollection = FirebaseFirestore.getInstance().collection("users")
        val userRef = userCollection.document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result == null || !task.result.exists()) {
                    Log.d("AGORA", "Current user does not exist in users collection.")
                } else {
                    Log.d("AGORA", "Fetching user from users collection failed -> ", task.exception
                    )
                }
            }

            userRef.update("listedItems", FieldValue.arrayUnion(productId))
                .addOnSuccessListener {
                    Log.d("AGORA", "Updated user's listed items")
                }
                .addOnFailureListener { e ->
                    listProductUiState =
                        ListProductUiState.ErrorDuringProductUpload("Error updating user with new product ID -> $e")
                }
        }

    }
}


sealed interface ListProductUiState {
    data object Init : ListProductUiState

    data object InputError: ListProductUiState
    data object LoadingProductUpload : ListProductUiState
    data object ProductUploadSuccess : ListProductUiState
    data class ErrorDuringProductUpload(val error: String?) : ListProductUiState

    data object LoadingImageUpload : ListProductUiState
    data class ErrorDuringImageUpload(val error: String?) : ListProductUiState
    data object ImageUploadSuccess : ListProductUiState
}
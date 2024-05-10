package hu.ait.agora.ui.screen.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.agora.data.User

class ProfileScreenViewModel: ViewModel() {
    private var user by mutableStateOf(
        User(
            profilePicture = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/ambika_mod.jpg?alt=media&token=d3df85eb-f7f1-41f7-9ced-ee79b944fcc6",
            name = "Ambika Mod",
            email = "Ambika is here because there was an error.",
            purchaseHistory = emptyList(),
            firebaseUID = "",
            listedItems = emptyList()
        )

    )
    fun getThisUser(): User {
        try {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser != null) {
                val userCollection = FirebaseFirestore.getInstance().collection("users")
                val userRef = userCollection.document(currentUser.uid)
                userRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            user = User(
                                profilePicture = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/gump.jpg?alt=media&token=70dd2413-5201-4922-8b49-672af2912683",
                                name = document.get("name") as String,
                                email = document.get("email") as String,
                                purchaseHistory = document.get("purchaseHistory") as List<String>? ?: emptyList(),
                                firebaseUID = document.get("firebaseUID") as String,
                                listedItems = document.get("listedItems") as List<String>? ?: emptyList(),
                            )
                        } else {
                            throw IllegalStateException("No such document")
                        }
                    } else {
                        throw IllegalStateException("get failed with " + task.exception)
                    }
                }
            } else {
                throw IllegalStateException("User not logged in")
            }
        } catch (e: Exception) {
            Log.d("MY_TEST", "Error: ${e.message}")
        }

        return user
    }

}
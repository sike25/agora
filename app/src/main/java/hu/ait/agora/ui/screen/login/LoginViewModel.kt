package hu.ait.agora.ui.screen.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.agora.data.User
import kotlinx.coroutines.tasks.await

class LoginViewModel: ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Init)
    var registerUiState: RegisterUiState by mutableStateOf(RegisterUiState.Init)

    fun registerUser(username: String, email:String, password:String) {
        registerUiState = RegisterUiState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener{
                    registerUiState = RegisterUiState.RegisterSuccess
                    setUsernameInFirebase(username)
                    addUserToFirebaseCollection(username, email) // not part of Firebase auth
                }
                .addOnFailureListener{
                    registerUiState = RegisterUiState.Error(it.message)
                }
        } catch(e: Exception) {
            registerUiState = RegisterUiState.Error(e.message)
        }
    }


    private fun setUsernameInFirebase(username: String) {
        val userProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        auth.currentUser?.updateProfile(
            userProfileChangeRequest)?.addOnCompleteListener { profileUpdateTask ->
            if (profileUpdateTask.isSuccessful) {
                Log.d("MY_TEST", "Username is set in firebase.")
            } else {
                Log.e(
                    "MY_TEST",
                    "Username is not in firebase: ",
                    profileUpdateTask.exception
                )
            }
        }
    }

    private fun addUserToFirebaseCollection(username: String, email: String) {
        val newUser = auth.uid?.let { it1 ->
            User(
                name = username,
                email = email,
                firebaseUID = it1
            )
        }
        val usersCollection = FirebaseFirestore.getInstance().collection(
            "users")
        if (newUser != null) {
            usersCollection.document(newUser.firebaseUID)
                .set(newUser)
                .addOnSuccessListener {
                    Log.d("MY_TEST", "Successfully added new user to firebase store.")
                }
                .addOnFailureListener{
                    Log.d("MY_TEST", "Failed to add new user to firebase store.")
                }
        }
        else {
            Log.d("MY_TEST", "Firebase UID is null.")
        }


    }















    suspend fun loginUser(email: String, password: String): AuthResult? {
        loginUiState = LoginUiState.Loading
        return try {
            val result = auth.signInWithEmailAndPassword(email,password).await()
            loginUiState = if (result.user != null) {
                LoginUiState.LoginSuccess
            } else {
                LoginUiState.Error("Login Failed")
            }
            result
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.message)
            null
        }
    }
}

sealed interface LoginUiState {
    data object Init : LoginUiState
    data object Loading : LoginUiState
    data object LoginSuccess : LoginUiState
    data class Error(val error: String?) : LoginUiState
}


sealed interface RegisterUiState {
    data object Init : RegisterUiState
    data object Loading : RegisterUiState
    data object RegisterSuccess : RegisterUiState
    data class Error(val error: String?) : RegisterUiState

}
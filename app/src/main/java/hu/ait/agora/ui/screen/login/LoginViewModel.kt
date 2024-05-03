package hu.ait.agora.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class LoginViewModel: ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth
    private var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Init)

    fun registerUser(email:String, password:String) {
        loginUiState = LoginUiState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener{
                    loginUiState = LoginUiState.RegisterSuccess
                }
                .addOnFailureListener{
                    loginUiState = LoginUiState.Error(it.message)
                }

        } catch(e: Exception) {
            loginUiState = LoginUiState.Error(e.message)
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
    data object RegisterSuccess : LoginUiState
    data class Error(val error: String?) : LoginUiState
}
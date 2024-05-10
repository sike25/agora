package hu.ait.agora.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.ait.agora.data.User
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular
import hu.ait.agora.ui.utils.AgoraBottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {

    var user by remember {
        mutableStateOf(
            User(
                profilePicture = "https://firebasestorage.googleapis.com/v0/b/agora-hills.appspot.com/o/ambika_mod.jpg?alt=media&token=d3df85eb-f7f1-41f7-9ced-ee79b944fcc6",
                name = "Ambika Mod",
                email = "Ambika is here because there was an error.",
                purchaseHistory = emptyList(),
                firebaseUID = "",
                listedItems = emptyList()
            )
        )
    }


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





    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    Icon(imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back arrow",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(start = 10.dp)
                            .clickable { /* onNavigateBack */ })
                },
                modifier = Modifier.padding(10.dp)
            )
        },
        bottomBar = {
            AgoraBottomNavBar(
                navController = navController,
            )
        },

        ) { paddingValues ->
        ProfileScreenContent(
            paddingValues = paddingValues,
            user = user
        )
    }
}

@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues,
    user: User
) {

    Column(
        modifier = Modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(color = agoraLightGrey, thickness = 1.dp)
        Spacer(modifier = Modifier.height(40.dp))

        AsyncImage(
            model =  user.profilePicture,
            contentDescription = user.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = user.name,
            fontFamily = interFamilyRegular,
            fontSize = 25.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = user.email,
            fontFamily = interFamilyRegular,
            fontSize = 14.sp,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(20.dp))

        Divider(color = agoraLightGrey, thickness = 4.dp, modifier = Modifier.width(70.dp))
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Purchase History",
            fontFamily = interFamilyBold,
            fontSize = 15.sp,
            color = agoraPurple,
            modifier = Modifier.clickable { /*TODO*/ }

        )
        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = "Listed Items",
            fontFamily = interFamilyBold,
            fontSize = 15.sp,
            color = agoraPurple,
            modifier = Modifier.clickable {
                /*TODO*/
            }
        )
        Spacer(modifier = Modifier.height(250.dp))

        Text(
            text = "Delete Account",
            fontFamily = interFamilyBold,
            fontSize = 15.sp,
            color = Color.Red,
            modifier = Modifier.clickable {
                /*TODO*/
            }

        )

    }



}
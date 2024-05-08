package hu.ait.agora.ui.screen.profile

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.ait.agora.R
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

    val user = User(
        profilePicture = R.drawable.gump,
        name = "Barbie Mattel",
        email = "barbie@gmail.com",
        purchaseHistory = emptyList(),
        listedItems = emptyList()
    )


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
            model =  R.drawable.gump,
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
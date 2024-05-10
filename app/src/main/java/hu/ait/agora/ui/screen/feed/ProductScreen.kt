package hu.ait.agora.ui.screen.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.ait.agora.data.Product
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular
import hu.ait.agora.ui.utils.AgoraBottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavController,
    feedViewModel: FeedViewModel = viewModel ()
) {

    val product = feedViewModel.getProductToShow().product

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
                            .clickable { navController.popBackStack() })
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
        ProductScreenContent(
            paddingValues = paddingValues,
            product = product
        )
    }
}

@Composable
fun ProductScreenContent(
    paddingValues: PaddingValues,
    product: Product
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
    ) {
        Divider( color = agoraLightGrey, thickness = 1.dp)

        AsyncImage(
            model = product.image,
            contentDescription = product.description,
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
        Spacer( modifier = Modifier.height(20.dp) )

        Column( modifier = Modifier.padding(start = 10.dp) ) {

            Text(
                text = product.owner.name,
                fontFamily = interFamilyRegular,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = product.title,
                fontFamily = interFamilyBold,
                fontSize = 19.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$" + product.price,
                fontFamily = interFamilyBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = product.description,
                fontFamily = interFamilyRegular,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    /* handle email */
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(start = 40.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(containerColor = agoraPurple, contentColor = agoraWhite)
            ) {
                Text(text = "ACQUIRE",  fontSize = 18.sp)
            }

        }
    }
}
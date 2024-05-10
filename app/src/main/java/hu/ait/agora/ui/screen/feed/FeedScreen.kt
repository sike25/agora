package hu.ait.agora.ui.screen.feed


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.ait.agora.R
import hu.ait.agora.data.Product
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular
import hu.ait.agora.ui.utils.AgoraBottomNavBar
import hu.ait.agora.ui.utils.AgoraSearchBar

@Composable
fun FeedScreen(
    navController: NavController,
    feedViewModel: FeedViewModel = viewModel(),
) {
    val feedListState = feedViewModel.productFlow.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            FeedTopAppBar()
        },
        bottomBar = {
            AgoraBottomNavBar(
                navController = navController,
            )
        }
    ) { paddingValues ->

        FeedContent(
            paddingValues = paddingValues,
            feedList = feedListState.value
        )
    }
}

@Composable
fun FeedContent(
    paddingValues: PaddingValues,
    feedList: List<Product>
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Divider(color = agoraLightGrey, thickness = 1.dp)
        LazyColumn(
            modifier = Modifier,
        ) {
            items(feedList){
                ProductCard(
                    product = it,
                )
            }
        }

    }
}

@Composable
fun ProductCard(
    product: Product,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = agoraWhite,
        ),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
    ) {

        Column {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model =  R.drawable.gump,
                    contentDescription = product.description,
                    modifier = Modifier.size(50.dp).clip(CircleShape),
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = product.owner.name,
                    fontFamily = interFamilyRegular,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$" + product.price.toString(),
                    fontFamily = interFamilyBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }

            AsyncImage(
                model = product.image,
                contentDescription = product.description,
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = product.description,
                fontFamily = interFamilyRegular,
                fontSize = 16.sp,
                modifier = Modifier.padding(25.dp)
            )
            Divider(color = agoraLightGrey, thickness = 1.dp)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTopAppBar(

) {
    var query by remember { mutableStateOf("") }
    TopAppBar(
        title = { },
        actions = {
            AgoraSearchBar(
                value = query,
                onValueChange = {query = it},
                placeHolder = "Search"
            )
        },
        modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 8.dp, bottom = 10.dp)
    )
}


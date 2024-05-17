package hu.ait.agora.ui.screen.buy_product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.ait.agora.data.ProductWithId
import hu.ait.agora.ui.navigation.Screen
import hu.ait.agora.ui.screen.list_product.ListProductUiState
import hu.ait.agora.ui.theme.agoraBlack
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.utils.AgoraBottomNavBar
import hu.ait.agora.ui.utils.AgoraSearchBar

@Composable
fun SearchResultsScreen(
    navController: NavController,
    feedViewModel: FeedViewModel,
) {
    Text(text = "search results")
    val searchResultsList: List<ProductWithId> = feedViewModel.getSearchResultsList()

    Scaffold(
        topBar = {
            SearchResultsTopAppBar(feedViewModel = feedViewModel)
        },
        bottomBar = {
            AgoraBottomNavBar(
                navController = navController,
            )
        }
    ) { paddingValues ->

        SearchResultsContent(
            paddingValues = paddingValues,
            navController = navController,
            feedViewModel = feedViewModel,
            searchResults = searchResultsList
        )
    }
}

@Composable
fun SearchResultsContent(
    paddingValues: PaddingValues,
    navController: NavController,
    feedViewModel: FeedViewModel,
    searchResults: List<ProductWithId>
) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        var searchResultsCount = searchResults.size
        Divider(color = agoraLightGrey, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$searchResultsCount results",
            fontFamily = interFamilyBold,
            fontSize = 16.sp,
            color = agoraBlack,
            modifier = Modifier.padding(start = 40.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(searchResults.size) { searchResult ->
                    SearchResultImage(
                        feedViewModel = feedViewModel,
                        searchResult = searchResults[searchResult],
                        navController = navController,
                    )
                }
            },
            modifier = Modifier.padding(start = 40.dp, end = 40.dp),
            userScrollEnabled = true,
        )

    }

}

@Composable
fun SearchResultImage (
    feedViewModel: FeedViewModel,
    searchResult: ProductWithId,
    navController: NavController,
) {
    AsyncImage(
        model = searchResult.product.image,
        contentDescription = searchResult.product.description,
        modifier = Modifier
            .height(100.dp).width(100.dp)
            .clickable {
                feedViewModel.setProductToShow(searchResult)
                navController.navigate(Screen.Product.route)
            },
        contentScale = ContentScale.Crop,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsTopAppBar (
    feedViewModel: FeedViewModel
) {
    TopAppBar(
        title = { },
        actions = {
            AgoraSearchBar(
                value = feedViewModel.query,
                onValueChange = {},
                placeHolder = feedViewModel.query,
                onDone = {}
            )
        },
        modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 8.dp, bottom = 10.dp)
    )
}



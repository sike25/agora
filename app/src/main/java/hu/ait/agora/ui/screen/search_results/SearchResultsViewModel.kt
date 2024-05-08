package hu.ait.agora.ui.screen.search_results

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import hu.ait.agora.R
import hu.ait.agora.data.Product

class SearchResultsViewModel : ViewModel() {
    private var searchQuery = ""


    private val searchResultsList = listOf(""
//            Product (
//                image = R.drawable.ramen,
//                title = "Ramen",
//                description = "Yummy yummy and artisanal",
//                price = 0.0
//            ),
//            Product (
//                image = R.drawable.lamp,
//                title = "Lava Lamp",
//                description = "My grandmother;s lamp needs a new home",
//                price = 0.0
//            ),
//            Product (
//                image = R.drawable.bicycle,
//                title = "Bicycle",
//                description = "Who wants my bicycle when I graduate?",
//                price = 0.0
//            ),

    )


    fun setSearchQuery( query : String) {
        searchQuery = query
    }

}
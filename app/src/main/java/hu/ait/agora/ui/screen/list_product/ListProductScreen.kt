package hu.ait.agora.ui.screen.list_product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular
import hu.ait.agora.ui.utils.EnterProductDetail
import hu.ait.agora.ui.utils.Spinner
import hu.ait.agora.ui.utils.TagChip



data class NavItemState(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val hasBadge : Boolean,
    val badgeNum : Int

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductScreen(
    onNavigateToFeed: () -> Unit = {},
    onPublish: () -> Unit = {},
    onUploadImage: () -> Unit = {},
) {

    var bottomNavState by rememberSaveable {
        mutableIntStateOf(0)
    }
    val navItems = listOf (
        NavItemState (
            title = "Feed",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasBadge = false,
            badgeNum = 0,
        ),
        NavItemState (
            title = "Inbox",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasBadge = true,
            badgeNum = 0,
        ),
        NavItemState (
            title = "Sexy",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasBadge = true,
            badgeNum = 0,
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "List Item",
                        fontFamily = interFamilyRegular,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 110.dp)
                    )
                },
                actions = {
                    TextButton(onClick = { onPublish() }) {
                        Text(
                            text = "Publish",
                            fontFamily = interFamilyBold,
                            fontSize = 18.sp,
                            color = agoraPurple,
                            modifier = Modifier.padding(10.dp, top = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    Icon(imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back to login",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onNavigateToFeed() })
                },
                modifier = Modifier.padding(10.dp)
            )
        },




        bottomBar = {
            NavigationBar {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavState == index,
                        onClick = { bottomNavState = index },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.hasBadge) Badge {}
                                    if (item.badgeNum != 0) Badge {
                                        Text( text = item.badgeNum.toString())
                                    }
                                }
                            ) {
                                Icon (
                                    imageVector =  if (bottomNavState == index) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title,
                                )
                            }
                        },
                        label = {}
                    )
                }
            }
        }

    ) { paddingValues ->
        ListProductContent(onUploadImage = onUploadImage, paddingValues = paddingValues)
    }

}

























@Composable
fun ListProductContent(
    onUploadImage: () -> Unit,
    paddingValues: PaddingValues,
) {
    val categoryList = mutableListOf("Food", "Fashion", "Tech")

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var presentTag by remember { mutableStateOf("") }

    // make remember, create product object
    val tags = mutableListOf("green", "denim", "comprehension", "green", "denim", "green", "denim", "comprehension")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
    ) {
        Divider(color = agoraLightGrey, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        // upload image
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(25.dp))
            Icon(imageVector = Icons.Filled.Add,
                contentDescription = "upload image",
                modifier = Modifier
                    .size(70.dp)
                    .padding(10.dp)
                    .background(shape = RoundedCornerShape(10.dp), color = agoraLightGrey)
                    .clickable { onUploadImage() })
            Text(text = "Upload Image of Item", fontFamily = interFamilyBold, fontSize = 18.sp)
            // replace text with thumbnail of uploaded image
        }
        Spacer(modifier = Modifier.height(30.dp))

        // title, description, price
        EnterProductDetail(
            initialValue = title,
            label = "Write a title",
            onValueChange = { title = it },
            imeAction = ImeAction.Next
        )
        EnterProductDetail(
            initialValue = description,
            label = "Write a description",
            onValueChange = { description = it },
            imeAction = ImeAction.Next
        )
        EnterProductDetail(
            initialValue = price,
            label = "Price (in dollars)",
            onValueChange = { price = it },
            imeAction = ImeAction.Done
        )

        // category
        Text(
            text = "Category",
            fontFamily = interFamilyBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Spinner(
            list = categoryList,
            preselected = categoryList[0],
            onSelectionChanged = { category = it },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(start = 50.dp, end = 50.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))

        // tags
        Text(
            text = "Tags",
            fontFamily = interFamilyBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = presentTag,
            onValueChange = { presentTag = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    tags.add(presentTag)
                    presentTag = ""
                }
            ),
            modifier = Modifier
                .background(agoraWhite)
                .fillMaxWidth(0.95f)
                .padding(start = 50.dp, end = 50.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Adaptive(30.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalItemSpacing = 8.dp,
            content = {
                items(tags.size) { tag ->
                    TagChip(tag = tags[tag], onRemoveItem = { tags.removeAt(tag) })
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
                .height(200.dp),
            userScrollEnabled = true,
        )
    }
}

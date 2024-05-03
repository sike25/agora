package hu.ait.agora.ui.screen.list_product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular
import java.util.Locale.Category


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductScreen(
    onNavigateToFeed: () -> Unit = {},
    onPublish: () -> Unit = {},
    onUploadImage: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "List Item",
                        fontFamily = interFamilyRegular,
                        fontSize = 25.sp,
                        modifier =  Modifier
                            .padding(start = 110.dp)
                    )

                },
                actions = {
                    TextButton(onClick = { /* Handle publish */ }) {
                        Text(
                            text = "Publish",
                            fontFamily = interFamilyBold,
                            fontSize = 18.sp,
                            color = agoraPurple,
                            modifier =  Modifier.padding(10.dp, top = 4.dp)
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back to login",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onNavigateToFeed() }
                    )
                },
                modifier = Modifier.padding(10.dp)
            )
        }
    ) {  paddingValues ->

        ListProductContent(
            onUploadImage = onUploadImage,
            paddingValues = paddingValues
        )

    }
}


@Composable
fun ListProductContent(
    onUploadImage: () -> Unit,
    paddingValues: PaddingValues,
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Fashion") }  // Default category
    var showCategoryMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {

        Divider(color = agoraLightGrey, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        // upload image
        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(25.dp))
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "upload image",
                modifier = Modifier
                    .size(70.dp)
                    .padding(10.dp)
                    .background(shape = RoundedCornerShape(10.dp), color = agoraLightGrey)
                    .clickable { onUploadImage() }
            )

            Text(
                text = "Upload Image of Item",
                fontFamily = interFamilyBold,
                fontSize = 18.sp,
            )

            // replace text thumbnail of uploaded image
        }

        Spacer(modifier = Modifier.height(30.dp))

        // title
        Text(
            text = "Write a title",
            fontFamily = interFamilyBold,
            fontSize = 18.sp,
            modifier =  Modifier
                .padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = title,
            onValueChange = { title = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.background(agoraWhite).fillMaxWidth(0.95f).padding(start = 50.dp, end = 50.dp),
        )
        Spacer(modifier = Modifier.height(25.dp))

        // description
        Text(
            text = "Write a description",
            fontFamily = interFamilyBold,
            fontSize = 18.sp,
            modifier =  Modifier
                .padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            modifier = Modifier.background(agoraWhite).fillMaxWidth(0.95f).padding(start = 50.dp, end = 50.dp),
        )
        Spacer(modifier = Modifier.height(25.dp))


        // price
        Text(
            text = "Price (in dollars)",
            fontFamily = interFamilyBold,
            fontSize = 18.sp,
            modifier =  Modifier
                .padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = price,
            onValueChange = { price = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier.background(agoraWhite).fillMaxWidth(0.95f).padding(start = 50.dp, end = 50.dp),
            keyboardActions = KeyboardActions(onDone = {})
        )
        Spacer(modifier = Modifier.height(25.dp))

        // category
        Text(
            text = "Category",
            fontFamily = interFamilyBold,
            fontSize = 18.sp,
            modifier =  Modifier
                .padding(start = 50.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        IconButton(onClick = { showCategoryMenu = !showCategoryMenu }) {
            Icon(Icons.Filled.MoreVert, "show category menu")
        }
        CategoryDropDownMenu(showMenu = showCategoryMenu, onDismissRequest = { showCategoryMenu = false})
    }

}

@Composable
fun CategoryDropDownMenu(
    showMenu: Boolean = false,
    onDismissRequest: () -> Unit = {},
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismissRequest
    ) {

        DropdownMenuItem(
            onClick = { onDismissRequest() },
            text = {Text(text = "Fashion")}
        )

        DropdownMenuItem(
            onClick = { onDismissRequest() },
            text = {Text(text = "Food")}
        )


    }
}

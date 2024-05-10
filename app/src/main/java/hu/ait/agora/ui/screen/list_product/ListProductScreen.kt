package hu.ait.agora.ui.screen.list_product

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import hu.ait.agora.R
import hu.ait.agora.data.Category
import hu.ait.agora.ui.navigation.Screen
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular
import hu.ait.agora.ui.utils.AgoraBottomNavBar
import hu.ait.agora.ui.utils.EnterProductDetail
import hu.ait.agora.ui.utils.EnterProductPrice
import hu.ait.agora.ui.utils.Spinner
import hu.ait.agora.ui.utils.TagChip


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ListProductScreen(
    navController: NavController,
    listScreenViewModel: ListScreenViewModel = viewModel()
) {

    Scaffold(
        topBar = { ListProductTopAppBar( listScreenViewModel = listScreenViewModel, navController = navController) },
        bottomBar = { AgoraBottomNavBar( navController = navController ) }
    ) { paddingValues ->
        ListProductContent(
            paddingValues = paddingValues,
            listScreenViewModel = listScreenViewModel,
            navController = navController
        )
    }
}




@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductTopAppBar(
    listScreenViewModel: ListScreenViewModel,
    navController: NavController
) {

    val context = LocalContext.current
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
            TextButton(
                onClick = {
                    if (listScreenViewModel.allInputsValid()) {
                        // uploadProductImage calls general uploadProduct upon completion
                        listScreenViewModel.uploadProductImage (
                            contentResolver = context.contentResolver
                        )
                    } else {
                        listScreenViewModel.listProductUiState = ListProductUiState.ErrorDuringProductUpload("Some input fields have not been filled.")
                    }

                }
            ) {
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
                    .clickable { navController.popBackStack() })
        },
        modifier = Modifier.padding(10.dp)
    )
}




@Composable
fun ListProductContent(
    paddingValues: PaddingValues,
    listScreenViewModel: ListScreenViewModel,
    navController: NavController

) {
    val scrollState = rememberScrollState()
    var presentTag by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
    ) {
        Divider(color = agoraLightGrey, thickness = 1.dp)
        Spacer(modifier = Modifier.height(20.dp))

        ErrorManagementElement(listScreenViewModel = listScreenViewModel, navController = navController)

        // upload image
        UploadImageRow (
            onImageSelected = { uri -> listScreenViewModel.imageUri = uri }
        )

        // title, description, price
        EnterProductDetail(
            initialValue = listScreenViewModel.title,
            label = "Write a title",
            onValueChange = { listScreenViewModel.title = it },
            imeAction = ImeAction.Next,
        )
        EnterProductDetail(
            initialValue = listScreenViewModel.description,
            label = "Write a description",
            onValueChange = { listScreenViewModel.description = it },
            imeAction = ImeAction.Next
        )
        EnterProductPrice(
            initialValue = listScreenViewModel.price,
            label = "Price (in dollars)",
            onValueChange = { listScreenViewModel.price = it },
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
            list = Category.getCategoryList(),
            preselected = Category.getCategoryList()[0],
            onSelectionChanged = { listScreenViewModel.category = it },
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
                    listScreenViewModel.tags.add(presentTag)
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
                items(listScreenViewModel.tags.size) { tag ->
                    TagChip(tag = listScreenViewModel.tags[tag], onRemoveItem = { listScreenViewModel.tags.removeAt(tag) })
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



@Composable
fun ErrorManagementElement(
    listScreenViewModel: ListScreenViewModel,
    navController: NavController
) {
    when (listScreenViewModel.listProductUiState) {
        is ListProductUiState.Init -> {}
        is ListProductUiState.InputError -> {
            Text(
                text = "Fill in all the input fields correctly!",
                fontFamily = interFamilyBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 50.dp)
            )
        }

        is ListProductUiState.LoadingImageUpload -> {}
        is ListProductUiState.ImageUploadSuccess -> {}
        is ListProductUiState.ErrorDuringImageUpload -> {
            Text(
                text = "Error: ${(listScreenViewModel.listProductUiState as ListProductUiState.ErrorDuringImageUpload).error}",
                fontFamily = interFamilyBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 50.dp)
            )
        }

        is ListProductUiState.LoadingProductUpload -> { CircularProgressIndicator() }
        is ListProductUiState.ProductUploadSuccess -> { navController.navigate(Screen.Feed.route)}
        is ListProductUiState.ErrorDuringProductUpload -> {
            Text(
                text = "Error: ${(listScreenViewModel.listProductUiState as ListProductUiState.ErrorDuringProductUpload).error}",
                fontFamily = interFamilyBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 50.dp)
            )
        }
    }

}


@Composable
fun UploadImageRow(
    onImageSelected: (Uri) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { result: Uri? ->
        selectedImageUri = result
    }
    var uploaded by remember { mutableStateOf(false) }

    Row(
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
                .clickable {
                    getContent.launch("image/*")
                }
        )

        if (!uploaded) {
            Text(text = "Upload Image of Item", fontFamily = interFamilyBold, fontSize = 18.sp)
        }

        selectedImageUri?.let { uri ->
            uploaded = true
            AsyncImage(
                model = uri,
                contentDescription = "Selected image",
                modifier = Modifier.height(70.dp),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.error)
            )
            onImageSelected(uri)
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
}

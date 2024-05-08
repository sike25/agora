package hu.ait.agora.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import hu.ait.agora.data.navItems
import hu.ait.agora.ui.theme.agoraBlack
import hu.ait.agora.ui.theme.agoraLightGrey
import hu.ait.agora.ui.theme.agoraPurple
import hu.ait.agora.ui.theme.agoraWhite
import hu.ait.agora.ui.theme.interFamilyBold
import hu.ait.agora.ui.theme.interFamilyRegular



@Composable
fun EnterProductDetail(
    initialValue: String,
    label: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction
) {
    Text(
        text = label,
        fontFamily = interFamilyBold,
        fontSize = 18.sp,
        modifier =  Modifier.padding(start = 50.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextField(
        value = initialValue,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        modifier = Modifier.background(agoraWhite).fillMaxWidth(0.95f).padding(start = 50.dp, end = 50.dp),
    )
    Spacer(modifier = Modifier.height(25.dp))
}



@Composable
fun Spinner(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (myData: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value

    Card(
        modifier = modifier.clickable { expanded = !expanded }.height(50.dp)
    ) {
        Row {
            Text(
                text = selected,
                modifier = Modifier.weight(0.2f).padding(horizontal = 16.dp, vertical = 12.dp),
                fontFamily = interFamilyRegular,
                color = agoraBlack,
                fontSize = 16.sp
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp, vertical = 12.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.72f)
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry,
                                modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                            )
                        },
                    )
                }
            }
        }
    }
}



@Composable
fun TagChip(
    tag: String,
    onRemoveItem: () -> Unit,
) {
    Card (
        shape = RoundedCornerShape(10.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "remove tag",
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 4.dp)
                    .clickable { onRemoveItem() }
            )
            Text(
                text = tag,
                fontFamily = interFamilyRegular,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}



@Composable
fun AgoraBottomNavBar(
    navController: NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.height(100.dp),
    ) {
        navItems.forEachIndexed { _, item ->
            val selected = currentRoute == item.screen
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon (
                        imageVector =  item.icon,
                        contentDescription = item.screen,
                        modifier = Modifier.size(30.dp),
                        tint = if (currentRoute == item.screen) agoraPurple else agoraBlack
                    )
                },
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgoraSearchBar(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeHolder: String = "",

    ) {

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = agoraWhite)
            .background(agoraLightGrey, shape = RoundedCornerShape(9.dp))
            .height(40.dp),

        textStyle = TextStyle(fontSize = 16.sp)
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 2.dp, bottom = 2.dp
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "search icon"
                )
            },
            placeholder = {
                Text(
                    text = placeHolder,
                    fontSize = 16.sp,
                    fontFamily = interFamilyRegular,
                    color = agoraBlack,
                    modifier = Modifier
                )
            },
        )
    }

}

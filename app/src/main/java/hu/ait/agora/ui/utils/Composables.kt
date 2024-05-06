package hu.ait.agora.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.ait.agora.ui.theme.agoraBlack
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

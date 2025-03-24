package com.example.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onSearchTextChanged: (TextFieldValue) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 8.dp).padding(top = 8.dp).padding(bottom = 8.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material.TextField(
                value = searchText,
                onValueChange = onSearchTextChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                placeholder = { Text("Search...") }
            )

            IconButton(onClick = {  }) {
                Icon(Icons.Default.Send, contentDescription = "Close")
            }
        }
    }
}
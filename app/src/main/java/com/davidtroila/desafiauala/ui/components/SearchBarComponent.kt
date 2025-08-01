package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarComponent(
    query: String,
    onQueryChanged: (String) -> Unit,
    onShowFavClicked: () -> Unit,
    isShowingFav: Boolean
) {
    val favIcon = if (isShowingFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                onQueryChanged(it)
            },
            label = { Text("Filter") },
            modifier = Modifier.
                padding(start = 16.dp)
                .weight(1F),
            singleLine = true,
            shape = RoundedCornerShape(100.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )
        IconButton(
            onClick = {
                onShowFavClicked()
            }
        ) {
            Icon(
                imageVector = favIcon,
                tint = Color.Red,
                contentDescription = "Favorite Icon"
            )
        }
    }
}
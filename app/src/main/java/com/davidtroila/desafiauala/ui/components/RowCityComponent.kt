package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davidtroila.desafiauala.model.CityDTO

@Composable
fun RowCityComponent(
    city: CityDTO,
    backgroundColor: Color,
    onCitySelected: (CityDTO) -> Unit,
    onFavouriteClicked: (Int) -> Unit) {
    Row (modifier = Modifier
        .background(backgroundColor)
        .fillMaxWidth()
        .clickable { onCitySelected(city) }
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.weight(1F)) {
            Text("${city.name}, ${city.country}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Lat: ${city.lat} - Long: ${city.lon}", fontWeight = FontWeight.Light, fontSize = 13.sp)
        }

        val favIcon = if (city.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

        IconButton(
            onClick = {
                onFavouriteClicked(city.id)
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
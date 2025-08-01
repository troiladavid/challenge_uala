package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.davidtroila.desafiauala.model.CityDTO

@Composable
fun CityListComponent(
    cities: List<CityDTO>,
    error: String?,
    onCitySelected: (CityDTO) -> Unit,
    paddingValues: PaddingValues,
    onLoadMore: () -> Unit,
    onFavoriteClicked: (Int) -> Unit,
) {
    if (error != null) {
        Text("Error: $error")
    } else {
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(cities, key = { _, city -> city.id }) { index, item ->
                val isSelected = item.selected
                val backgroundColor =  if (isSelected) { Color.Cyan } else if (index % 2 == 1) {
                    Color.LightGray
                } else {
                    Color.Transparent
                }
                RowCityComponent(item, backgroundColor, onCitySelected, onFavoriteClicked )

                if (index == cities.lastIndex - 8) {
                    onLoadMore( )
                }
            }
        }
    }
}
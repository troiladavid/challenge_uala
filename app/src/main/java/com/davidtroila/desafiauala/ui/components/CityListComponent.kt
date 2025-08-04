package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.ui.screens.isLandscape

@Composable
fun CityListComponent(
    cities: List<CityDTO>,
    onCitySelected: (CityDTO) -> Unit,
    paddingValues: PaddingValues,
    onLoadMore: () -> Unit,
    onFavoriteClicked: (Int) -> Unit,
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        itemsIndexed(cities, key = { _, city -> city.id }) { index, item ->
            val isSelected = item.selected
            val backgroundColor = if (isSelected && isLandscape()) {
                Color.Cyan
            } else if (index % 2 == 1) {
                Color.LightGray
            } else {
                Color.Transparent
            }
            RowCityComponent(item, backgroundColor, onCitySelected, onFavoriteClicked)

            if (index == cities.lastIndex - 8) {
                onLoadMore()
            }
        }
    }
}
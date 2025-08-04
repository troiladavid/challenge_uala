package com.davidtroila.desafiauala.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.ui.components.CityListComponent
import com.davidtroila.desafiauala.ui.components.MapComponent
import com.davidtroila.desafiauala.ui.components.SearchBarComponent

@Composable
fun LandscapeListMapScreen(
    cities: List<CityDTO>,
    query: String,
    selectedCity: CityDTO?,
    onQueryChanged: (String) -> Unit,
    onCitySelected: (CityDTO) -> Unit,
    onFavoriteClicked: (Int) -> Unit,
    onLoadMore: () -> Unit,
    showOnlyFav: Boolean,
    onShowFavClicked: () -> Unit,
    paddingValues: PaddingValues
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1F)) {
            SearchBarComponent(query, onQueryChanged , onShowFavClicked, showOnlyFav )
            Spacer(modifier = Modifier.height(4.dp))
            CityListComponent( cities = cities, onCitySelected = { onCitySelected(it)}, paddingValues, onLoadMore, onFavoriteClicked)
        }
        selectedCity?.let {
            Row(
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp)
            ) {
                MapComponent(
                    modifier = Modifier.padding(paddingValues),
                    latitude = selectedCity.lat,
                    longitude = selectedCity.lon
                )
            }
        }
    }
}
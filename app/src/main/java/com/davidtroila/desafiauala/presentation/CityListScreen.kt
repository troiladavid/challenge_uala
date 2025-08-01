package com.davidtroila.desafiauala.presentation

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidtroila.desafiauala.data.CityEntity
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.ui.components.SearchBarComponent

@Composable
fun CityListScreen(
    navigateToMap: (CityDTO) -> Unit,
    viewModel: CityViewModel = hiltViewModel()
) {
    val cities by viewModel.cities.collectAsState()
    val error by viewModel.error.collectAsState()
    val query by viewModel.query.collectAsState()
    val selectedCity by viewModel.selectedcity.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.init()
    }

    if (isLandscape()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            LandscapeListMapScreen(
                cities = cities,
                error = error,
                query = query,
                selectedCity = selectedCity,
                onCitySelected = { city -> viewModel.onCitySelected(city) },
                onQueryChanged = {query -> viewModel.onQueryChanged(query)},
                paddingValues = it
            )
        }
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(it)
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                SearchBarComponent(query) { q -> viewModel.onQueryChanged(q) }
                Spacer(modifier = Modifier.size(8.dp))
                CityList(cities, error, navigateToMap, selectedCity, it)
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun CityList(
    cities: List<CityDTO>,
    error: String?,
    onCitySelected: (CityDTO) -> Unit,
    selectedCity: CityDTO?,
    paddingValues: PaddingValues
) {
    if (error != null) {
        Text("Error: $error")
    } else {
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(cities) { index, item ->
                val isSelected = selectedCity?.id == item.id
                val backgroundColor =  if (isSelected) { Color.Cyan } else if (index % 2 == 1) {
                    Color.LightGray
                } else {
                    Color.Transparent
                }
                Text("${item.name}, ${item.country}", modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCitySelected(item) }
                    .background(backgroundColor)
                    .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
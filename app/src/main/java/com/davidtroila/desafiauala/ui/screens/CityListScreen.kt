package com.davidtroila.desafiauala.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.ui.components.CityListComponent
import com.davidtroila.desafiauala.ui.components.ErrorComponent
import com.davidtroila.desafiauala.ui.components.SearchBarComponent
import com.davidtroila.desafiauala.viewmodel.CityViewModel

@Composable
fun CityListScreen(
    navigateToMap: (CityDTO) -> Unit,
    viewModel: CityViewModel = hiltViewModel()
) {
    val cities by viewModel.cities.collectAsState()
    val error by viewModel.error.collectAsState()
    val query by viewModel.query.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val showOnlyFav by viewModel.showOnlyFav.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadCities()
    }
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Cargando ciudades...", modifier = Modifier.padding(24.dp))
        }
    } else {
        if (!error) {
            if (isLandscape()) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    LandscapeListMapScreen(
                        cities = cities,
                        query = query,
                        onCitySelected = viewModel::onCitySelected,
                        onQueryChanged = viewModel::onQueryChanged,
                        onLoadMore = viewModel::loadNextPage,
                        onFavoriteClicked = { id -> viewModel.setFavorite(id) },
                        selectedCity = selectedCity,
                        showOnlyFav = showOnlyFav,
                        onShowFavClicked = viewModel::onFavFilterClicked,
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
                        SearchBarComponent(
                            query,
                            viewModel::onQueryChanged,
                            viewModel::onFavFilterClicked,
                            showOnlyFav
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        HorizontalDivider(Modifier.background(Color.LightGray))
                        CityListComponent(
                            cities,
                            navigateToMap,
                            it,
                            viewModel::loadNextPage,
                            viewModel::setFavorite
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
        } else {
            ErrorComponent {
                viewModel.loadCities()
            }
        }
    }
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
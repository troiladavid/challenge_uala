package com.davidtroila.desafiauala.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.model.CountryDTO
import com.davidtroila.desafiauala.ui.components.ErrorComponent
import com.davidtroila.desafiauala.ui.components.MapComponent
import com.davidtroila.desafiauala.viewmodel.MapViewModel
import com.davidtroila.desafiouala.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(cityId: Int, onBackPressed: () -> Unit, viewModel: MapViewModel = hiltViewModel()) {
    val isLoading by viewModel.isLoading.collectAsState()
    val city by viewModel.city.collectAsState()
    val country by viewModel.countryInfo.collectAsState()
    val error by viewModel.error.collectAsState()

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (!error) {
            Scaffold(
                topBar = {
                    Column {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Detalle de ciudad",
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = onBackPressed) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use AutoMirrored for RTL support
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                        HorizontalDivider(modifier = Modifier.background(Color.LightGray))
                    }
                }) {
                if (isLandscape()) {
                    LandscapeMapScreenContent(city = city!!, country = country!!, paddingValues = it)
                } else {
                    PortraitMapScreenContent(city = city!!, country = country!!, paddingValues = it)
                }
            }
        } else {
            ErrorComponent {
                viewModel.getInfo(cityId)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getInfo(cityId)
    }
}

@Composable
fun PortraitMapScreenContent(city: CityDTO, country: CountryDTO, paddingValues: PaddingValues){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        MapComponent(
            modifier = Modifier
                .weight(1.7f)
                .padding(paddingValues), city.lat, city.lon
        )
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(modifier = Modifier.background(Color.LightGray))
        Spacer(modifier = Modifier.height(20.dp))
        CityInfoComponent(modifier = Modifier.weight(1f), city, country)
    }
}

@Composable
fun LandscapeMapScreenContent(city: CityDTO, country: CountryDTO, paddingValues: PaddingValues){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        MapComponent(
            modifier = Modifier
                .weight(1.7f)
                .padding(paddingValues), city.lat, city.lon
        )
        Spacer(modifier = Modifier.width(24.dp))
        CityInfoComponent(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(paddingValues), city, country)
    }
}

@Composable
fun CityInfoComponent(modifier: Modifier, cityDTO: CityDTO, country: CountryDTO) {
        Column(modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(id = R.string.city_info_city),
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = cityDTO.name,
                modifier = Modifier.padding(top = 2.dp),
                color = Color.Gray
            )
            Text(
                text = stringResource(id = R.string.city_info_country),
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(text = country.commonName, color = Color.Gray)
            Text(
                text = stringResource(id = R.string.city_info_coordinates),
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text =  stringResource(id = R.string.city_info_lat_s_lon_s, cityDTO.lat, cityDTO.lon),
                modifier = Modifier.padding(top = 2.dp),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(0.dp))
        }

}

package com.davidtroila.desafiauala.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.davidtroila.desafiauala.navigation.screens.CityList
import com.davidtroila.desafiauala.navigation.screens.MapView
import com.davidtroila.desafiauala.presentation.CityListScreen
import com.davidtroila.desafiauala.presentation.MapScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CityList) {
        composable<CityList> (exitTransition = {slideOutHorizontally()}) {
            CityListScreen({ city -> navController.navigate(MapView(city.name, city.lat, city.lon))})
        }
        composable<MapView> (enterTransition = { slideInHorizontally() }, exitTransition = {slideOutHorizontally()}){
            val mapView = it.toRoute<MapView>()
            MapScreen(mapView.name, mapView.latitude, mapView.longitude)
        }
    }
}
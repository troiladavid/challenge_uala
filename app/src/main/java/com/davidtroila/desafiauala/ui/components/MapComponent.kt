package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapComponent(paddingValues: PaddingValues, latitude: Double?, longitude: Double?)  {
    if (latitude != null && longitude != null) {
        val location = LatLng(latitude, longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 10f)
        }

        LaunchedEffect(location) {
            location.let {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(it, 10f),
                    durationMs = 1000
                )
            }
        }

        GoogleMap(
            modifier = Modifier.padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
            )
        }
    }

}
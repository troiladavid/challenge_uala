package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapComponent(modifier: Modifier, latitude: Double? = 0.0, longitude: Double? = 0.0)  {
    if (latitude != null && longitude != null) {
        val location = LatLng(latitude, longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 10f)
        }

        GoogleMap(
            modifier = modifier.clip(
                RoundedCornerShape(50.dp)).testTag("google_map"),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                )) {
            Marker(
                state = MarkerState(position = LatLng(latitude, longitude)),
            )
        }

        LaunchedEffect(location) {
            location.let {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(it, 10f),
                    durationMs = 500
                )
            }
        }
    }

}
package com.davidtroila.desafiauala.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.davidtroila.desafiauala.ui.components.MapComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(name:String, lat: Double, long: Double) {
    Scaffold ( topBar = {
        TopAppBar(
            title = { Text(text = name) },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation, e.g., navController.navigateUp() */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use AutoMirrored for RTL support
                        contentDescription = "Back"
                    )
                }
            }
            )
    }) {
        MapComponent(paddingValues = it, lat, long)
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapTopBar(paddingValues: PaddingValues) {
    TopAppBar(title = {  }, navigationIcon = )
}*/

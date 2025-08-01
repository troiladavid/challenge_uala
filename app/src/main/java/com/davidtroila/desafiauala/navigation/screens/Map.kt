package com.davidtroila.desafiauala.navigation.screens

import kotlinx.serialization.Serializable

@Serializable
data class MapView(val name: String, val latitude: Double, val longitude: Double)
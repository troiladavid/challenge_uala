package com.davidtroila.desafiauala.model

data class CityDTO(
    var id: Int,
    var country: String,
    var name: String,
    var lon: Double,
    var lat: Double,
    var selected :Boolean = false
)
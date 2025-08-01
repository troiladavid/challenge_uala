package com.davidtroila.desafiauala.data

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("_id")
    val id: Double,
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("coord")
    val coordinates: Coordinate
) {
    data class Coordinate(
        @SerializedName("lon")
        val longitude: Double,
        @SerializedName("lat")
        val latitude: Double
    )


}
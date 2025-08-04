package com.davidtroila.desafiauala.data

import retrofit2.http.GET
import retrofit2.http.Path

interface CityApi {

    @GET("alpha/{code}")
    suspend fun getCountryByCode(@Path("code") code: String): List<CityResponse>
}
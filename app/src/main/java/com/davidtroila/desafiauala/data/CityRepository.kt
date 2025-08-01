package com.davidtroila.desafiauala.data

import android.content.Context
import com.davidtroila.desafiauala.model.CityDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CityRepository @Inject constructor(
    private val cityDao: CityDao,
    @ApplicationContext private val context: Context
) {
    suspend fun getCities(): List<CityDTO> {
        if (cityDao.getAll().isNotEmpty()) return getAllCities()

        val json = context.assets.open("cities.json").bufferedReader().use { it.readText() }

        val cities: List<CityEntity> = Gson().fromJson(json, object : TypeToken<List<CityEntity>>() {}.type)
        cityDao.insertAll(cities)
        return getAllCities()
    }

    suspend fun getAllCities(): List<CityDTO> = cityDao.getAll().map { it.toDTO() }

    suspend fun filterCities(query: String) = cityDao.searchCities(query).map { it.toDTO() }
}
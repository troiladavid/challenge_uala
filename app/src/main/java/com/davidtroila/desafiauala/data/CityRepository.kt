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
    suspend fun getCities(limit: Int, offset: Int): List<CityDTO> {
        if (cityDao.getAll().isNotEmpty()) return getCitiesPaged(limit, offset, false)

        val json = context.assets.open("cities.json").bufferedReader().use { it.readText() }

        val cities: List<CityEntity> = Gson().fromJson(json, object : TypeToken<List<CityEntity>>() {}.type)
        cityDao.insertAll(cities)
        return getCitiesPaged(limit, offset, false)
    }

    suspend fun getCitiesPaged(limit: Int, offset: Int, includeFav: Boolean) = cityDao.getCitiesPaged(limit, offset, includeFav).map { it.toDTO() }

    suspend fun filterCities(query: String, includeFav: Boolean) = cityDao.searchCities(query, includeFav).map { it.toDTO() }

    suspend fun setFavorite(cityId: Int): CityDTO {
        val cityEntity = cityDao.getCity(cityId)
        cityEntity.favorite = !cityEntity.favorite
        cityDao.setFavorite(cityEntity)
        return cityEntity.toDTO()
    }
}
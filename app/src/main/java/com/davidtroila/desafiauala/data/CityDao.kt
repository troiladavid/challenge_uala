package com.davidtroila.desafiauala.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("SELECT * FROM city ORDER BY name ASC")
    suspend fun getAll(): List<CityEntity>

    @Query("SELECT * FROM city WHERE name GLOB :prefix || '*' ORDER BY name ASC")
    suspend fun searchCities(prefix: String): List<CityEntity>

}
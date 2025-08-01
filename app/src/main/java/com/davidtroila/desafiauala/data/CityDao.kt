package com.davidtroila.desafiauala.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("SELECT * FROM city ORDER BY name ASC")
    suspend fun getAll(): List<CityEntity>

    @Query("SELECT * FROM city WHERE _id =:id")
    suspend fun getCity(id: Int): CityEntity

    @Query("SELECT * FROM city WHERE name GLOB :prefix || '*' AND (:includeFav = 0 OR favorite = 1) ORDER BY name ASC")
    suspend fun searchCities(prefix: String, includeFav: Boolean): List<CityEntity>


    @Query("SELECT * FROM city WHERE (:includeFav = 0 OR favorite = 1)  ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getCitiesPaged(limit: Int, offset: Int, includeFav: Boolean): List<CityEntity>

    @Update
    suspend fun setFavorite(cityEntity: CityEntity): Int
}
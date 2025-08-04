package com.davidtroila.desafiauala.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.davidtroila.desafiauala.model.CityDTO

@Entity(tableName = "city", indices = [Index(value = ["name"])])
data class CityEntity (
    @PrimaryKey(autoGenerate = true)
    val localId: Int,
    val _id: Int,
    val country: String,
    @ColumnInfo(name = "favorite", defaultValue = "0")
    var favorite: Boolean = false,
    val name: String,
    @Embedded val coord: Coord
) {
    fun toDTO() = CityDTO(
        id = _id,
        name = name,
        country = country,
        lat = coord.lat,
        lon = coord.lon,
        isFavorite = favorite
    )
}

data class Coord(
    val lon: Double,
    val lat: Double
)
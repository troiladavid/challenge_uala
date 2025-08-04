package com.davidtroila.desafiauala.data

import com.davidtroila.desafiauala.model.CountryDTO
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("name")
    val name: Name,
    @SerializedName("cca2")
    val code: String,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("subregion")
    val subregion: String? = null,
    @SerializedName("translations")
    val translations: Map<String, TranslatedName>
) {
    data class TranslatedName(
        val official: String,
        val common: String
    )

    data class Name(
        @SerializedName("common")
        val common: String,

        @SerializedName("official")
        val official: String
    )

    fun toDTO() = CountryDTO(
        officialName = translations["spa"]?.official ?: name.official,
        commonName = translations["spa"]?.common ?: name.common,
        region = region,
        subregion = subregion
    )
}
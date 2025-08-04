package com.davidtroila.desafiauala.model

data class CountryDTO(
    val officialName: String,
    val commonName: String,
    val region: String? = null,
    val subregion: String? = null,
)

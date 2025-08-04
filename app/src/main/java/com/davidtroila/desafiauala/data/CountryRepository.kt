package com.davidtroila.desafiauala.data

import javax.inject.Inject

class CountryRepository @Inject constructor(val api: CityApi, val dao: CityDao) {

    suspend fun getCity(id: Int) = dao.getCity(id).toDTO()

    suspend fun getCountryInfo(countryCode: String) = api.getCountryByCode(countryCode).first().toDTO()
}
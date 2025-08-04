package com.davidtroila.desafiauala.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtroila.desafiauala.data.CountryRepository
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.model.CountryDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: CountryRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    private val _city = MutableStateFlow<CityDTO?>(null)
    val city: StateFlow<CityDTO?> = _city

    private val _countryInfo = MutableStateFlow<CountryDTO?>(null)
    val countryInfo: StateFlow<CountryDTO?> = _countryInfo

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun getInfo(cityId: Int) {
        try {
            viewModelScope.launch {
                _isLoading.value = true
                val city = repository.getCity(cityId)
                val country = repository.getCountryInfo(city.country)
                _city.value = city
                _countryInfo.value = country
                _isLoading.value = false
                _error.value = false
            }
        } catch (e: Exception) {
            _error.value = true
        }

    }
}
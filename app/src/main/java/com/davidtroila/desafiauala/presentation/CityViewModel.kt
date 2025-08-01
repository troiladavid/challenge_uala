package com.davidtroila.desafiauala.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.davidtroila.desafiauala.data.CityEntity
import com.davidtroila.desafiauala.data.CityRepository
import com.davidtroila.desafiauala.data.CityResponse
import com.davidtroila.desafiauala.model.CityDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val repository: CityRepository): ViewModel() {

    private val _cities = MutableStateFlow<List<CityDTO>>(emptyList())
    val cities: StateFlow<List<CityDTO>> = _cities

    private val _selectedcity = MutableStateFlow<CityDTO?>(null)
    val selectedcity: StateFlow<CityDTO?> = _selectedcity

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        observeSearch()
    }

    fun init() {
        try {
            viewModelScope.launch(Dispatchers.IO) { val pepe = repository.getCities()
                _cities.value = pepe
                _error.value = null
            }
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Unknown error"
        }
    }

    fun onCitySelected(city: CityDTO) {
        _selectedcity.value = city.copy(selected = true)
    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    private fun observeSearch() {
        viewModelScope.launch {
            _query
                .debounce(300) // wait 300ms after the last input
                .distinctUntilChanged()
                .collectLatest { query ->
                    try {
                        val result = if (query.isBlank()) {
                            repository.getAllCities()
                        } else {
                            repository.filterCities(query)
                        }
                        _cities.value = result
                        _error.value = null
                    } catch (e: Exception) {
                        _error.value = e.message
                    }
                }
        }
    }
}
package com.davidtroila.desafiauala.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtroila.desafiauala.data.CityRepository
import com.davidtroila.desafiauala.model.CityDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val repository: CityRepository): ViewModel() {

    private val _cities = MutableStateFlow<List<CityDTO>>(emptyList())
    val cities: StateFlow<List<CityDTO>> = _cities

    private val _selectedCity = MutableStateFlow<CityDTO?>(null)
    val selectedCity: StateFlow<CityDTO?> = _selectedCity

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _showOnlyFav = MutableStateFlow(false)
    val showOnlyFav: StateFlow<Boolean> = _showOnlyFav

    private var currentOffset = 0
    private val pageSize = 30
    private var isLoading = false
    private var allLoaded = false

    init {
        observeSearch()
    }

    fun init() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _cities.value = repository.getCities(pageSize, currentOffset)
            }
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Unknown error"
        }
    }

    fun onCitySelected(city: CityDTO) {
        _cities.update { currentList ->
            currentList.map {
                if (it.id == city.id) it.copy(selected = !city.selected)
                else it.copy(selected = false)
            }
        }
        _selectedCity.value = city
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
                    loadNextPage(query)
                }
        }
    }

    fun onFavFilterClicked() {
        _showOnlyFav.value = !_showOnlyFav.value
        allLoaded = false
        currentOffset = 0
        loadNextPage(_query.value)
    }

    fun loadNextPage(query: String = "") {
        if (isLoading || allLoaded) return

        viewModelScope.launch {
            isLoading = true
            var newItems = listOf<CityDTO>()
            if (query.isBlank()) {
                newItems = repository.getCitiesPaged(pageSize, currentOffset, _showOnlyFav.value)
                if (newItems.isEmpty()) {
                    allLoaded = true
                } else {
                    _cities.value = if (currentOffset > 0) _cities.value + newItems else newItems
                    currentOffset += pageSize
                }
            } else {
                currentOffset = 0
                allLoaded = false
                _cities.value = repository.filterCities(query, _showOnlyFav.value)
            }
            isLoading = false
        }
    }

    fun setFavorite(cityId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val city = repository.setFavorite(cityId)
            _cities.update { currentList ->
                currentList.map {
                    if (it.id == city.id) it.copy(isFavorite = city.isFavorite)
                    else it
                }
            }
        }
    }

}
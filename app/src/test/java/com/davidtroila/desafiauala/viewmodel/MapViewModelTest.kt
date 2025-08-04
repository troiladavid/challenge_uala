package com.davidtroila.desafiauala.viewmodel

import com.davidtroila.desafiauala.data.CountryRepository
import com.davidtroila.desafiauala.model.CityDTO
import com.davidtroila.desafiauala.model.CountryDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)

class MapViewModelTest {

    /*@get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()*/

    private lateinit var viewModel: MapViewModel
    private val repository: CountryRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MapViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given cityId 1 when getInfo Then city and countryInfo are not null`() = runTest {
        val fakeCity = CityDTO(id = 1, name = "Buenos Aires", country = "AR", lat = 0.0, lon = 0.0)
        val fakeCountry = CountryDTO(officialName = "AR Argentina", commonName = "Argentina")

        coEvery { repository.getCity(1) } returns fakeCity
        coEvery { repository.getCountryInfo("AR") } returns fakeCountry

        viewModel.getInfo(1)

        // Advance coroutine until idle
        advanceUntilIdle()

        // Verify the states
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(fakeCity, viewModel.city.value)
        assertEquals(fakeCountry, viewModel.countryInfo.value)
    }
}
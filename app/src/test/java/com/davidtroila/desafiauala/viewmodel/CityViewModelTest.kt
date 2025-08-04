package com.davidtroila.desafiauala.viewmodel

import com.davidtroila.desafiauala.data.CityRepository
import com.davidtroila.desafiauala.model.CityDTO
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
class CityViewModelTest {

    private lateinit var repository: CityRepository
    private lateinit var viewModel: CityViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = CityViewModel(repository)
    }

    @Test
    fun `given no data when init then get cities from repository`() = runTest {
        coEvery { repository.getCities(any(), any()) } returns listOf(
            CityDTO(1, "AR", "Buenos Aires", -34.61, -58.38, false),
            CityDTO(2,  "AR", "Córdoba", -31.41, -64.18, false)
        )
        coEvery { repository.getCitiesPaged(any(), any(), false) } returns listOf(
            CityDTO(1, "AR", "Buenos Aires", -34.61, -58.38, false),
            CityDTO(2,  "AR", "Córdoba", -31.41, -64.18, false)
        )

        viewModel.loadCities()
        advanceUntilIdle()

        val cities = viewModel.cities.value
        assert(!viewModel.loading.value)
        assertEquals(2, cities.size)
    }

    @Test
    fun `onQueryChanged filters cities`() = runTest {
        // When filtering
        coEvery { repository.filterCities("Cord", any()) } returns listOf(
            CityDTO(2,  "AR", "Córdoba", -31.41, -64.18, false)
        )

        viewModel.onQueryChanged("Cord")
        advanceTimeBy(300) // debounce
        advanceUntilIdle()

        val filtered = viewModel.cities.value
        assertEquals(1, filtered.size)
        assertEquals("Córdoba", filtered[0].name)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

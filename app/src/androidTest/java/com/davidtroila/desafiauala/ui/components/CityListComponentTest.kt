package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.davidtroila.desafiauala.model.CityDTO
import org.junit.Rule
import org.junit.Test

class CityListComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cityList_displaysCities_andHandlesClick() {
        val testCities = List(10) {
            CityDTO(id = it, name = "City $it", selected = false, country = "AR", lat = 1.0, lon = 1.0)
        }

        var selectedCity: CityDTO? = null
        var loadMoreCalled = false

        composeTestRule.setContent {
            CityListComponent(
                cities = testCities,
                error = null,
                onCitySelected = { selectedCity = it },
                paddingValues = PaddingValues(),
                onLoadMore = { loadMoreCalled = true },
                onFavoriteClicked = { }
            )
        }

        // Check that city rows are displayed
        composeTestRule.onNodeWithTag("city_row_0").assertExists()
        composeTestRule.onNodeWithTag("city_row_1").assertExists()

        // Click on a city
        composeTestRule.onNodeWithTag("city_row_3").performClick()
        assert(selectedCity?.id == 3)

        // Scroll to trigger onLoadMore
        composeTestRule.onNodeWithTag("city_row_1").performScrollTo()
        composeTestRule.waitForIdle()
        assert(loadMoreCalled)
    }

    @Test
    fun cityList_favoriteIconClickCallsCallback() {
        val testCities = listOf(
            CityDTO(id = 1, name = "City 1", selected = false, country = "AR", lat = 1.0, lon = 1.0)
        )
        var favoriteClicked: Int? = null

        composeTestRule.setContent {
            CityListComponent(
                cities = testCities,
                error = null,
                onCitySelected = {},
                paddingValues = PaddingValues(),
                onLoadMore = {},
                onFavoriteClicked = { id -> favoriteClicked = id }
            )
        }

        composeTestRule.onNodeWithTag("fav_icon_1").performClick()
        assert(favoriteClicked == 1)
    }
}
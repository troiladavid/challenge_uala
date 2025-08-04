package com.davidtroila.desafiauala.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.davidtroila.desafiauala.model.CityDTO
import org.junit.Rule
import org.junit.Test

class RowCityComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rowCityComponent_displaysCityAndRespondsToClicks() {
        val testCity = CityDTO(
            id = 1,
            name = "Memdoza",
            country = "AR",
            lat = -34.6037,
            lon = -58.3816,
            isFavorite = false,
            selected = false
        )

        var selectedCity: CityDTO? = null
        var favoriteClickedId: Int? = null

        composeTestRule.setContent {
            RowCityComponent(
                city = testCity,
                backgroundColor = Color.White,
                onCitySelected = { selectedCity = it },
                onFavouriteClicked = { favoriteClickedId = it }
            )
        }

        // Check the city row is displayed
        composeTestRule
            .onNodeWithTag("city_row_${testCity.id}")
            .assertIsDisplayed()

        // Check the favorite icon is NOT filled (FavoriteBorder)
        composeTestRule
            .onNodeWithTag("fav_icon_${testCity.id}")
            .assertIsDisplayed()

        // Perform click on the row
        composeTestRule
            .onNodeWithTag("city_row_${testCity.id}")
            .performClick()

        assert(selectedCity == testCity)

        // Perform click on the favorite icon
        composeTestRule
            .onNodeWithTag("fav_icon_${testCity.id}")
            .performClick()

        assert(favoriteClickedId == testCity.id)
    }
}
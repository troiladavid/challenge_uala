package com.davidtroila.desafiauala.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class MapComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Ignore
    fun mapComponent_shouldDisplayMap_whenCoordinatesAreProvided() {
        composeTestRule.setContent {
            MapComponent(
                modifier = Modifier.padding(1.dp),
                latitude = 37.7749,
                longitude = -122.4194
            )
        }

        composeTestRule.onNodeWithTag("google_map").assertExists()
    }

    @Test
    fun mapComponent_shouldNotDisplayMap_whenCoordinatesAreNull() {
        composeTestRule.setContent {
            MapComponent(
                modifier = Modifier.padding(1.dp),
                latitude = null,
                longitude = null
            )
        }

        composeTestRule.onNodeWithTag("google_map").assertDoesNotExist()
    }


}
package com.davidtroila.desafiauala.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class SearchBarComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun given_searchBar_when_queryTextChange_then_textIsUpdated() {
        var queryText = ""
        composeTestRule.setContent {
            SearchBarComponent(
                query = queryText,
                onQueryChanged = {queryText = it},
                onShowFavClicked = { },
                isShowingFav = false
            )
        }

        composeTestRule.onNodeWithTag("search_field").performTextInput("Mendoza")
        assert(queryText == "Mendoza")
    }

    @Test
    fun given_searchBar_when_favIconClicked_then_IconIsUpdated() {
        var favClicked = false
        composeTestRule.setContent {
            SearchBarComponent(
                query = "",
                onQueryChanged = {},
                onShowFavClicked = {favClicked = !favClicked },
                isShowingFav = favClicked
            )
        }

        composeTestRule.onNodeWithTag("search_fav_icon").performClick()
        assert(favClicked)
        composeTestRule.onNodeWithTag("search_fav_icon").performClick()
        assert(!favClicked)
    }
}
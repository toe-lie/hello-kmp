package dev.toelie.hellokmp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso.pressBack
import org.junit.Rule
import org.junit.Test

class NewsListTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun launch_displays_bundled_article_titles() {
        composeRule.onNodeWithText("Morning update").assertIsDisplayed()
        composeRule.onNodeWithText("Science report").assertIsDisplayed()
    }

    @Test
    fun selecting_article_displays_its_detail() {
        composeRule.onNodeWithText("Science report").performClick()
        composeRule.onNodeWithText("Science report").assertIsDisplayed()
        composeRule.onNodeWithText("Researchers tested a new method for storing solar energy.").assertIsDisplayed()
    }

    @Test
    fun back_returns_to_list_and_allows_selecting_another_article() {
        val scienceBody =
            "Researchers tested a new method for storing solar energy."
        val morningBody =
            "OpenAI has temporarily paused the training of its latest AI models."

        composeRule.onNodeWithText("Science report").performClick()
        composeRule.onNodeWithText(scienceBody).assertIsDisplayed()

        pressBack()

        composeRule.onNodeWithText("Morning update").assertIsDisplayed()
        composeRule.onNodeWithText("Science report").assertIsDisplayed()
        composeRule.onNodeWithText(scienceBody).assertIsNotDisplayed()

        composeRule.onNodeWithText("Morning update").performClick()
        composeRule.onNodeWithText(morningBody).assertIsDisplayed()
        composeRule.onNodeWithText(scienceBody).assertIsNotDisplayed()
    }

    @Test
    fun bookmarking_an_article_updates_the_action() {
        composeRule.onNodeWithText("Science report").performClick()
        composeRule.onNodeWithText("Science report").assertIsDisplayed()

        composeRule.onNodeWithText("Bookmark").assertIsDisplayed()
        composeRule.onNodeWithText("Bookmark").performClick()

        composeRule.onNodeWithText("Remove bookmark").assertIsDisplayed()
        composeRule.onNodeWithText("Bookmark").assertIsNotDisplayed()
    }

    @Test
    fun removing_a_bookmark_restores_the_bookmark_action() {
        composeRule.onNodeWithText("Science report").performClick()
        composeRule.onNodeWithText("Bookmark").performClick()
        composeRule.onNodeWithText("Remove bookmark").assertIsDisplayed()

        composeRule.onNodeWithText("Remove bookmark").performClick()

        composeRule.onNodeWithText("Bookmark").assertIsDisplayed()
        composeRule.onNodeWithText("Remove bookmark").assertIsNotDisplayed()
    }
}
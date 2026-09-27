package dev.toelie.hellokmp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
}
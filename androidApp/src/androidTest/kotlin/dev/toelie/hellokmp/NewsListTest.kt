package dev.toelie.hellokmp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
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
}
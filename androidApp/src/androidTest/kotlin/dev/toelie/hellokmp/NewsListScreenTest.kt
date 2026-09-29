package dev.toelie.hellokmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class NewsListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun empty_list_displays_no_news_message() {
        composeTestRule.setContent {
            MaterialTheme {
                NewsListScreen(
                    articles = emptyList(),
                    bookmarks = Bookmarks(),
                    onArticleClick = {})
            }
        }
        composeTestRule.onNodeWithText("No news available").assertIsDisplayed()

    }
}
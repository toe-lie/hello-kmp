package dev.toelie.hellokmp

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BookmarksTest {

    @Test
    fun bookmarking_an_article_marks_only_that_article() {
        val bookmarks = Bookmarks()

        bookmarks.add("science-report")

        assertTrue(bookmarks.contains("science-report"))
        assertFalse(bookmarks.contains("morning-update"))
    }
}
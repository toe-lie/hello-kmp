package dev.toelie.hellokmp

class Bookmarks {
    val bookmarks = mutableSetOf<String>()
    fun add(articleId: String) {
        bookmarks.add(articleId)
    }

    fun contains(articleId: String): Boolean {
        return bookmarks.contains(articleId)
    }
}
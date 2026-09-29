package dev.toelie.hellokmp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.map

class Bookmarks {

    private val _ids = MutableStateFlow<Set<String>>(emptySet())

    fun add(articleId: String) {
        _ids.update { current -> current + articleId }
    }

    fun remove(articleId: String) {
        _ids.update { current -> current - articleId }
    }

    fun contains(articleId: String): Boolean {
        return calculateContains(articleId, _ids.value)
    }

    fun observeContains(articleId: String): Flow<Boolean> {
        return _ids.map { currentIds ->
            calculateContains(articleId, currentIds)
        }.distinctUntilChanged()
    }

    private fun calculateContains(
        articleId: String,
        currentIds: Set<String>
    ): Boolean {
        return articleId in currentIds;
    }
}
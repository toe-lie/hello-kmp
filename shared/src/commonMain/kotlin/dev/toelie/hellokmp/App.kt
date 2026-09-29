package dev.toelie.hellokmp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation3.runtime.NavKey
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
private data object NewsListRoute : NavKey

@Serializable
private data class NewsDetailRoute(val id: String) : NavKey

private val navigationConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(NewsListRoute::class, NewsListRoute.serializer())
            subclass(NewsDetailRoute::class, NewsDetailRoute.serializer())
        }
    }
}

data class Article(
    val id: String,
    val title: String,
    val body: String,
)

val articles = listOf(
    Article(
        id = "morning-update",
        title = "Morning update",
        body = "OpenAI has temporarily paused the training of its latest AI models."
    ),
    Article(
        id = "science-report",
        title = "Science report",
        body = "Researchers tested a new method for storing solar energy."
    )
)

@Composable
@Preview
fun App() {
    val bookmarks = remember { Bookmarks() }
    val backStack = rememberNavBackStack(navigationConfig, NewsListRoute)
    MaterialTheme {
        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding(),
            backStack = backStack,
            entryProvider = entryProvider {
                entry<NewsListRoute> {
                    NewsListScreen(
                        articles = articles,
                        bookmarks = bookmarks,
                        onArticleClick = { articleId ->
                            backStack.add(NewsDetailRoute(articleId))
                        })
                }
                entry<NewsDetailRoute> {
                    NewsDetailScreen(
                        articleId = it.id,
                        bookmarks = bookmarks,
                    )
                }
            }
        )
    }
}

@Composable
fun NewsListScreen(
    articles: List<Article>,
    bookmarks: Bookmarks,
    onArticleClick: (String) -> Unit,
) {
    if (articles.isEmpty()) {
        return Text("No news available")
    }

    Column {
        articles.forEach { article ->
            NewsListRow(
                article = article,
                bookmarks = bookmarks,
                onArticleClick = onArticleClick
            )
        }
    }
}

@Composable
private fun NewsListRow(
    article: Article,
    bookmarks: Bookmarks,
    onArticleClick: (String) -> Unit,
) {
    val bookmarkStatus = remember(bookmarks, article.id) {
        bookmarks.observeContains(article.id)
    }
    val isBookmarked by bookmarkStatus.collectAsState(
        initial = bookmarks.contains(article.id)
    )

    Column(
        modifier = Modifier
            .clickable(true) {
                onArticleClick(article.id)
            }) {
        Text(article.title)
        if (isBookmarked) {
            Text("Bookmarked")
        }
    }
}

@Composable
private fun NewsDetailScreen(
    articleId: String,
    bookmarks: Bookmarks,
) {
    val article = articles.find { it.id == articleId }
    val bookmarkStatus = remember(bookmarks, articleId) {
        bookmarks.observeContains(articleId)
    }
    val isBookmarked by bookmarkStatus.collectAsState(
        initial = bookmarks.contains(articleId)
    )

    Column {
        Text(article?.title ?: "")
        if (isBookmarked)
            Button(onClick = { bookmarks.remove(articleId) }) {
                Text("Remove bookmark")
            }
        else
            Button(onClick = { bookmarks.add(articleId) }) {
                Text("Bookmark")
            }
        Text(article?.body ?: "")
    }
}

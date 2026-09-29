package dev.toelie.hellokmp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

@Serializable
private data class ArticleSummaryResponse(
    val id: String,
    val title: String,
)

class HttpArticleSource(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun loadArticles(): List<ArticleSummary> {
        val url = "${baseUrl.trimEnd('/')}/articles"
        val response = client.get(url).body<List<ArticleSummaryResponse>>()
        return response.map {
            ArticleSummary(it.id, it.title)
        }
    }
}
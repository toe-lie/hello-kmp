package dev.toelie.hellokmp

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import org.junit.Test
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HttpArticleSourceTest {

    @Test
    fun loads_article_summaries_in_response_order() = runBlocking {
        MockWebServer().use { server ->
            server.enqueue(
                MockResponse.Builder()
                    .code(200)
                    .addHeader("Content-Type", "application/json")
                    .body("""
                        [
                            {"id": "science-report", "title": "Science report"},
                            {"id": "morning-update", "title": "Morning update"}
                        ]
                    """.trimIndent())
                    .build()
            )
            server.start()

            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 5_000
                }
            }

            client.use { client ->
                val source = HttpArticleSource(
                    client = client,
                    baseUrl = server.url("/").toString()
                )
                val articles = source.loadArticles()

                assertEquals(
                    listOf(
                        ArticleSummary("science-report", "Science report"),
                        ArticleSummary("morning-update", "Morning update"),
                    ),
                    articles,
                )

                val request = assertNotNull(
                    server.takeRequest(1, TimeUnit.SECONDS),
                    "Expected an article request",
                )

                assertEquals("GET", request.method)
                assertEquals("/articles", request.url.encodedPath)
                assertEquals("application/json", request.headers["Accept"])
            }


        }
    }
}
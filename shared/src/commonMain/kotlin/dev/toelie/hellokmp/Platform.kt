package dev.toelie.hellokmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
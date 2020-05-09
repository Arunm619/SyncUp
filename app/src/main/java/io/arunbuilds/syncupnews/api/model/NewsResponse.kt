package io.arunbuilds.syncupnews.api.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
package com.example.sigit_moonlight.pertemuan_11.news

data class NewsResponse(
    val code: Int,
    val status: String,
    val messages: String?,
    val data: List<NewsPost>?
)

data class NewsPost(
    val title: String,
    val link: String,
    val contentSnippet: String?,
    val isoDate: String?,
    val image: NewsImage?
)

data class NewsImage(
    val small: String?,
    val large: String?
)
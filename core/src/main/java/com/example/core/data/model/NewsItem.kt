package com.example.core.data.model

data class NewsItem(
    val title: String,
    val author_name: String,
    val date: String,
    val url: String,
    val thumbnail_pic_s: String? = null,
    val thumbnail_pic_s02: String? = null,
    val thumbnail_pic_s03: String? = null
)
data class NewsResponse(
    val reason: String,
    val result: NewsResult
)

data class NewsResult(
    val stat: String,
    val data: List<NewsItem>
)

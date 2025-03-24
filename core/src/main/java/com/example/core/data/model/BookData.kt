package com.example.core.data.model

data class Book(
    val title: String,
    val img: String,
    val author: String,
    val isbn: String,
    val isbn10: String,
    val publisher: String,
    val pubdate: String,
    val keyword: String,
    val pages: String,
    val price: String,
    val binding: String,
    val summary: String
)

data class BookResponse(
    val code: Int,
    val msg: String,
    val data: Book
)

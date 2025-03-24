package com.example.core.data.model

data class DailyResponse(
    val reason: String,
    val result: DailyData,
    val error_code: Int
)

data class DailyData(
    val text: String
)

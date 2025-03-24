package com.example.student.domain.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate


data class CalendarDay(
    val date: LocalDate,               // 日期
    val dayOfWeek: DayOfWeek,          // 星期几
    val lunar: String = "",            // 农历日期，默认为空字符串
    val festival: String? = null       // 节日信息，默认为 null
)
data class CalendarEvent(
    val id: Long,
    val title: String,
    val startDate: Long,
    val endDate: Long
)
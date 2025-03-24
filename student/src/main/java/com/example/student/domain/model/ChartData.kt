package com.example.student.domain.model

import androidx.compose.ui.graphics.Color

data class PieChartData(
    val value: Float,
    val color: Color,
    val title:String
)

data class LineChartData(
    val time: List<Float>,  // 学习时间对应的纵坐标数据
    val week: List<String>, // 星期几作为横坐标
    val color: Color        // 折线与 x 轴之间区域的颜色
)

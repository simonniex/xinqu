package com.example.student.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.student.domain.model.LineChartData
import com.example.student.domain.model.PieChartData

@Composable
fun PieChart4(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    animationDuration: Int = 3000,
    reloadTrigger: Int,
    onClick: () -> Unit
) {
    val totalValue = data.sumOf { it.value.toDouble() }.toFloat()
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(key1 = reloadTrigger) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = LinearOutSlowInEasing
            )
        )
    }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() })
            }
    ) {
        var startAngle = -90f
        data.forEach { pieData ->
            val sweepAngle = 360f * (pieData.value / totalValue) * animatedProgress.value
            drawArc(
                color = pieData.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun LineChart(
    data: LineChartData,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1000,
    lineThickness: Float = 4f
) {
    val maxTime = 12f  // 最大学习时间为12小时
    val minTime = 2f   // 最小学习时间为2小时
    val timeSteps = 6  // 显示 2h 到 12h 的6个刻度
    val yAxisValues = (minTime.toInt()..maxTime.toInt() step 2).toList()  // 2, 4, 6, 8, 10, 12

    val animatedProgress = remember { Animatable(0f) }

    // 动画效果
    LaunchedEffect(key1 = data) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Canvas(modifier = modifier.padding(16.dp)) {
        val chartWidth = size.width
        val chartHeight = size.height
        val xInterval = chartWidth / (data.week.size - 1) // 计算x轴刻度间距
        val yMax = chartHeight * 0.8f // 留一些空白给标题和纵坐标标签
        val yInterval = yMax / (timeSteps - 1) // 计算y轴刻度间距

        // 绘制 X 和 Y 轴
        drawLine(
            color = Color.Gray,
            start = Offset(0f, yMax),
            end = Offset(chartWidth, yMax),
            strokeWidth = lineThickness
        )

        drawLine(
            color = Color.Gray,
            start = Offset(0f, 0f),
            end = Offset(0f, yMax),
            strokeWidth = lineThickness
        )

        // 绘制 Y 轴的坐标与虚线
        yAxisValues.forEachIndexed { index, value ->
            val y = yMax - index * yInterval
            // 虚线
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, y),
                end = Offset(chartWidth, y),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)),  // 设置虚线样式
                strokeWidth = 1.dp.toPx()
            )
            // 显示 Y 轴坐标
            drawContext.canvas.nativeCanvas.drawText(
                "${value}h",
                -30f,  // 坐标值在Y轴左侧显示
                y + 10f,  // 调整文本位置与虚线对齐
                android.graphics.Paint().apply {
                    textSize = 30f
                    color = android.graphics.Color.BLACK
                    textAlign = android.graphics.Paint.Align.RIGHT
                }
            )
        }

        // 绘制折线图下方填充的区域
        for (i in 0 until data.time.size - 1) {
            val x1 = i * xInterval
            val x2 = (i + 1) * xInterval
            val y1 = (yMax - (data.time[i] - minTime) / (maxTime - minTime) * yMax) * animatedProgress.value
            val y2 = (yMax - (data.time[i + 1] - minTime) / (maxTime - minTime) * yMax) * animatedProgress.value

            drawPath(
                path = Path().apply {
                    moveTo(x1, yMax)
                    lineTo(x1, y1)
                    lineTo(x2, y2)
                    lineTo(x2, yMax)
                    close()
                },
                color = data.color
            )
        }

        // 绘制折线
        for (i in 0 until data.time.size - 1) {
            val x1 = i * xInterval
            val x2 = (i + 1) * xInterval
            val y1 = (yMax - (data.time[i] - minTime) / (maxTime - minTime) * yMax) * animatedProgress.value
            val y2 = (yMax - (data.time[i + 1] - minTime) / (maxTime - minTime) * yMax) * animatedProgress.value

            drawLine(
                color = Color.Black,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = lineThickness
            )
        }

        // 绘制 X 轴的标签（星期几）
        data.week.forEachIndexed { index, day ->
            val x = index * xInterval
            drawContext.canvas.nativeCanvas.drawText(
                day,
                x,
                yMax + 30f, // 标签距离X轴的距离
                android.graphics.Paint().apply {
                    textSize = 30f
                    color = android.graphics.Color.BLACK
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

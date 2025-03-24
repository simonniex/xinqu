package com.example.student.presentation.home.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.student.component.LineChart
import com.example.student.component.PieChart4
import com.example.student.domain.model.LineChartData
import com.example.student.domain.model.PieChartData

@Destination("student_home_chart_route")
@Composable
fun MyDataDetail(navController: NavHostController){
    LazyColumn(
        modifier = Modifier.statusBarsPadding()
    ) {
        item {
            BackLine(navController,"个人数据报告")
        }
        item {
            androidx.compose.material.Text("学习分布", modifier = Modifier.padding(16.dp))
        }
        item {
            PieChartScreen()
        }
        item {
            androidx.compose.material.Text("学习时长", modifier = Modifier.padding(16.dp))
        }
        item {
            LineChartScreen()
        }
//        item {
//            androidx.compose.material.Text("AI分析", modifier = Modifier.padding(16.dp))
//        }
//        item {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),  // 设置卡片的外边距
//                elevation = CardDefaults.elevatedCardElevation(8.dp),   // 设置卡片的阴影高度
//                shape = RoundedCornerShape(12.dp)  // 圆角形状
//            ) {
//                Text("我是智能分析报告")
//            }
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineChartScreen() {
    val lineChartData = LineChartData(
        time = listOf(12f, 4f, 6f, 8f, 4f, 2f, 12f),
        week = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日"),
        color = Color.Transparent
    )

    // 使用Card来包裹折线图
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // 设置内边距
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "学习时间折线图",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LineChart(
                data = lineChartData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PieChartScreen() {
    val pieData = listOf(
        PieChartData(value = 25f, color = Color(0xFFffd08c), title = "实训"),
        PieChartData(value = 35f, color = Color(0xFFfff78c), title = "学习"),
        PieChartData(value = 20f, color = Color(0xFF8ceaff), title = "问答"),
        PieChartData(value = 20f, color = Color(0xFFc0ff8c), title = "自习")
    )

    var reloadTrigger by remember { mutableStateOf(0) }

    MaterialTheme {
        // 使用 Card 包裹内容
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),  // 设置卡片的外边距
            elevation = CardDefaults.elevatedCardElevation(8.dp),   // 设置卡片的阴影高度
            shape = RoundedCornerShape(12.dp)  // 圆角形状
        ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),  // 设置卡片内部的内容边距
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 左边的标注文本与颜色方块
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // 添加每个饼图片段的标注与颜色方块
                        pieData.forEach { pieData ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                // 颜色方块
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(pieData.color)
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // 用于分隔方块和文字
                                // 标题和百分比
                                Text(
                                    text = "${pieData.title}: ${pieData.value}%",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }

                    // 饼图
                    PieChart4(
                        data = pieData,
                        modifier = Modifier
                            .size(150.dp)  // 设置饼图大小
                            .aspectRatio(1f),
                        animationDuration = 3000,
                        reloadTrigger = reloadTrigger,
                        onClick = {
                            reloadTrigger++
                        }
                    )
                }
        }
    }
}
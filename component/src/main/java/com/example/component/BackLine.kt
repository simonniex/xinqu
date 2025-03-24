package com.example.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BackLine(navController: NavHostController, text: String) {
    // 使用 remember 避免不必要的重组
    val arrowBackIcon = remember { Icons.Filled.ArrowBack }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .drawBehind {
                // 在组件下方绘制一条阴影或线条
                drawLine(
                    color = Color.LightGray, // 线条颜色
                    start = Offset(0f, size.height), // 从左下角开始
                    end = Offset(size.width, size.height), // 到右下角结束
                    strokeWidth = 4f // 线条宽度
                )
            }
            .background(Color.White)
    ) {
        // Row 放在 Box 中，左右对齐图标
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 左侧的返回按钮
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = arrowBackIcon,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

        }

        // 让 Text 绝对居中
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

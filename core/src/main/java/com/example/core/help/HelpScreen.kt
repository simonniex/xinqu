package com.example.core.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination


@Destination("about_us")
@Composable
fun AboutUsScreen(navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        BackLine(navController,"关于心驱")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 图片
            Image(
                painter = painterResource(id = com.example.core.R.drawable.core_ic_launcher), // 替换为你的图片资源
                contentDescription = "关于我们的图片",
                modifier = Modifier
                    .size(200.dp) // 设置图片大小
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp)) // 间隔

            // 开发人员
            Text(
                text = "开发人员: 江苏安全技术职业学院 聂鑫",
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(8.dp)) // 间隔

            // 开发时间
            Text(
                text = "开发时间: 2024年6月-2024年9月",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(8.dp)) // 间隔

            // 软件介绍
            Text(
                text = "软件介绍: 本软件用于联动学生，教师和企业并为其提供大量实用功能，旨在提升专科学生的内驱力，实现国家大力支持发展的现代化职业教育体系。",
                style = MaterialTheme.typography.body1
            )

            Spacer(modifier = Modifier.height(8.dp)) // 间隔

            // 软件技术总结
            Text(
                text = buildAnnotatedString {
                    append("技术总结:\n")
                    append("移动端: 使用最主流最先进的 Android 原生开发技术，Kotlin 结合 Jetpack Compose。\n")
                    append("桌面端: 采用 Python 的 Tkinter 实现意见反馈功能。\n")
                    append("后端: 使用 JavaScript 编写 Node.js 的 Express 框架服务接口，以及 Python 使用 FastAPI 编写 HTTP 接口，和 Kotlin 结合 Ktor 编写 WebSocket 服务器端。")
                },
                style = MaterialTheme.typography.body1
            )

        }
    }

}

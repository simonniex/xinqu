package com.example.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImprovedBadgeExample() {
    BadgedBox(
        badge = {
            Badge(
                content = {
                    Text("5")
                },
                containerColor = MaterialTheme.colorScheme.error, // 自定义颜色
                contentColor = Color.White
            )
        }
    ) {
        androidx.compose.material.Icon(
            imageVector = Icons.Default.Notifications,  // 使用默认的搜索图标
            contentDescription = "Message",
            tint = Color.Black // 可选：设置图标颜色
        )
    }
}
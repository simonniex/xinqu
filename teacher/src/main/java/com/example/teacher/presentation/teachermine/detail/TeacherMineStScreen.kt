package com.example.teacher.presentation.teachermine.detail

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination

@Destination("teacher_mine_st")
@Composable
fun TeacherMineStScreen(navController: NavHostController) {
    var clubName by remember { mutableStateOf("") } // 存储社团名称
    var selectedImageUrl by remember { mutableStateOf("") } // 用于存储选中的图片 URL
    val context = LocalContext.current

    // 创建一个 ActivityResultLauncher 用于选择图片
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUrl = it.toString() // 更新选中的图片 URL
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween // 分散上下内容
    ) {
        // 社团名称
        BackLine(navController,"我的社团")

        Spacer(modifier = Modifier.height(8.dp))

        // 社团图片显示区域
        if (selectedImageUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUrl),
                contentDescription = "Club Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            // 默认图像或占位符
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("请选择图片", color = Color.DarkGray)
            }
        }

        // 底部的 Row，包含选择图片的图标、输入框和发送按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 选择图片按钮
            IconButton(onClick = { launcher.launch("image/*") }) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto, // 选择适合的图标
                    contentDescription = "选择社团图片"
                )
            }

            // 输入社团名称的文本框
            TextField(
                value = clubName,
                onValueChange = { newName -> clubName = newName },
                label = { Text("输入社团名称") },
                modifier = Modifier.weight(1f) // 填充剩余空间
            )

            // 发送按钮
            Button(onClick = { /* 发送社团信息逻辑 */ }) {
                Text("发送")
            }
        }
    }
}


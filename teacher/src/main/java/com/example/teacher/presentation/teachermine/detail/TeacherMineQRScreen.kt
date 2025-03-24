package com.example.teacher.presentation.teachermine.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.teacher.presentation.teachermine.detail.viewmodel.TeacherMineQRViewModel

@Destination("teacher_mine_qr")
@Composable
fun TeacherMineQRScreen(navController: NavHostController,viewModel: TeacherMineQRViewModel = hiltViewModel()) {
    val inputText by viewModel.inputText.collectAsState()
    val qrCodeUrl by viewModel.qrCodeUrl.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackLine(navController,"二维码")

        // 显示二维码图片
        if (qrCodeUrl != null) {
            Image(
                painter = rememberImagePainter(qrCodeUrl),
                contentDescription = "QR Code",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        } else {
            Image(
                painter = painterResource(id = com.example.teacher.R.drawable.queshengtu1),
                contentDescription = "QR Code Placeholder",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 输入框
        OutlinedTextField(
            value = inputText,
            onValueChange = { viewModel.updateInputText(it) },
            label = { Text("输入内容") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 发送按钮
        Button(
            onClick = { viewModel.fetchQRCode() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("发送")
        }
    }
}


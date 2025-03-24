package com.example.teacher.presentation.teacherhome.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.data.model.CourseData
import com.example.core.nav.Destination
import com.example.core.other.util.internet.Message
import com.example.teacher.presentation.teacherhome.detail.viewmodel.TeacherAssistantViewModel

@Destination("teacher_assisant_detail")
@Composable
fun TeacherAssistantDetail(navController:NavHostController,viewModel: TeacherAssistantViewModel = hiltViewModel()) {

    Column(modifier = Modifier.fillMaxSize()) {
        BackLine(navController,"智能助手")
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false
        ) {
            items(viewModel.courseList) { course ->
                CourseBubble(course)
            }
        }

        // 输入框和发送按钮
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.inputText,
                onValueChange = { viewModel.onInputTextChanged(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("输入问题") }
            )
            androidx.compose.material3.Button(
                onClick = {
                    viewModel.postMessage() // 假设这是发送课程信息的函数
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("发送")
            }
        }
    }
}

@Composable
fun CourseBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.role == "assistant") Arrangement.Start else Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (message.role == "assistant") Color.LightGray else Color.Green,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {
            androidx.compose.material.Text(
                text = message.content,
                color = if (message.role == "assistant") Color.Black else Color.White,
                fontSize = 16.sp
            )
        }
    }
}

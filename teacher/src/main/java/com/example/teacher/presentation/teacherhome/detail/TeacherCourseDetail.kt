package com.example.teacher.presentation.teacherhome.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.teacher.presentation.teacherhome.detail.viewmodel.TeacherCourseViewModel



@Destination("teacher_course_detail")
@Composable
fun TeacherCourseDetail(navController: NavHostController, viewModel: TeacherCourseViewModel = hiltViewModel()) {
    // 使用 ViewModel 中的状态流
    val courseName by viewModel.inputText.collectAsState()
    val instructorName by viewModel.instructorName.collectAsState()
    val location by viewModel.location.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BackLine(navController, "课程安排")

        // 输入课程名称
        OutlinedTextField(
            value = courseName,
            onValueChange = { viewModel.updateInputText(it) }, // 更新 ViewModel 中的输入框值
            label = { Text("课程名称") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入授课老师
        OutlinedTextField(
            value = instructorName,
            onValueChange = { viewModel.updateInstructorName(it) }, // 更新 ViewModel 中的授课老师
            label = { Text("授课老师") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入授课地点
        OutlinedTextField(
            value = location,
            onValueChange = { viewModel.updateLocation(it) }, // 更新 ViewModel 中的授课地点
            label = { Text("授课地点") },
            modifier = Modifier.fillMaxWidth()
        )

        // 按钮
        Button(onClick = { viewModel.addCourse() }) {
            Text("发布课程")
        }
    }
}

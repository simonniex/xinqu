package com.example.student.presentation.home.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.data.model.CourseData
import com.example.core.nav.Destination


@Destination("student_home_class_route")
@Composable
fun ClassDetail(navController: NavHostController, viewModel: ClassDetailViewModel = hiltViewModel()) {
    var selectedTab by remember { mutableStateOf(0) }

    // 根据选中的标签获取课程
    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            0 -> viewModel.getCourse("teacher") // 校园课程
            1 -> viewModel.getCourse("company") // 企业课程
        }
    }

    val courses = if (selectedTab == 0) viewModel.campusCourses else viewModel.companyCourses

    LazyColumn(
        modifier = Modifier
            .statusBarsPadding().fillMaxSize()
    ) {
        item {
            BackLine(navController, "课程")
        }
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Text("校园课程", modifier = Modifier.padding(16.dp))
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Text("企业课程", modifier = Modifier.padding(16.dp))
                }
            }
        }
        items(courses.value) { course ->
            CourseItem(course, onCourseSelected = { /* 点击事件 */ })
        }
    }
}

@Composable
fun CourseItem(course: CourseData, onCourseSelected: (CourseData) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onCourseSelected(course) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = course.instructorName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = course.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course.courseName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = course.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

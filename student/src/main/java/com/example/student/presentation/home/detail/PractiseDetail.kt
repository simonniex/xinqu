package com.example.student.presentation.home.detail

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.core.nav.Destination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.student.domain.model.Competition
import com.example.student.domain.model.Exam
import com.example.student.domain.model.Project

@Destination("student_home_practise_route")
@Composable
fun PractiseDetail( navController: NavHostController){
    var selectedTab by remember { mutableStateOf("项目") }
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        BackLine(navController,"实训")
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxSize()) {
            // 左侧的按钮部分
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            ) {
                Button(
                    onClick = { selectedTab = "项目" },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  MaterialTheme.colorScheme.primary// 自定义颜色
                    ),
                    shape = RectangleShape // 移除圆角
                ) {
                    Text("项目", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { selectedTab = "考试" },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RectangleShape
                ) {
                    Text("考试",fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { selectedTab = "比赛" },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RectangleShape
                ) {
                    Text("比赛",fontSize = 18.sp)
                }
            }
            // 添加分割线
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            // 右侧显示内容的 LazyColumn
            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    "项目" -> ProjectList(projects = getProjectData())
                    "考试" -> ExamList(exams = getExamData())
                    "比赛" -> CompetitionList(competitions = getCompetitionData())
                }
            }
        }
    }
}

@Composable
fun ProjectList(projects: List<Project>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(projects) { project ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = project.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = project.requirement, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun ExamList(exams: List<Exam>) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(exams) { exam ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = exam.name, style = MaterialTheme.typography.titleMedium)
                        Button(onClick = {
                            val toast = Toast.makeText(context, "参加成功", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.TOP, 0, 100)  // 将Toast显示在顶部，yOffset为100像素
                            toast.show()
                        }) {
                            Text("参加")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "地点: ${exam.location}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "时间: ${exam.time}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun CompetitionList(competitions: List<Competition>) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(competitions) { competition ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = competition.name, style = MaterialTheme.typography.titleMedium)
                        Button(onClick = {
                            val toast = Toast.makeText(context, "参加成功", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.TOP, 0, 100)  // 将Toast显示在顶部，yOffset为100像素
                            toast.show()
                        }) {
                            Text("参加")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = competition.introduction, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "时间: ${competition.time}", style = MaterialTheme.typography.bodyMedium)
                    if (competition.isOngoing) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "报名截止: ${competition.registrationDeadline}", style = MaterialTheme.typography.bodyMedium)
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "已结束", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

// 假数据
fun getProjectData(): List<Project> {
    return listOf(
        Project("项目1", "项目1的要求"),
        Project("项目2", "项目2的要求"),
        Project("项目3", "项目3的要求")
    )
}

fun getExamData(): List<Exam> {
    return listOf(
        Exam("考试1", "A教室", "2024-10-01"),
        Exam("考试2", "B教室", "2024-10-05"),
        Exam("考试3", "C教室", "2024-10-10")
    )
}

fun getCompetitionData(): List<Competition> {
    return listOf(
        Competition("比赛1", "比赛1简介", "2024-09-30", true, "2024-09-29"),
        Competition("比赛2", "比赛2简介", "2024-10-10", false, "")
    )
}
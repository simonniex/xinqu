package com.example.teacher.presentation.teachermine.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.teacher.presentation.teachermine.detail.viewmodel.XinCheng
import com.example.teacher.presentation.teachermine.detail.viewmodel.XinChengRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.teacher.presentation.teachermine.detail.viewmodel.TeacherMineXcViewModel
import com.example.teacher.presentation.teachermine.detail.viewmodel.ViewModelFactory

@Destination("teacher_mine_xc")
@Composable
fun TeacherMineXcScreen(navController: NavHostController) {
    val context = LocalContext.current
    val repository = XinChengRepository(context)
    val viewModel: TeacherMineXcViewModel = viewModel(factory = ViewModelFactory(repository))

    // 获取当前状态
    val xinChengState = viewModel.xinCheng.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        BackLine(navController, "我的行程")

        // 水平居中输入框
        TextField(
            value = xinChengState.place,
            onValueChange = { place -> viewModel.updateXinCheng(xinChengState.copy(place = place)) },
            label = { Text("上课地点") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        TextField(
            value = xinChengState.time,
            onValueChange = { time -> viewModel.updateXinCheng(xinChengState.copy(time = time)) },
            label = { Text("上课时间") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        TextField(
            value = xinChengState.course,
            onValueChange = { course -> viewModel.updateXinCheng(xinChengState.copy(course = course)) },
            label = { Text("上课科目") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        TextField(
            value = xinChengState.context,
            onValueChange = { context -> viewModel.updateXinCheng(xinChengState.copy(context = context)) },
            label = { Text("上课内容") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )

        Button(onClick = {
            viewModel.saveXinCheng()
        }) {
            Text("保存")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn 显示数据库中的内容
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listOf(xinChengState)) { item -> // 使用一个列表
                Text(text = "地点: ${item.place}, 时间: ${item.time}, 科目: ${item.course}, 内容: ${item.context}")
            }
        }
    }
}


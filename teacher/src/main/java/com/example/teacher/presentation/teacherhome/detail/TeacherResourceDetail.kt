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
import com.example.teacher.presentation.teacherhome.detail.viewmodel.TeacherResourceViewModel
import androidx.compose.material3.*


@Composable
fun TeacherResourceDetail(navController: NavHostController, viewModel: TeacherResourceViewModel = hiltViewModel()) {
    // 观察 ViewModel 中的状态
    val resourceTitle by viewModel.resourceTitle.collectAsState()
    val resourceLink by viewModel.resourceLink.collectAsState()
    val imageLink by viewModel.imageLink.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackLine(navController, "资源发布")

        // 输入资源标题
        OutlinedTextField(
            value = resourceTitle,
            onValueChange = {
                viewModel.updateResourceTitle(it) // 更新 ViewModel 中的标题
            },
            label = { Text("请输入资源标题") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入资源链接
        OutlinedTextField(
            value = resourceLink,
            onValueChange = {
                viewModel.updateResourceLink(it) // 更新 ViewModel 中的链接
            },
            label = { Text("请输入资源链接") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入图片链接
        OutlinedTextField(
            value = imageLink,
            onValueChange = {
                viewModel.updateImageLink(it) // 更新 ViewModel 中的图片链接
            },
            label = { Text("请输入图片链接") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { viewModel.publishResource() }) {
            Text("发布资源")
        }
    }
}

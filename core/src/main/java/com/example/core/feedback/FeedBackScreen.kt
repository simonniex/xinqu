package com.example.core.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Destination("feedback_route")
@Composable
fun FeedBackScreen(
    identity: String,
    username: String,
    naviController: NavHostController,
    viewModel: FeedBackViewModel = hiltViewModel()
) {
    var feedbackText by remember { mutableStateOf("") }
    val feedbackList by viewModel.feedbackList.collectAsState() // 使用 collectAsState 观察变化
    var isRefreshing by remember { mutableStateOf(false) } // 刷新状态

    LaunchedEffect(Unit) {
        viewModel.fetchFeedbackData1(identity, username)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BackLine(naviController, "用户反馈")

        // 使用 SwipeRefresh 组件
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = {
                isRefreshing = true
                viewModel.fetchFeedbackData1(identity, username) // 重新获取数据
                isRefreshing = false // 刷新结束
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        label = { Text("输入反馈内容") },
                        modifier = Modifier.fillMaxWidth().height(200.dp).padding(horizontal = 16.dp),
                        placeholder = { Text("在此输入反馈...") }
                    )
                }
                item {
                    Button(
                        onClick = {
                            if (feedbackText.isNotBlank()) {
                                viewModel.sendFB(identity, username, feedbackText) // 发送反馈
                                feedbackText = ""
                                // 发送反馈后立即重新获取反馈数据
                                viewModel.fetchFeedbackData1(identity, username)
                            }
                        }
                    ) {
                        Text("发送")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(feedbackList) { feedback ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(4.dp) // 减少圆角
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("ID: ${feedback.id}", style = MaterialTheme.typography.bodyMedium)
                            Text("内容: ${feedback.fanKui.content}", style = MaterialTheme.typography.bodyLarge)
                            Text("回应: ${feedback.fanKui.back}", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                text = "状态: ${feedback.fanKui.state}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = when (feedback.fanKui.state) {
                                    "已完成" -> Color.Green // 绿色
                                    "待处理" -> Color.Red // 红色
                                    else -> Color.Black // 默认颜色
                                }
                            )
                            Text("时间: ${feedback.fanKui.sendTime}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

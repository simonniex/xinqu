package com.example.student.presentation.virtue.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.core.other.util.internet.Message
import com.example.student.R
import com.example.student.domain.model.VirtueData
import com.example.student.presentation.virtue.detail.viewmodel.VirtueDialogViewModel
import com.example.core.data.model.NewsItem
import com.example.core.data.model.TextItem
import com.example.core.other.util.internet.fetchNews
import com.example.core.other.util.internet.fetchText
import com.example.core.other.util.internet.fetchText1
import com.example.core.state.userState
import com.example.student.domain.model.VirtueTeacher
import com.example.student.presentation.virtue.detail.viewmodel.VirtueListenViewModel
import java.time.format.DateTimeFormatter

@Destination("student_virtue_heart_route")
@Composable
fun MentalHealth(navController: NavHostController) {
    val context = LocalContext.current
    val rowList = listOf<VirtueData>(
        VirtueData(icon = R.drawable.ic_virtue_talk, title = "心灵对话"),
        VirtueData(icon = R.drawable.ic_virtue_hear, title = "倾听陪伴"),
        VirtueData(icon = R.drawable.ic_virtue_teach, title = "心理辅导"),
        VirtueData(icon = R.drawable.ic_virtue_test, title = "心理测试")
    )
    val articles = listOf(
        ArticleData(
            title = "做一个心理健康的人",
            summary = "做一个心理健康的人，不仅要身体的健康，而且也要心理健康。心理健康是现代人的健康不可分割的重要方面。",
            imageRes = R.drawable.pic_virtue_news1, // 替换为实际的图片资源ID
            url = "https://mp.weixin.qq.com/s/ovyN4jVXD2OQwPBP8AfSjw"
        ),
        ArticleData(
            title = "做回真正的自己",
            summary = "我们的自卑与防卫心理，是戴上面具的一个重要原因。面对真实的自己是很难的，正视缺点、自我批评更是困难的，人们更倾向于承认自己的优点，而否认自己的缺点。\n",
            imageRes = R.drawable.pic_virtue_news2, // 替换为实际的图片资源ID
            url = "https://mp.weixin.qq.com/s/WP7yOejjibKiyAP6dcySYA"
        ),
        ArticleData(
            title = "与压力和解，与快乐为友",
            summary = "每个人或多或少都会有些焦虑情绪，有些焦虑症状不明显，难以觉察。美国“积极的力量”网站总结了6种隐性表现，如果你有以下情况，就要关注心理健康了。",
            imageRes = R.drawable.pic_virtue_news3, // 替换为实际的图片资源ID
            url = "https://mp.weixin.qq.com/s/ddtDz6lL5cJ7Uyp-GfTByw"
        ),
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background) // 设置背景颜色
        .statusBarsPadding()
    ) {
        BackLine(navController, "心理健康")

        androidx.compose.material.Text("功能", modifier = Modifier.padding(16.dp))

        // 包裹 LazyRow 的卡片
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(rowList) { item ->
                    // 使用实际图标和标题
                    IconWithTitle(
                        icon = painterResource(item.icon),
                        title = item.title,
                        navController
                    )
                }
            }
        }

        androidx.compose.material.Text("文章", modifier = Modifier.padding(16.dp))

        LazyColumn(
            modifier = Modifier .padding(16.dp).fillMaxWidth()
        ){
            items(articles) { article ->
                ArticleItem(article = article) { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    // 使用 Intent 打开 URL
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun IconWithTitle(icon: Painter, title: String,navController: NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(8.dp)
            .widthIn(max = 100.dp)
            .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 圆形背景的 Box
        Box(
            modifier = Modifier
                .clickable {
                    if (title=="心灵对话"){
                        navController.navigate("student_virtue_heart_Dialog_route")
                    }else if (title == "倾听陪伴"){
                        navController.navigate("student_virtue_heart_Listen_route")
                    }else if (title == "心理辅导"){
                        navController.navigate("student_virtue_heart_counsel_route")
                    }else{
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/ch/%E4%BA%BA%E6%A0%BC%E6%B5%8B%E8%AF%95"))
                        // 使用 Intent 打开 URL
                        context.startActivity(intent)
                    }
                }
                .size(56.dp) // 设置大小，稍大于图标
                .clip(CircleShape) // 圆形裁剪
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)), // 半透明背景
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // 自然间距

        Text(text = title, textAlign = TextAlign.Center)
    }
}


@Composable
fun ArticleItem(article: ArticleData, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(article.url) }, // 点击卡片时调用 onClick
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // 左侧图片
            Image(
                painter = painterResource(article.imageRes), // 假设你有图片资源
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp)) // 图片与文本间距

            // 右侧文本
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp)) // 标题与内容间距
                Text(
                    text = article.summary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


// ArticleData 数据类
data class ArticleData(
    val title: String,
    val summary: String,
    val imageRes: Int, // 图像资源ID
    val url: String // 文章链接
)



@Destination("student_virtue_heart_Dialog_route")
@Composable
fun VirtueDialogCard(navController: NavHostController, viewModel: VirtueDialogViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        BackLine(navController, "心灵对话")

        // 使用 Box 包裹 LazyColumn 和 Row
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp) // 为 Row 保留足够的空间
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp), // 保留空间给 Row
                reverseLayout = false
            ) {
                items(viewModel.messages) { message ->
                    MessageBubble(message)
                }
            }

            // 确保 Row 始终位于 Box 的底部
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart) // 将 Row 对齐到底部
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewModel.inputText,
                    onValueChange = { viewModel.onInputTextChanged(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("输入消息") }
                )
                androidx.compose.material3.Button(
                    onClick = {
                        viewModel.postMessage()
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("发送")
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start =  if (message.role=="assistant") 8.dp else 0.dp)
            .padding(end =  if (message.role=="assistant") 0.dp else 8.dp)
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
            Text(
                text = message.content,
                color = if (message.role == "assistant") Color.Black else Color.White,
                fontSize = 16.sp
            )
        }
    }
}


@Destination("student_virtue_heart_Listen_route")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VirtueListenCard(navController: NavHostController,viewModel: VirtueListenViewModel = hiltViewModel()) {
    // 使用 remember 来保存文本项列表
    // 使用 remember 来保存文本项列表
    val textsItems = remember { mutableStateOf<List<TextItem>>(emptyList()) }

    // 使用 remember 来保存当前的 fetch 方式
    val isSearching = remember { mutableStateOf(false) }

    // 根据 fetchType 来调用不同的数据获取函数
    LaunchedEffect(isSearching.value) {
        if (isSearching.value) {
            try {
                textsItems.value = fetchText1(viewModel.myCode) // 搜索用户名
                viewModel.onMyCodeChanged("") // 搜索成功后清空输入框
            } catch (e: Exception) {
                Log.e("FetchTextError", "Error fetching text items", e)
            }
        } else {
            textsItems.value = fetchText() // 默认获取方式
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        BackLine(navController,"倾听陪伴")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp) // 为 Row 保留足够的空间
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp)
            ) {
                item{
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = viewModel.myCode,
                                onValueChange = {
                                    viewModel.onMyCodeChanged(it) // 监听变化
                                },
                                placeholder = { Text("搜索用户名") },
                                modifier = Modifier.background(Color.Transparent)
                            )
                            Button(onClick = {
                                isSearching.value = true
                            }) {
                                Text("搜索")
                            }
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
                items(textsItems.value) { item ->
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp)),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = item.text,
                            style = androidx.compose.material.MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                        Text(
                            text = item.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            style = androidx.compose.material.MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                        Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 将 Row 对齐到底部
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = viewModel.textFieldValue,
                    onValueChange = { viewModel.onInputTextChanged(it) },
                    placeholder = { Text("输入文本") }
                )
                Button(onClick = {
                    viewModel.postText {
                        isSearching.value = viewModel.myCode.isNotEmpty() // 更新搜索状态
                    }
                }) {
                    Text("发送")
                }
            }
        }
    }
}




@Destination("student_virtue_heart_counsel_route")
@Composable
fun VirtueCounsellingCard(navController: NavHostController){
    // 假数据
    val teachers = listOf(
        VirtueTeacher(
            name = "张老师",
            phoneNumber = "12311111234",
            imageRes = R.drawable.ic_virtue_teach1 // 本地资源图片
        ),
        VirtueTeacher(
            name = "李老师",
            phoneNumber = "12322221234",
            imageRes = R.drawable.ic_virtue_teach2
        ),
        VirtueTeacher(
            name = "王老师",
            phoneNumber = "12333331234",
            imageRes = R.drawable.ic_virtue_teach3
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            BackLine(navController,"心理辅导")
        }
        items(teachers) { teacher ->
            TeacherItem(teacher)
        }
    }
}
@Composable
fun TeacherItem(teacher: VirtueTeacher) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // 头像
        Image(
            painter = painterResource(id = teacher.imageRes),
            contentDescription = "头像",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 姓名和电话
        Column {
            Text(
                text = teacher.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = teacher.phoneNumber,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
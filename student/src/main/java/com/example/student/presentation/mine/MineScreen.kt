package com.example.student.presentation.mine

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.core.other.util.compressAndCacheAvatar
import com.example.core.other.util.toBitmap
import com.example.core.state.userState
import com.example.student.R
import com.example.student.imageIcon
import java.io.File
import java.io.FileOutputStream

@Composable
fun MineScreen(viewModel: MineViewModel= hiltViewModel()) {
    // 默认头像
    val defaultPainter: Painter = painterResource(R.drawable.img)
    val mineState by viewModel.state.collectAsState()
    val avatarUri = remember { mutableStateOf<String?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val username = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val school = remember { mutableStateOf("") }
    val studentId = remember { mutableStateOf("") }
    val club = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val great = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    println(imageIcon +"聂鑫")

    LaunchedEffect(Unit) {
        username.value = userState.getUsernameId().toString()
        phone.value = userState.getPhone().toString()
        school.value = userState.getSchool().toString()
        studentId.value = userState.getStudentId().toString()
        club.value = userState.getClub().toString()
        email.value = userState.getEmail().toString()
        great.value = userState.getGreat().toString()
        password.value = userState.getPassword().toString()
    }
    println("$imageIcon"+"聂鑫1111111")

    println(username.value +"111111")

    val cachedAvatarUri = File(context.cacheDir, "${username.value}_avatar.png").toUri()
    // 创建 ActivityResultLauncher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                // 处理选中的图片 URI，例如进行压缩和缓存
                compressAndCacheAvatar(context, uri,username.value)

                val studentMap = mapOf(
                    "type" to "student",
                    "phone" to (phone ?: ""), // 替换为实际数据，提供默认值
                    "username" to (username ?: ""), // 使用 ViewModel 获取用户名
                    "password" to (password ?: ""), // 替换为实际密码
                    "school" to (school ?: ""), // 替换为实际学校
                    "studentid" to (studentId ?: ""),
                    "club" to (club ?: ""),
                    "email" to (email ?: ""), // 替换为实际邮箱
                    "great" to (great ?: "") // 提供默认值
                )
                // 使用 updateUserData 更新数据
                viewModel.updateUserData(uri, studentMap,context)
            }
        }
    }


        Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 头像区域
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                    )
                        AsyncImage(
                            model = cachedAvatarUri,
                            contentDescription = "Profile Image",
                            modifier = Modifier           .size(90.dp)
                                .clip(CircleShape)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                        addCategory(Intent.CATEGORY_OPENABLE)
                                        type = "image/*"
                                    }
                                    imagePickerLauncher.launch(intent)
                                },
                            contentScale = ContentScale.Crop
                        )
                }

                Text(
                    text = username.value ?: "用户名未设置",
                    style = MaterialTheme.typography.bodyMedium, // 选择合适的样式
                )
            }

        // 选项卡栏
        MineTabRow(){
            StudentMyPlan()
        }
    }
}


@Composable
fun MineTabRow(
    content: @Composable () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("我的评论", "帮助中心","工具箱")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 可滑动的选项卡栏
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = tab,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun StudentMyPlan(){
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "niexin - 1+1等于几？",
                    color = Color.Black,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "2024.9.26",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "niexin - 2+2等于几？",
                    color = Color.Black,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "2024.9.26",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

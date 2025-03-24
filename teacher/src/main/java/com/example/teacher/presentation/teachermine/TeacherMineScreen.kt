package com.example.teacher.presentation.teachermine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.teacher.R

@Composable
fun TeacherMineScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.room1),
            contentDescription = "beij",
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.White),
                        startY = 0f,
                        endY = 800f
                    ))
                .padding(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(100.dp).background(Color.Transparent))
                ProfileCard()
            }
            item {
                CommonCard(navController)
            }
            item {
                MoreCard(navController)
                Spacer(modifier = Modifier.height(20.dp).background(Color.Transparent))
            }
        }
    }
}

@Composable
fun ProfileCard() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = com.example.teacher.R.drawable.room1),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "User Name", style = MaterialTheme.typography.h6)
                Text(text = "User Position", style = MaterialTheme.typography.body2)
            }
        }
}

@Composable
fun CommonCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(text = "常用功能", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                FeatureItem(imageRes = com.example.teacher.R.drawable.ic_teacher_mine_service1, label = "我的社团",navController)
                FeatureItem(imageRes = com.example.teacher.R.drawable.ic_teacher_mine_service2, label = "二维码生成",navController)
                FeatureItem(imageRes = com.example.teacher.R.drawable.ic_teacher_mine_service3, label = "我的行程",navController)
            }
        }
    }
}

@Composable
fun MoreCard(navController: NavHostController) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        ){
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(8.dp)) {
                    androidx.compose.material.Icon(
                        painter = painterResource(com.example.teacher.R.drawable.edit_document_24px),  // 使用默认的搜索图标
                        contentDescription = "opinion",
                        tint = Color.Black
                    )
                    Text(text = "意见反馈", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.weight(1f))
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,  // 使用默认的搜索图标
                        contentDescription = "ArrowForward",
                        tint = Color.Black, modifier = Modifier.clickable {
                            navController.navigate("feedback_route")
                        }
                    )
                }
            Spacer(modifier = Modifier.height(32.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(8.dp)) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Help,  // 使用默认的搜索图标
                        contentDescription = "help",
                        tint = Color.Black
                    )
                    Text(text = "关于心驱", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.weight(1f))
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,  // 使用默认的搜索图标
                        contentDescription = "ArrowForward",
                        tint = Color.Black, modifier = Modifier.clickable {
                            navController.navigate("about_us")
                        }
                    )
                }
            Spacer(modifier = Modifier.height(32.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(8.dp)) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.Settings,  // 使用默认的搜索图标
                        contentDescription = "setting",
                        tint = Color.Black
                    )
                    Text(text = "设置", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.weight(1f))
                    androidx.compose.material.Icon(
                        imageVector = Icons.Filled.ArrowForwardIos,  // 使用默认的搜索图标
                        contentDescription = "ArrowForward",
                        tint = Color.Black, modifier = Modifier.clickable {
                            navController.navigate("Setting_route")
                        }
                    )
                }
        }
    }
}

@Composable
fun FeatureItem(imageRes: Int, label: String,navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp).clickable {
            if (label=="二维码生成"){
                navController.navigate("teacher_mine_qr")
            }else if (label == "我的社团"){
                navController.navigate("teacher_mine_st")
            }else if (label == "我的行程"){
                navController.navigate("teacher_mine_xc")
            }else{
                println("哈哈哈")
            }
        }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, style = MaterialTheme.typography.body2)
    }
}

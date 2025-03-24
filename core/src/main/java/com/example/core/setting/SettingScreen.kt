package com.example.core.setting

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.core.other.util.getTotalCacheSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.core.nav.Navigator
import com.example.core.other.util.clearCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.ResourceBundle.clearCache

@Destination("Setting_route")
@Composable
fun SettingScreen(navController: NavHostController,navigator: Navigator) {

    val context = LocalContext.current

    var cacheSize by remember { mutableStateOf("计算中...") }
    var showDialog by remember { mutableStateOf(false) }

    // 计算缓存大小
    LaunchedEffect(Unit) {
        cacheSize = getTotalCacheSizeAsync(context)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackLine(navController,"设置")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 账号设置
            Text(text = "账号")
            Spacer(modifier = Modifier.height(8.dp))

            // 账号按钮
            Button(
                onClick = {
                    navController.navigate("core_setting_user")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 4.dp, horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("账号与安全", color = Color.Black, style = MaterialTheme.typography.h6)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 通用设置
            Text(text = "通用")
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("core_setting_permission")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 4.dp, horizontal = 20.dp)
                ,
                colors = ButtonDefaults.buttonColors(Color.White)
                ,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("权限管理", color = Color.Black, style = MaterialTheme.typography.h6)
            }

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 4.dp, horizontal = 20.dp)
                ,
                colors = ButtonDefaults.buttonColors(Color.White)
                ,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("清除缓存", color = Color.Black, style = MaterialTheme.typography.h6)

            }

            Spacer(modifier = Modifier.height(16.dp))

            // 退出登录按钮
            Button(
                onClick = {
                    navigator.navigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 4.dp, horizontal = 20.dp)
                ,
                colors = ButtonDefaults.buttonColors(Color.White)
                ,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("退出登录", color = Color.Black, style = MaterialTheme.typography.h6)
            }
        }

    }


    // AlertDialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("缓存大小:") },
            text = { Text("$cacheSize\n\n你确定要清除这些缓存?") },
            confirmButton = {
                TextButton(onClick = {
                    clearCacheAsync(context)
                    showDialog = false // 关闭对话框
                }) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("取消")
                }
            }
        )
    }
}


// 异步获取缓存大小
private suspend fun getTotalCacheSizeAsync(context: Context): String {
    return withContext(Dispatchers.IO) {
        getTotalCacheSize(context)
    }
}

// 异步清除缓存
private fun clearCacheAsync(context: Context) {
    // 使用协程来执行 I/O 操作
    CoroutineScope(Dispatchers.IO).launch {
        clearCache(context)
    }
}
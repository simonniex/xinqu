package com.example.softcup.presentation.start

import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.nav.Destination
import com.example.core.nav.Navigator
import com.example.softcup.R

@Destination("start")
@Composable
fun StartScreen(navigator: Navigator) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 底部实蓝色背景
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        )

        // 背景图片并添加虚化效果（上半部分）
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.start2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .blur(16.dp),
                contentScale = ContentScale.Crop
            )

// 渐变色覆盖层
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0xff00a2e8)),
                            startY = 0f,
                            endY = 1300f // 调整渐变高度以符合需求
                        )
                    )
            )

        }

// 按钮区域
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {

            // 图片和文字
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_login2),
                    contentDescription = null,
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_login3),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 登录按钮
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(70.dp)
                        .padding(10.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            navigator.navigateTo("login", true)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "登录",
                            color = Color(0xff00a2e8),
                            style = TextStyle(fontSize = 24.sp) // 设置字体大小为24sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(1.dp))

                // 注册按钮
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(70.dp)
                        .padding(10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                        .background(Color.Transparent, RoundedCornerShape(4.dp))
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                                  navigator.navigateTo("register",true)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "注册",
                            color = Color.White,
                            style = TextStyle(fontSize = 24.sp) // 设置字体大小为24sp
                        )
                    }
                }
            }
        }

    }
}


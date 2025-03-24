package com.example.softcup.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softcup.domain.model.TopAppBarStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.core.view.WindowCompat
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimonTopAppBar(
    style: TopAppBarStyle,
    selectedCity: StateFlow<String>,  // StateFlow<String> 类型
    onMenuClick: () -> Unit,
    onCityClick: () -> Unit,
    selectedTabIndex: StateFlow<Int>,  // StateFlow<Int> 类型
    onTabSelected: (Int) -> Unit
) {
    val city by selectedCity.collectAsState() // 将 StateFlow<String> 转换为 String
    val tabIndex by selectedTabIndex.collectAsState() // 将 StateFlow<Int> 转换为 Int

    var isVisible by remember { mutableStateOf(false) }
    when (style) {
        TopAppBarStyle.STYLE1 -> {
            androidx.compose.material3.TopAppBar(
                title = {
                Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment =  Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            AnimatedVisibility(
                                visible = isVisible,
                                enter = remember { slideInHorizontally(initialOffsetX = { -it / 3 }) + fadeIn() },
                                exit = remember { slideOutHorizontally(targetOffsetX = { it / 3 }) + fadeOut() }
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                    androidx.compose.material3.Text(
                                        text = city,
                                        style = androidx.compose.material.MaterialTheme.typography.body2,
                                        color = Color(0xff004ec4),
                                        modifier = Modifier.clickable {
                                            println("City clicked")
                                            onCityClick()
                                        }

                                    )
                                    Icon(
                                        painter = painterResource(com.example.softcup.R.drawable.ic_city_1),
                                        contentDescription = null,
                                        tint = Color(0xff004ec4),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "首页",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center, // Ensure the text is centered
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { isVisible = !isVisible }) {
                        Icon(Icons.Filled.Place, contentDescription = "Place", tint = Color(0xff004ec4))
                    }
                },
                actions = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Message", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(com.example.softcup.R.color.white),
                ),
                modifier = Modifier.shadow(15.dp)
            )
        }
        TopAppBarStyle.STYLE2 -> {
            androidx.compose.material3.TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center // 让整个 Row 居中
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(0.dp), // 将间距设为 0.dp 使文本贴在一起
                            modifier = Modifier.wrapContentWidth() // 根据内容大小调整宽度
                        ) {
                            val tabs = listOf("每日学习", "智能规划")
                            androidx.compose.material.TabRow(
                                modifier = Modifier.background(Color.Transparent),
                                selectedTabIndex = tabIndex,
                                indicator = { tabPositions ->  // 自定义指示器
                                    Box(Modifier.padding(0.dp)) // 空的 Box 确保没有下划线
                                },
                                divider = {},
                                backgroundColor = Color.Transparent,
                                contentColor = Color.Transparent
                            ) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = tabIndex == index,
                                        onClick = { onTabSelected(index) },
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(0.dp), // 去掉 Tab 的额外 padding
                                        text = {
                                            Text(
                                                text = title,
                                                color = if (tabIndex == index) Color.Black else Color.Gray,
                                                fontSize = if (tabIndex == index) 20.sp else 15.sp, // 控制字体大小
                                                textAlign = if (index == 0) TextAlign.End else TextAlign.Start,
                                                modifier = Modifier.fillMaxWidth()
                                            )


                                        }
                                    )
                                }

                            }
                        }
                    }

                },
                navigationIcon = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                actions = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(com.example.softcup.R.color.white),
                ),
                modifier = Modifier.shadow(10.dp)
            )
        }
        TopAppBarStyle.STYLE3 -> {
            androidx.compose.material3.TopAppBar(
                windowInsets = WindowInsets.statusBars ,
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "德育",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center, // Ensure the text is centered
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                actions = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(com.example.softcup.R.color.white),
                ),
                modifier = Modifier.shadow(15.dp)
            )
        }
        TopAppBarStyle.STYLE4 -> {
            androidx.compose.material3.TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "交流",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center, // Ensure the text is centered
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                actions = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(com.example.softcup.R.color.white),
                ),
                modifier = Modifier.shadow(15.dp))
        }
        TopAppBarStyle.STYLE5 -> {
            androidx.compose.material3.TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "我的",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center, // Ensure the text is centered
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                actions = {
                    Box(modifier = Modifier.size(48.dp)) {}
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(com.example.softcup.R.color.white),
                ),
                modifier = Modifier.shadow(15.dp))
        }
    }
}

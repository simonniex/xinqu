package com.example.student.presentation.home

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.amap.api.services.core.PoiItem
import com.example.component.AnimationEffect
import com.example.component.FloatingButtonMenu
import com.example.component.SearchBar
import com.example.core.state.userState
import com.example.student.MapActivity
import com.example.student.R
import com.example.student.domain.model.StudyRoom
import com.example.student.poiDetails
import com.example.student.poiItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val visibilityStates = remember { mutableStateMapOf<StudyRoom, Boolean>() }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var isSearchBarVisible by remember { mutableStateOf(false) }
    var accumulatedScrollY by remember { mutableStateOf(0f) }
    val scrollThreshold = 360f  // 调整这个值以改变触发的灵敏度
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0) {
                    accumulatedScrollY += available.y
                    if (accumulatedScrollY > scrollThreshold) {
                        isSearchBarVisible = true
                        accumulatedScrollY = 0f
                    } else if (accumulatedScrollY < -scrollThreshold) {
                        isSearchBarVisible = false
                        accumulatedScrollY = 0f
                    }
                }
                return Offset.Zero
            }
        }
    }

    if (isSearchBarVisible) {
        LaunchedEffect(Unit) {
            delay(3000L)  // 3秒延迟
            if (isSearchBarVisible && searchText.text.isEmpty()) {
                isSearchBarVisible = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xfff8f8fc))) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .nestedScroll(nestedScrollConnection),
            state = listState,
        ) {
            println(poiDetails)
            //搜索框
            item {
                AnimatedVisibility(
                    visible = isSearchBarVisible,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    SearchBar(
                        searchText = searchText,
                        onSearchTextChanged = { searchText = it }
                    )
                }
            }
            //卡片-学生端-课程
            item {
                Spacer(modifier = Modifier.height(16.dp))
                LargeCard(navController) // 假设LargeCard是一个@Composable函数
                Spacer(modifier = Modifier.height(16.dp))
            }
            //卡片-学生端-资源，卡片-学生端-实训
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SmallCard(modifier = Modifier.weight(1f),navController)
                    Spacer(modifier = Modifier.width(16.dp))
                    SmallCard1(modifier = Modifier.weight(1f),navController)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            //卡片-学生端-个人数据报告
            item {
                SmallCard2(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text("线上自习室")
                Spacer(modifier = Modifier.height(8.dp))
            }
            //横向列表-学生端-线上自习室
            item{
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    modifier = Modifier.height(180.dp),
                    horizontalArrangement =  Arrangement.spacedBy(16.dp)
                ) {
                    items(people.people) { person ->
                        // 确保 visibilityStates[person] 有明确的初始值
                        val isVisible = visibilityStates[person] ?: true

                        Column {
                            // PersonCard 显示
                            PersonCard(
                                modifier = Modifier.fillMaxWidth(),
                                person = person,
                                onClick = {
                                    // 切换 visibilityStates 的显示状态
                                    visibilityStates[person] = !(visibilityStates[person] ?: true)
                                },
                                isVisible = isVisible // 使用当前的 isVisible 状态
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // PersonDetailsCard 显示/隐藏
                            PersonDetailsCard(
                                person = person,
                                isVisible = !isVisible, // 反转显示状态
                                onClose = {
                                    // 在关闭时切换显示状态
                                    visibilityStates[person] = !(visibilityStates[person] ?: true)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }


                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text("附近自习室", modifier = Modifier.clickable {
                    val intent = Intent(context, MapActivity::class.java)
                    context.startActivity(intent)
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(poiDetails) { poiItem ->
                PoiItemView1(poiItem)
            }
//            items(OfficeLine.room) { index ->
//                StudyRoomCard(
//                    room = index,
//                    onClick = {
//
//                    },
//                    modifier = Modifier// Different heights for varying sizes
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//            }
//            item {
//                androidx.compose.material.Button(
//                    onClick = {
//                        // 创建 Intent 并启n动新的 Activity
////                        val intent = Intet(context, MapActivity::class.java)
//                        context.startActivity(intent)
//                    },
//                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//                ) {
//                    Text(text = "跳转到下一个Activity")
//                }
//            }
        }
        FloatingButtonMenu(
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            orientation = Orientation.Vertical,
            animationEffect = AnimationEffect.LIQUID_EXPANSION
        ) {
            menuItem(label = "提问", icon = Icons.Default.Favorite) {
            }
            menuItem(label = "提问", icon = Icons.Default.Call) {
            }
            menuItem(label = "提问", icon = Icons.Default.Home) {
            }
        }
    }
}


object people{
    val people = listOf(
        StudyRoom(
            imageRes = R.drawable.room2,
            name = "共享自习室",
            description = "人数：520",
            lianJiAn = "334-5172-3962"
        ),
        StudyRoom(
            imageRes = R.drawable.room1,
            name = "备考12月计算机一级",
            description = "人数：120",
            lianJiAn = "674-4869-7583"
        ),
        StudyRoom(
            imageRes = R.drawable.room3,
            name = "备考软考",
            description = "人数：200",
            lianJiAn = "689-5234-6072"
        )
    )
}


@Composable
fun LargeCard( navController: NavHostController) {

    Card(
        modifier = Modifier
            .clickable {
                navController.navigate("student_home_class_route")
            }
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF014ec4)),
        elevation = CardDefaults.cardElevation(4.dp),

        shape = shapes.medium
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Card content here
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "课程",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }

            // Draw two triangles at the bottom
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) // Height of the combined triangles
                    .align(Alignment.BottomStart)
            ) {
                val firstTriangleWidth = size.width / 3
                val secondTriangleWidth = 2 * size.width / 3
                val triangleColor = Color(0xFF0f5acf)

                drawPath(
                    path = Path().apply {
                        moveTo(0f, size.height) // 移动到中间底部
                        lineTo(firstTriangleWidth, size.height) // 上升到顶部左边
                        lineTo(firstTriangleWidth/2, size.height - 20.dp.toPx())
                        close()
                    },
                    color = triangleColor
                )


                // Draw second triangle
                drawPath(
                    path = Path().apply {
                        moveTo(firstTriangleWidth, size.height)
                        lineTo(firstTriangleWidth + secondTriangleWidth, size.height)
                        lineTo(firstTriangleWidth+secondTriangleWidth/2, size.height - 40.dp.toPx())
                        close()
                    },
                    color = triangleColor
                )
            }

            // Place image on the second triangle
            Image(
                painter = painterResource(id = R.drawable.course1), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-25).dp, y = 0.dp) // Adjust the position accordingly
            )
        }
    }
}
@Composable
fun SmallCard(modifier: Modifier = Modifier,navController: NavHostController) {
    Card(
        modifier = modifier
            .clickable {
                navController.navigate("student_home_resource_route")
            }
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE091C9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = shapes.medium
    ) {
        // Card content here
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            contentAlignment = Alignment.CenterStart

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "资源",
                    color = Color.White
                )
                Image(
                    alignment = Alignment.Center,
                    painter = painterResource(id = R.drawable.file), // Replace with your image resource
                    contentDescription = null
                )
            }
        }
    }
}
@Composable
fun SmallCard1(modifier: Modifier = Modifier,navController: NavHostController) {
    Card(
        modifier = modifier
            .clickable {
                navController.navigate("student_home_practise_route")
            }
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF40BBF3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = shapes.medium
    ) {
        // Card content here
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            contentAlignment = Alignment.CenterStart

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "实训",
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.train), // Replace with your image resource
                    contentDescription = null
                )
            }
        }
    }
}
@Composable
fun SmallCard2(navController: NavHostController){
    Card(
        modifier = Modifier
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF8252a9)) // 设为紫色
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左边显示文本
            Text(
                text = "查看个人数据报告",
                color = Color.White,
                fontSize = 16.sp
            )

            // 右边放置按钮
            Button(
                onClick = {
                          navController.navigate("student_home_chart_route")
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White) // 设为白色
            ) {
                Text("进入", color = Color.Black, fontSize = 14.sp) // 添加按钮上的文本，并设置为紫色
            }
        }
    }
}
@Composable
fun PersonCard(
    person: StudyRoom,
    onClick: () -> Unit,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    SharedTransitionLayout(isVisible = isVisible, modifier = modifier) {
        Card(
            modifier = Modifier
                .width(300.dp)
                .height(180.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp), // 设置阴影为0
            colors = CardDefaults.cardColors(containerColor = Color.Transparent) // 背景透明
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // 根据需要调整高度
            ) {
                // 背景图片
                Image(
                    painter = painterResource(id = person.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                // 叠加层：标题和人数
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = person.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        style = typography.titleMedium,
                        modifier = Modifier
                            .background(Color(0x80000000)) // 半透明背景以提高可读性
                            .padding(4.dp)
                    )
                    Text(
                        text = person.description,
                        color = Color.White,
                        fontSize = 14.sp,
                        style = typography.bodySmall,
                        modifier = Modifier
                            .background(Color(0x80000000)) // 半透明背景以提高可读性
                            .padding(4.dp)
                    )
                }
            }
        }


    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SharedTransitionLayout(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val animationDuration = 600

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = animationDuration, easing = EaseInOutEasing)) +
                expandVertically(animationSpec = tween(durationMillis = animationDuration, easing = EaseInOutEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = animationDuration, easing = EaseInOutEasing)) +
                shrinkVertically(animationSpec = tween(durationMillis = animationDuration, easing = EaseInOutEasing)),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.animateContentSize(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = EaseInOutEasing
                )
            )
        ) {
            content()
        }
    }
}

private val EaseInOutEasing = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)
@Composable
fun PersonDetailsCard(
    person: StudyRoom,
    isVisible: Boolean,
    onClose: () -> Unit
) {
    SharedTransitionLayout(isVisible = isVisible) {
        Card(
            modifier = Modifier
                .fillMaxWidth() // Adjust size to avoid filling entire screen
                .padding(16.dp)
                .clickable { onClose() },
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = person.name,
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleMedium // Use MaterialTheme for consistency
                )
                Text(
                    text = person.description,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "腾讯会议号:${person.lianJiAn}",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun PoiItemView1(poiItem: PoiItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        elevation = 1.dp, // 添加阴影效果
        shape = RoundedCornerShape(8.dp) // 圆角
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)

        ) {
            val imageUrl = poiItem.photos?.getOrNull(0)?.url // 获取第一个照片
            if (imageUrl != null) {
                // 使用列来布局文本
                // 显示图像
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "POI 图片",
                    modifier = Modifier
                        .size(150.dp) // 调整图像大小
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)), // 圆角
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = poiItem.title, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "地址: ${poiItem.snippet}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "联系电话: ${poiItem.tel}", style = MaterialTheme.typography.bodyMedium)
                }

            }
        }
    }
}

package com.example.teacher.presentation.teacherhome

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.component.ImprovedBadgeExample
import com.example.core.dao.StudentEntity
import com.example.core.data.model.NewsItem
import com.example.core.nav.Destination
import com.example.core.other.util.internet.fetchNews
import com.example.core.state.userState
import com.example.teacher.R
import com.example.teacher.util.CachedImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import java.io.File


data class BannerItem(
    val title: String,
    val imageRes: Int // Using resource ID instead of URL
)
val bannerItems = listOf(
    BannerItem("课程安排", R.drawable.ic_teacher_home_service1),
    BannerItem("资源发布", R.drawable.ic_teacher_home_service2),
    BannerItem("实训计划", R.drawable.ic_teacher_home_service3),
    BannerItem("智能助手", R.drawable.ic_teacher_home_service4)
)
val items = listOf(
    BannerItem("活动", R.drawable.pic_teacher_home2),
    BannerItem("资讯", R.drawable.pic_teacher_home3),
    BannerItem("比赛",R.drawable.pic_teacher_home1)
)
@Composable
fun TeacherHomeScreen(navController: NavHostController,viewModel:TeacherHomeViewModel= hiltViewModel()) {
    val newsItems = remember { mutableStateOf<List<NewsItem>>(emptyList()) }

    val studentList by viewModel.studentList.collectAsState()

    LaunchedEffect(Unit) {
        val school = userState.getSchool()
        if (school != null) {
            println(school)
            viewModel.getAllStudentsForTeacher(school)
        }
        newsItems.value = fetchNews()
    }

    val context = LocalContext.current
    var currentPage by remember { mutableStateOf(0) }
    // 创建滚动状态
    val scrollState = rememberLazyListState()

    // 计算 Row 的高度，用于判断何时显示固定的 Row
    val headerHeight = 80.dp
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    // 通过判断第一个可见项的位置来决定何时固定 Row
    val shouldStick = remember {
        derivedStateOf {
            val firstVisibleItem = scrollState.firstVisibleItemIndex
            val offset = scrollState.firstVisibleItemScrollOffset
            firstVisibleItem > 0 || (firstVisibleItem == 0 && offset > headerHeightPx / 2) // 提高灵敏度
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(R.drawable.animation) // 替换为你的 GIF 资源 ID
                .decoderFactory(ImageDecoderDecoder.Factory())
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "Animated Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (shouldStick.value){
            Row(
                modifier = Modifier
                    .zIndex(1f)// 确保 Row 在其他内容上方显示
                    .padding(bottom = 16.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "心驱",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 24.sp // 设置更大的字体大小
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically).weight(1f).padding(8.dp),
                    fontWeight =FontWeight.Bold
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterVertically).padding(8.dp)
                ){
                    ImprovedBadgeExample()
                }
            }
        }
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize().background(
                    brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.White),
                    startY = 0f,
                    endY = 1300f // 调整渐变高度以符合需求
                )).padding(horizontal = 4.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "心驱",
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontSize = 24.sp // 设置更大的字体大小
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically).weight(1f),
                            fontWeight =FontWeight.Bold
                        )
                        IconButton(
                            onClick = {},
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ){
                            ImprovedBadgeExample()
                        }
                    }

                }
                item {
                    SearchBar()
                }
                item {
                    InfiniteBannerCarousel(
                        items = items,
                        onItemClicked = { clickedItem ->
                            when (clickedItem.title) {
                                "活动" -> {
                                    navController.navigate("teacher_three_one_screen")
                                }
                                "资讯" -> {
                                    navController.navigate("teacher_three_two_screen")
                                }
                                "比赛" -> {
                                    navController.navigate("teacher_three_screen")
                                }
                                else -> {
                                    println("Unknown item clicked: ${clickedItem.title}")
                                }
                            }
                        },
                        onPageChanged = { page ->
                            currentPage = page
                            println("Current Page: $currentPage")
                        }
                    )
                }
                item {
                    Text(
                        text = "智能服务",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Gray
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        bannerItems.forEach { item ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                                    .clickable {
                                        if (item.title=="课程安排"){
                                            navController.navigate("teacher_course_detail")
                                        }else if(item.title=="资源发布"){
                                            navController.navigate("teacher_resource_detail")
                                        }else if (item.title=="实训计划"){
                                            navController.navigate("teacher_course_detail")
                                        }else{
                                            navController.navigate("teacher_assisant_detail")
                                        }
                                    }
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = painterResource(id = item.imageRes),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp) // 设置图标大小
                                        .background(Color.Transparent)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.title,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "我的学生",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Gray
                    )
                }
                item {
                    MyStudent(students = studentList,context)
                }
                item {
                    Text(
                        text = "新闻列表",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Gray
                    )
                }
                items(newsItems.value) { item ->
                    NewsItemView(newsItem = item)
                }
            }
    }
}
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InfiniteBannerCarousel(
    items: List<BannerItem>,
    onItemClicked: (BannerItem) -> Unit,
    onPageChanged: (Int) -> Unit, // Callback for page changes
    autoScrollInterval: Long = 3000L // 自动滚动的间隔时间，默认 3 秒
) {
    val pageCount = items.size
    val infinitePageCount = Int.MAX_VALUE
    val startPage = infinitePageCount / 2 - (infinitePageCount / 2 % pageCount)

    val pagerState = rememberPagerState(initialPage = startPage)
    var isUserInteracting by remember { mutableStateOf(false) }

    // 自动循环功能,处理
    LaunchedEffect(pagerState, isUserInteracting) {
        if (!isUserInteracting) {
            while (true) {
                delay(autoScrollInterval)
                try {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                } catch (e: CancellationException) {
                    // 如果动画被取消（例如用户手势中断），则继续下一轮循环
                    isUserInteracting = true // 标记用户正在交互
                } finally {
                    isUserInteracting = false // 恢复自动滚动
                }
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val page = pagerState.currentPage
        onPageChanged(page % pageCount) // 通知实际页面索引
    }

    HorizontalPager(
        state = pagerState,
        count = infinitePageCount, // 使用无限大页数
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .height(200.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isUserInteracting = true // 用户正在交互，暂停自动滚动
                        tryAwaitRelease() // 等待用户释放长按
                        isUserInteracting = false // 用户交互结束，恢复自动滚动
                    }
                )
            }
    ) { page ->
        val actualPage = page % pageCount

        BannerItemCard(
            item = items[actualPage],
            onClick = { onItemClicked(items[actualPage]) },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerItemCard(item: BannerItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    androidx.compose.material3.Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun MyStudent(students: List<StudentEntity>, context: Context) {
    println(students)

    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        items(students) { student ->
            // 生成每个学生的缓存头像 URI
            val cachedAvatarUri = File(context.cacheDir, "${student.username}_avatar.png").toUri()

            CoreValueCard(
                value = student.username,
                imageUrl = cachedAvatarUri.toString(), // 使用缓存头像 URI
                phone = student.phone
            )
        }
    }
}


@Composable
fun CoreValueCard(value: String, imageUrl: String,phone:String) {
    println(imageUrl)
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(8.dp)
            .background(Color(0xfff3f3f3))
            .size(250.dp, 40.dp), // 设定每个卡片的宽高
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xfff3f3f3)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 圆形头像
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 8.dp)
                    .background(Color.Transparent),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column( // 使用 Column 包裹姓名和电话
                modifier = Modifier.weight(1f) // 姓名和电话文本占满剩余空间
            ) {
                // 姓名
                Text(
                    text = value,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // 电话
                Text(
                    text = phone, // 使用 phone 变量
                    color = Color.Gray,
                    fontSize = 14.sp, // 可以选择稍微小一点的字体大小
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@Composable
fun NewsItemView(newsItem: NewsItem) {
    val context = LocalContext.current

    val imageCount = listOf(
        newsItem.thumbnail_pic_s,
        newsItem.thumbnail_pic_s02,
        newsItem.thumbnail_pic_s03
    ).count { it != null && it != "" }
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(androidx.compose.material.MaterialTheme.colors.surface, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { // 添加点击事件
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // 占据剩余空间
                .padding(end = 8.dp) // 给图片留出空间
        ) {
            Text(text = newsItem.title, style = androidx.compose.material.MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            // 图片处理
            when {
                // 检查是否有多张图片
                listOf(
                    newsItem.thumbnail_pic_s,
                    newsItem.thumbnail_pic_s02,
                    newsItem.thumbnail_pic_s03
                ).count { it != null && it != "" } > 1 -> {
                    Row {
                        listOf(
                            newsItem.thumbnail_pic_s,
                            newsItem.thumbnail_pic_s02,
                            newsItem.thumbnail_pic_s03
                        ).forEach { url ->
                            url?.let {
                                CachedImage(
                                    url = it,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            }
                        }
                    }
                }
                else -> Unit // 这里不做任何操作
            }
            if (imageCount == 1) {
                Text(
                    text = newsItem.author_name,
                    style = androidx.compose.material.MaterialTheme.typography.body2
                )
                Text(
                    text = newsItem.date,
                    style = androidx.compose.material.MaterialTheme.typography.caption
                )
            }else {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 作者名放在开头
                    Text(
                        text = newsItem.author_name,
                        style = androidx.compose.material.MaterialTheme.typography.body2,
                        modifier = Modifier.weight(1f) // 占据剩余空间
                    )

                    // 日期放在末尾
                    Text(
                        text = newsItem.date,
                        style = androidx.compose.material.MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.CenterVertically) // 垂直居中对齐
                    )
                }
            }
        }
        // 图片处理
        when {
            // 检查是否只有一张有效图片（thumbnail_pic_s 非空且另外两张图片为空）
            newsItem.thumbnail_pic_s != null && newsItem.thumbnail_pic_s != "" &&
                    (newsItem.thumbnail_pic_s02.isNullOrEmpty() && newsItem.thumbnail_pic_s03.isNullOrEmpty()) -> {
                CachedImage(
                    url = newsItem.thumbnail_pic_s ?: "",
                    modifier = Modifier
                        .size(width = 200.dp, height = 120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            // 没有有效图片
            else -> Unit // 这里不做任何操作
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("搜索", fontSize = 16.sp) },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp,Color.Transparent, RoundedCornerShape(8.dp))// 添加边框
                    .background(Color(0xfff7f8fa)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    focusedPlaceholderColor =  Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,  // 使用默认的搜索图标
                        contentDescription = "搜索",
                        tint = Color.Black // 可选：设置图标颜色
                    )
                }
            )
        }
    }
}
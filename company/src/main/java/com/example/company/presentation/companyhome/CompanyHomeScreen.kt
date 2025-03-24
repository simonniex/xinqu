package com.example.company.presentation.companyhome

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.company.R
import com.example.company.domain.model.BannerItem
import com.example.company.domain.model.NewsItem
import com.example.core.nav.Destination
import com.example.core.state.userState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


var username:String? = ""

val items = listOf(
    BannerItem(
        title = "工业互联网社团",
        imageRes = R.drawable.company_img,
        phone = "123-456-7890",
        time = "2024-09-01",
        people = 25,
        content = "致力于工业互联网的研究与发展，涵盖智能制造、大数据分析等领域。"
    ),
    BannerItem(
        title = "网络与信息安全实训室",
        imageRes = R.drawable.company_img_1,
        phone = "987-654-3210",
        time = "2024-09-02",
        people = 15,
        content = "提供网络安全实训环境，进行各种信息安全相关的课程与实训。"
    ),
    BannerItem(
        title = "程序设计与软件开发实训室",
        imageRes = R.drawable.company_img_2,
        phone = "555-123-4567",
        time = "2024-09-03",
        people = 30,
        content = "专注于程序设计和软件开发的实训，涵盖多种编程语言和开发工具。"
    )
)
val sampleNews = listOf(
    NewsItem(
        title = "学院召开2024秋季学期工作部署会",
        preview = "8月27日，学院召开2024秋季学期工作部署会。全体院领导出席会议，中层干部参加会议。会议由院长李桂萍主持。",
        imageUrl = R.drawable.company_newsitem_1,
        url = "https://www.jscst.edu.cn/35/f2/c45a79346/page.htm"
    ),
    NewsItem(
        title = "学院开展2024年暑期社会实践活动",
        preview = "2024暑假期间，学院持续开展2024年大学生暑期“三下乡”社会实践活动。",
        imageUrl = R.drawable.company_newsitem_2,
        url = "https://www.jscst.edu.cn/35/e1/c45a79329/page.htm"
    ),
    NewsItem(
        title = "学院承办徐州市化工医药行业职工安全生产劳动保护技能竞赛",
        preview = "为进一步提高化工医药企业全员安全知识水平，以更高标准和更严要求贯彻落实安全发展理念，切实维护职工的安康权益，8月6日，徐州市举行化工医药行业职工安全生产劳动保护技能竞赛，此次竞赛由徐州市总工会主办，我院工业安全与职业健康学院承办。",
        imageUrl = R.drawable.company_newsitem_3,
        url = "https://aqgc.jscst.edu.cn/35/a8/c513a79272/page.htm"
    )
)
@Destination("company_home_route")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyHomeScreen(navController: NavHostController){
    val context = LocalContext.current
    var currentPage by remember { mutableStateOf(0) }
    var isSearchVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        username =  userState.getUsernameId()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            item {//banner
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PracticalTrainBanner(
                        items = items,
                        onItemClicked = { clickedItem ->
                            navController.navigate("company_home_club_route/${clickedItem.title}/${clickedItem.imageRes}/${clickedItem.phone}/${clickedItem.time}/${clickedItem.people}/${clickedItem.content}"){
                                // 只清除栈中到 "company_home_route" 之前的页面
                                popUpTo("company_home_route") {
                                    inclusive = false // 保留 "company_home_route" 页面
                                }
                                // 防止重新创建实例
                                launchSingleTop = true
                            }
                        },
                        onPageChanged = { page ->
                            currentPage = page
                            println("Current Page: $currentPage")
                        }
                    )
                    IconButton(onClick = {
                        isSearchVisible = !isSearchVisible
                    },
                        modifier = Modifier.align(Alignment.TopEnd).padding(end = 30.dp, top = 30.dp).size(30.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f))) {
                        Icon(
                            painter = painterResource(R.drawable.company_search_icon),
                            contentDescription = "搜索",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
            item { //Row
                CompanyIdentify(navController)
            }
            item { //热门新闻
                androidx.compose.material3.Text(
                    text = "热门新闻",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Gray
                )
            }
            item { //Row->two card
                CompanyHotNews(context)
            }
            item { //校内新闻
                androidx.compose.material3.Text(
                    text = "校内新闻",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp),
                    color = Color.Gray
                )
            }
            items(sampleNews.size) { //Column->card
                NewsListItem(newsItem = sampleNews[it],context)
            }
            item {
                Spacer(modifier = Modifier.height(150.dp).background(Color.Transparent))
            }
        }
        // 半透明遮罩
        if (isSearchVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .background(Color.Black.copy(alpha = 0.5f)).clickable { isSearchVisible = false }
            )
        }
        // 搜索框的显示与隐藏
        AnimatedVisibility(
            visible = isSearchVisible,
            modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).zIndex(2f)//确保在遮罩之上
        ) {
            Row(
                modifier = Modifier
                    .padding( top = 70.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("搜索") },
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        focusedPlaceholderColor =  Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "搜索",
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }

    }

}

/*
*轮播图
* */
@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun PracticalTrainBanner(
    items: List<BannerItem>,
    onItemClicked: (BannerItem) -> Unit,
    onPageChanged: (Int) -> Unit, // Callback for page changes
    autoScrollInterval: Long = 3000L // 自动滚动的间隔时间，默认 3 秒
){
    val pageCount = items.size
    val infinitePageCount = Int.MAX_VALUE
    val startPage = infinitePageCount / 2 - (infinitePageCount / 2 % pageCount)

    val pagerState = com.google.accompanist.pager.rememberPagerState(initialPage = startPage)
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
            .height(250.dp)
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
                .fillMaxSize()
        )
    }
}

//教师和学生
@Composable
fun CompanyIdentify(
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.material.Card(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    navController.navigate("company_home_identity_route/教师")
                }
                .padding(end = 8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.company_teacher_icon), // 替换为实际图片资源
                    contentDescription = "图片1",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "教师",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        androidx.compose.material.Card(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    navController.navigate("company_home_identity_route/学生")
                }
                .padding(start = 8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.company_student_icon), // 替换为实际图片资源
                    contentDescription = "图片2",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "学生",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
//热门新闻
@Composable
fun CompanyHotNews(context:Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material.Card(
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth(),
                elevation = 4.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.company_hotnews_1), // 替换为实际图片资源
                    contentDescription = "图片1",
                    modifier = Modifier
                        .fillMaxSize().clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jscst.edu.cn/36/34/c45a79412/page.htm"))
                            context.startActivity(intent)
                        }
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop // 确保图片完全填充 Card
                )
            }
            Text(
                text = "学院举办党的二十届三中全会精神融入思政课教学研讨暨大中小学思政课集体备课会",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material.Card(
                modifier = Modifier
                    .height(220.dp)
                    .fillMaxWidth(),
                elevation = 4.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.company_hotnews_2), // 替换为实际图片资源
                    contentDescription = "图片1",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jscst.edu.cn/36/09/c45a79369/page.htm"))
                            context.startActivity(intent)
                        }
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop // 确保图片完全填充 Card
                )
            }
            Text(
                text = "学院召开2024年人才培养状态数据采集工作推进会",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/*
* 轮播图item
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerItemCard(item: BannerItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            androidx.compose.material3.Text(
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
fun NewsListItem(newsItem: NewsItem,context: Context) {
        androidx.compose.material.Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).clickable { // 添加点击事件
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
                context.startActivity(intent)
            },
            elevation = 4.dp
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                Image(
                    painter = painterResource(newsItem.imageUrl),
                    contentDescription = newsItem.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = newsItem.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = newsItem.preview,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
}
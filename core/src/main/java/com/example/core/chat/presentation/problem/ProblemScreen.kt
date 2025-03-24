package com.example.core.chat.presentation.problem

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.component.AnimationEffect
import com.example.component.FloatingButtonMenu
import com.example.core.R
import kotlinx.coroutines.launch
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.component.ImprovedBadgeExample
import com.example.core.data.model.Employee
import com.example.core.data.model.GoodStudent
import com.example.core.data.model.TalkListItem
import com.example.core.state.UserState
import com.example.core.state.userState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProblemScreen1() {
    val items = remember {
        mutableStateListOf(
            "高数:1+1=？",
            "英语: Another example of a long text that will wrap to the next line if it is too long to fit in a single line.",
            "安卓: compose是什么",
            "化学: 高锰酸钾公式"
        )
    }
    val (newItem, setNewItem) = remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { true }  // 确保状态改变时总是允许的
    )

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        SearchAndFilterList(
            items = items,
            onFilterClicked = { /* TODO: Handle filter click */ }
        )

    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                androidx.compose.material.Text(text = "输入问题", style =androidx.compose.material.MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                androidx.compose.material.TextField(
                    value = newItem,
                    onValueChange = setNewItem,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { androidx.compose.material.Text("问题") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        items.add(newItem)
                        coroutineScope.launch {
                            bottomSheetState.hide()  // 提交后隐藏底部弹窗
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("提交")
                }
            }
        }
    ) {
    }
}


//搜索栏
@Composable
fun SearchAndFilterList(
    items: List<String>,
    onFilterClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Icon(
                painter = painterResource(id = R.drawable.sift), // 替换为你的筛选图标资源
                contentDescription = "Filter",
                modifier = Modifier.size(24.dp)
            )
            androidx.compose.material.Text("筛选")
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    androidx.compose.material.Text(
                        text = item,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        maxLines = Int.MAX_VALUE,
                        overflow = TextOverflow.Visible
                    )
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}

data class BannerItem(
    val title: String,
    val imageRes: Int // Using resource ID instead of URL
)

val students = listOf(
    GoodStudent(R.drawable.icon_student,"张三"),
    GoodStudent(R.drawable.icon_student1,"李四"),
    GoodStudent(R.drawable.icon_student2,"王五"),
    GoodStudent(R.drawable.icon_student3,"刘六")
)

val employees = listOf(
    Employee(R.drawable.icon_company, "安卓"),
    Employee(R.drawable.icon_company1, "ios"),
    Employee(R.drawable.icon_company2,"harmony"),
    Employee(R.drawable.icon_company3, "flutter")
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerCarousel2(
    items: List<BannerItem>,
    onItemClicked: (BannerItem) -> Unit,
    onPageChanged: (Int) -> Unit // Callback for page changes
) {
    val pageCount = items.size

    // Ensure there's at least one item to display
    if (pageCount == 0) return

    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(pagerState.currentPage) {
        val page = pagerState.currentPage
        onPageChanged(page % pageCount) // Notify about the actual page index
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) {
            HorizontalPager(
                state = pagerState,
                 pageCount = pageCount, // Use actual page count
                contentPadding = PaddingValues(end = 64.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { page ->
                // Directly using the page index since count matches items size
                if (page in items.indices) {
                    BannerItemCard1(
                        item = items[page],
                        onClick = { onItemClicked(items[page]) },
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerItemCard1(item: BannerItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
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
                color = Color.Black,
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProblemScreen(username:String,navController: NavHostController,viewModel: ProblemViewModel= hiltViewModel()) {

    val (newItem, setNewItem) = remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { true }  // 确保状态改变时总是允许的
    )

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.getData()
    }
    println(username)

    val items = listOf(
        BannerItem("安卓开发工程师", R.drawable.pic_android),
        BannerItem("嵌入式开发工程师", R.drawable.pic_qianrus),
        BannerItem("web开发实习生", R.drawable.pic_qianduan),
        BannerItem("计算机软件安装工程师", R.drawable.pic_ruanjian)
    )

    // 搜索框的状态
    var searchText by remember { mutableStateOf("") }
    val talks by viewModel.talkList.collectAsState() // 使用 collectAsState 观察变化
    var isRefreshing by remember { mutableStateOf(false) } // 刷新状态
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            viewModel.getData()
            isRefreshing = false // 刷新结束
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .zIndex(1f)// 确保 Row 在其他内容上方显示
                    .padding(bottom = 16.dp)
                    .background(Color.Transparent)
                    .fillMaxWidth()
            ) {
                // 搜索框
                Card(
                    shape = RoundedCornerShape(100.dp),
                    elevation = 0.dp,
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min =62.dp) // 使用 heightIn，设置card最小高度
                        .padding(8.dp)

                ) {
                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 18.sp),
                        colors =  TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent
                        ),
                        placeholder = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                androidx.compose.material.Text("请输入搜索内容")
                            }
                        },
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xfff7f8fa))
                            .height(60.dp) // 强制设置 TextField 高度
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        trailingIcon = {
                            androidx.compose.material.Icon(
                                imageVector = Icons.Default.PhotoCamera,  // 使用默认的搜索图标
                                contentDescription = "照片",
                                tint = Color.Black // 可选：设置图标颜色
                            )
                        }
                    )
                }
            }



            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item{
                    Spacer(modifier = Modifier.height(62.dp))
                }
                item {
                    Text(
                        text = "热门岗位",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black
                    )
                }
                item {
                    BannerCarousel2(items,{},{})
                }
                item {
                    Text(
                        text = "优秀毕业生",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(students) { student ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = student.imageRes),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(Color.White)
                                        .clip(CircleShape).background(Color.Transparent),
                                    contentScale = ContentScale.Crop
                                )
                                Text(text = student.name, color = Color.Black, fontSize = 14.sp)
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "高薪职员",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(employees) { employee ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = employee.imageRes),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape).background(Color.Transparent),
                                    contentScale = ContentScale.Crop
                                )
                                Text(text = employee.name, color = Color.Black, fontSize = 14.sp)
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "交流广场",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.Black
                    )
                }
                items(talks) { talk ->
                    username?.let { JobRequirementCard(talk,navController, it) }
                }
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }

            FloatingButtonMenu(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                orientation = Orientation.Vertical,
                animationEffect = AnimationEffect.TOP
            ) {
                menuItem(label = "提问", icon = Icons.Default.Favorite) {
                    coroutineScope.launch {
                        bottomSheetState.show()  // 展示底部弹窗
                    }
                }
            }
        }
    }
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                androidx.compose.material.Text(text = "输入问题", style =androidx.compose.material.MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                androidx.compose.material.TextField(
                    value = newItem,
                    onValueChange = setNewItem,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { androidx.compose.material.Text("问题") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.addList(newItem,username)
                        coroutineScope.launch {
                            bottomSheetState.hide()  // 提交后隐藏底部弹窗
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("提交")
                }
            }
        }
    ) {
    }
}
@Composable
fun JobRequirementCard(job: TalkListItem,navController: NavHostController,username:String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val fixedUsername = "niexin"  // 固定的用户名

                navController.navigate("chat_screen/$username")

            }
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
                text = "${job.title} - ${job.salary}",
                color = Color.Black,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = job.description,
                color = Color.Gray,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

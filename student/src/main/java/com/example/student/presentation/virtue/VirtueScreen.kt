package com.example.student.presentation.virtue

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.student.R
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BannerCarousel1(
    items: List<BannerItem>,
    onItemClicked: (BannerItem) -> Unit,
    onPageChanged: (Int) -> Unit // Callback for page changes
) {
    val pageCount = items.size

//    val infinitePageCount = Int.MAX_VALUE
//    val startPage = infinitePageCount / 2 - (infinitePageCount / 2 % pageCount)

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
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White)
        ) {
            HorizontalPager(
                state = pagerState,
                count = pageCount, // Use actual page count
                contentPadding = PaddingValues(end = 64.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { page ->
                val actualPage = page % pageCount

                BannerItemCard(
                    item = items[actualPage],
                    onClick = { onItemClicked(items[actualPage]) },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerItemCard(item: BannerItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
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


data class BannerItem(
    val title: String,
    val imageRes: Int // Using resource ID instead of URL
)

@Composable
fun VirtueScreen(navController:NavHostController) {
    val daily = remember { mutableStateOf("") }

//    LaunchedEffect(Unit) {
//       daily.value = fetchDaily()
//    }


    val items = listOf(
        BannerItem("心理健康", R.drawable.virtue_pic1),
        BannerItem("职业道德", R.drawable.virtue_pic3),
        BannerItem("党政教育", R.drawable.virtue_pic4),
        BannerItem("人文素养", R.drawable.virtue_pic2)
    )

    var currentPage by remember { mutableStateOf(0) }
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xfff8f8fc))
    ) {
        item {
                androidx.compose.material.Text("每日一句", modifier = Modifier.padding(16.dp))
        }
        item {
            val backgroundImage = rememberImagePainter(R.drawable.virtue_day)
            CardA(
                backgroundImage = backgroundImage,
                text = daily.value,
                onClick = {}
            )
        }
        item {
            androidx.compose.material.Text("道德教育", modifier = Modifier.padding(16.dp))
        }

        item {
            BannerCarousel1(
                items = items,
                onItemClicked = { clickedItem ->
                    println("Clicked on: ${clickedItem.title}")
                    if (clickedItem.title=="心理健康"){
                        navController.navigate("student_virtue_heart_route")
                    }else if (clickedItem.title =="职业道德"){
                        navController.navigate("student_virtue_z_route")
                    }else if (clickedItem.title == "党政教育"){
                        navController.navigate("student_virtue_ccp_route")
                    }else{
                        navController.navigate("student_virtue_people_route")
                    }
                },
                onPageChanged = { page ->
                    currentPage = page
                    println("Current Page: $currentPage")
                }
            )
        }
        item {
            androidx.compose.material.Text("社会主义核心价值观", modifier = Modifier.padding(16.dp))
        }
        item {
            CoreValuesGrid()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardA(
    backgroundImage: Painter,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Box {
            // 背景图
            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 半透明遮罩和文本
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 居中对齐
                    .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp) // 内边距
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun CoreValuesGrid() {
    // 社会主义核心价值观的十二个词
    val values = listOf(
        "富强", "民主", "文明", "和谐",
        "自由", "平等", "公正", "法治",
        "爱国", "敬业", "诚信", "友善"
    )

    // 创建 LazyHorizontalGrid 布局
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2), // 设置两行
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // 适当调整高度
    ) {
        items(values.size) { index ->
            CoreValueCard(value = values[index])
        }
    }
}

@Composable
fun CoreValueCard(value: String) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF00a2e8),Color.White)
    )
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(160.dp, 40.dp) // 设定每个卡片的宽高
            ,
        shape = RoundedCornerShape(12.dp), // 圆角
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(gradient)
        ) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
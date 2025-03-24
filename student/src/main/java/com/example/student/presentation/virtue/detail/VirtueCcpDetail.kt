package com.example.student.presentation.virtue.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import com.example.student.R
import com.example.student.domain.model.VirtueCcpData
import kotlinx.coroutines.launch


@Destination("student_virtue_ccp_route")
@Composable
fun VirtueCcpDetail(navController: NavHostController){
    val context = LocalContext.current

    // 轮播图假数据
    val carouselItems = listOf(
        R.drawable.pic_virtue_ccp1,
        R.drawable.pic_virtue_ccp2
    )
    // 列表假数据
    val listItems = listOf(
        VirtueCcpData(
            title = "学院召开学习贯彻习近平新时代中国特色社会主义思想主题教育总结会议",
            description = "9月1日，学院在云龙校区综合楼四楼第一学术报告厅召开学习贯彻习近平新时代中国特色社会主义思想主题教育总结会议",
            imageRes = R.drawable.pic_virtue_ccp3, // 本地图片资源
            url = "https://ztjyzt.jscst.edu.cn/20/64/c1711a73828/page.htm"
        ),
        VirtueCcpData(
            title = "学院党委班子成员指导二级单位党组织主题教育专题民主生活会",
            description = "根据中央文件精神和省委统一部署以及省应急管理厅党委具体要求，按照学院党委主题教育专题民主生活会工作安排",
            imageRes = R.drawable.pic_virtue_ccp4,
            url = "https://ztjyzt.jscst.edu.cn/20/82/c1711a73858/page.htm"
        ),
        VirtueCcpData(
            title = "学院召开党委领导班子主题教育专题民主生活会",
            description = "根据党中央关于在学习贯彻习近平新时代中国特色社会主义思想主题教育中开好专题民主生活会的通知要求，按照省委统一部署",
            imageRes = R.drawable.pic_virtue_ccp5,
            url = "https://ztjyzt.jscst.edu.cn/1e/b7/c1711a73399/page.htm"
        )
    )

    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        BackLine(navController,"党政教育")
        // 轮播图部分
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            carouselItems.forEach { imageRes ->
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight()
                        .width(300.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 列表部分
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                androidx.compose.material.Text("党政活动", modifier = Modifier.padding(16.dp))
            }
            items(listItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // 跳转到文章链接
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                            context.startActivity(intent)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 左边图片
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // 右边标题和简介
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black
                            )
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
package com.example.student.presentation.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.component.BackLine
import com.example.core.data.model.Video
import com.example.core.nav.Destination
import com.example.core.other.util.openUrl
import com.example.student.R
// 假数据


@Destination("student_home_resource_route")
@Composable
fun ResourceDetail(navController: NavHostController, viewModel: ResourceDetailViewModel = hiltViewModel()){

    val videos by viewModel.videos.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        BackLine(navController, "资源")
        VideoResourceList(videos = videos, onVideoClick = {
            openUrl(it,context)
        })
    }
}

@Composable
fun VideoResourceList(videos: List<Video>, onVideoClick: (String) -> Unit) {
    LazyColumn {
        items(videos) { video ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(200.dp)
                    .clip(RectangleShape)
                    .clickable { onVideoClick(video.name) } // 点击事件
            ) {
                // 加载网络图片
                Image(
                    painter = rememberImagePainter(video.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // 半透明的叠加层，显示视频名称
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = video.title,
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

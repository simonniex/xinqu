package com.example.student.presentation.virtue.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.student.domain.model.VirtueArticle

@Destination("student_virtue_z_route")
@Composable
fun VirtueZDetail(navController:NavHostController) {
    // 获取上下文以进行跳转
    val context = LocalContext.current

    // 假数据
    val articles = listOf(
        VirtueArticle(
            title = "遵守职业道德，提高职业准则",
            imageRes = R.drawable.pic_virtue_z1, // 本地图片资源
            url = "https://mp.weixin.qq.com/s/-YsHO0ZpHqUy4knOPe_-dA"
        ),
        VirtueArticle(
            title = "职业素养：四个要点",
            imageRes = R.drawable.pic_virtue_z2, // 本地图片资源
            url = "https://mp.weixin.qq.com/s/fPUSsq7YvFBlZ7sXpKz0uw"
        ),
        VirtueArticle(
            title = "优秀员工十大职业素养",
            imageRes = R.drawable.pic_virtue_z3, // 本地图片资源
            url = "https://mp.weixin.qq.com/s/iejYoslggK3BWq1VHgx6XQ"
        ),
        VirtueArticle(
            title = "如何提升员工的职业素养？其实不难！",
            imageRes = R.drawable.pic_virtue_z4, // 本地图片资源
            url = "https://mp.weixin.qq.com/s/P2DzLbup_54EvzX_iUlUag"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).padding(bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BackLine(navController,"职业道德")
        }
        items(articles) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // 跳转到文章链接
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                        context.startActivity(intent)
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 图片
                    Image(
                        painter = painterResource(id = article.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 文章标题
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
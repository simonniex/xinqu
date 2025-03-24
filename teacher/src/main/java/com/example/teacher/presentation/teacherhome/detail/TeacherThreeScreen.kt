package com.example.teacher.presentation.teacherhome.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.component.BackLine
import com.example.core.nav.Destination


@Destination("teacher_three_one_screen")
@Composable
fun TeacherOneScreen(navController: NavHostController) {
    val items = getFakeData1()
    Column {
        BackLine(navController,"活动")
        LazyColumnList(items = items)
    }
}

@Destination("teacher_three_two_screen")
@Composable
fun TeacherTwoScreen(navController: NavHostController) {
    val items = getFakeData2()
    Column {
        BackLine(navController,"比赛")
        LazyColumnList(items = items)
    }
}

@Destination("teacher_three_screen")
@Composable
fun TeacherFourScreen(navController: NavHostController) {
    val items = getFakeData3()
    Column {
        BackLine(navController,"资讯")
        LazyColumnList(items = items)
    }
}


@Composable
fun LazyColumnList(items: List<FakeData>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            ListItemCard(item)
        }
    }
}

@Composable
fun ListItemCard(item: FakeData) {
    val context = LocalContext.current

    androidx.compose.material.Card(
        modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                context.startActivity(intent)
            }
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp), // 小圆角
        elevation = 4.dp, // 降低阴影效果
        backgroundColor = Color.White // 设置卡片背景为白色
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // 增加间距
        ) {
            // 更大的上面图片
            Image(
                painter = rememberImagePainter(item.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth() // 图片宽度填满
                    .height(120.dp) // 调整图片高度
                    .clip(RoundedCornerShape(4.dp)), // 小圆角
                contentScale = ContentScale.Crop
            )

            // 标题
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // 内容预览
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}


data class FakeData(
    val title: String,
    val description: String,
    val imageUrl: Int,
    val url :String
)

fun getFakeData1(): List<FakeData> {
    return listOf(
        FakeData("月满中秋，情满校园——21级汽修新疆班中秋茶话会暨飞盘活动圆满举行", "在这个丹桂飘香、月圆人团圆的美好中秋佳节，汽车与交通安全学院开展了一场别开生面的“月满中秋，情满校园”茶话会及中秋飞盘活动，为21级汽修新疆班的同学们带来了别样的节日氛围。活动不仅加深了同学间的友谊，更让远离家乡的学子们感受到了家的温暖。", com.example.teacher.R.drawable.t1,"https://qcgc.jscst.edu.cn/37/27/c500a79655/page.htm"),
        FakeData("学生党支部开展“迎‘新’启航 满心关怀” 主题党日活动", "为确保2024级新生顺利入学，充分体现学院的温馨与关爱，汽车与交通安全学院学生党支部精心组织了《迎“新”启航 满心关怀》主题党日活动。党总支书记马学建亲自部署并亲临现场指导，旨在通过一系列贴心周到的服务，助力新生快速适应新环境，融入学院的大家庭。", com.example.teacher.R.drawable.t2,"https://qcgc.jscst.edu.cn/37/19/c497a79641/page.htm"),
        FakeData("马克思主义学院组织党的二十届三中全会融入思政课教学展示学习观摩活动", "为学习贯彻党的二十届三中全会精神，坚持用党的创新理论推动思政课建设高质量发展，实现将党的二十届三中全会精神与思政课教学的有机融合，9月6日下午，我院全体思政课教师在云龙校区404教室，集中观看由中宣部、教育部组织的全国高校思政课骨干教师学习贯彻三中全会精神专题研修班的教学展示活动。", com.example.teacher.R.drawable.t3,"https://szb.jscst.edu.cn/37/16/c1298a79638/page.htm")
    )
}

fun getFakeData2(): List<FakeData> {
    return listOf(
        FakeData("我院教师在省级教学能力比赛中荣获一等奖", "7月12日，在2024年江苏省职业院校教学能力比赛（高职组）决赛中，汽车与交通安全学院城市轨道交通教学团队（耿子康、王平、王子傲）凭借其卓越的教学设计......", com.example.teacher.R.drawable.t4,"https://qcgc.jscst.edu.cn/36/2e/c498a79406/page.htm"),
        FakeData("喜报：我院教学团队在2024年江苏省职业院校教学能力比赛中获得一等奖", "近日，2024年江苏省职业院校教学能力比赛落下帷幕。学校喜获一等奖6项，三等奖1项。一等奖获奖数创学校最好成绩，位列全省第一。", com.example.teacher.R.drawable.t5,"https://xxgc.jscst.edu.cn/36/26/c1481a79398/page.htm"),
        FakeData("马克思主义学院积极参加2024年校级微课教学比赛", "为进一步提升教师的专业技能，做好参加2024年江苏省高校微课教学比赛的准备，马克思主义学院根据《关于举办2024年江苏省高校微课教学比赛的通知》要求积极组织各位教师参赛。我院共有14位中青年教师提交微课参赛作品，他们立足学科特点，在认真解读新课标、教材的基础上，紧扣教学目标，精心设计了不同的微课教学主题，彰显出各自巧妙灵活的教学构思及新颖独特的教学风格。", com.example.teacher.R.drawable.t6,"https://szb.jscst.edu.cn/31/af/c1302a78255/page.htm")
    )
}

fun getFakeData3(): List<FakeData> {
    return listOf(
        FakeData("学院顺利举行2024年9月全国计算机等级考试", "9月21日，学院组织第74次全国计算机等级考试工作。本次考试设置了10个考场分3个批次进行，共1036名考生参加考试。考试期间，副院长吉智陪同巡视人员现场进行检查与督导。", com.example.teacher.R.drawable.t7,"https://www.jscst.edu.cn/37/34/c45a79668/page.htm"),
        FakeData("以“心”相迎 与“新”相遇——学院喜迎2024级新同学", "砺行逐梦迎新季，风帆再起展新程。9月19日，学院2024级新生如约而至。迎新标语将校园装点一新，随处可见的横幅、道旗、展板都在向新同学致以最热烈的欢迎。4700余名新同学从全国各地向苏安院奔赴而来，共启人生新征程。", com.example.teacher.R.drawable.t8,"https://www.jscst.edu.cn/37/00/c45a79616/page.htm"),
    )
}


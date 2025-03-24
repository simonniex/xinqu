package com.example.company.presentation.companysupport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.company.presentation.companyhome.username
import com.example.core.data.model.NewsItem
import com.example.core.nav.Destination
import com.example.core.state.userState

@Destination("company_support_route")
@Composable
fun CompanySupportScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            CustomTop()
        }
        item {//比赛
            androidx.compose.material3.Text(
                text = " ",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
                color = Color.Gray
            )
        }
        item {//比赛LazyRow
            RaceCard()
        }
        item {//热度榜单
            androidx.compose.material3.Text(
                text = "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
                color = Color.Gray
            )
        }
        item{//榜单Column
            HotListCard()
        }
        item {//实习岗位
            androidx.compose.material3.Text(
                text = "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
                color = Color.Gray
            )
        }
        item {
            PracticeCard()
        }
        item {
            Spacer(modifier = Modifier.height(150.dp).background(Color.Transparent))
        }
    }
}

@Composable
fun RaceCard(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "近期", style = MaterialTheme.typography.labelMedium)
        Text(
            text = "比赛",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOf(
                "领航杯" to "2024.9.28",
                "江苏省职业院校技能大赛" to "2025.1",
                "第六届全球校园人工智能算法精英大赛" to "2024.10.10"
            )) { destination ->
                DestinationCard(
                    title = destination.first,
                    rating = destination.second.split(" ")[0]
                )
            }
        }
    }
}
@Composable
fun DestinationCard(title: String, rating: String) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.bodySmall)
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = rating, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
@Composable
fun HotListCard(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "比赛", style = MaterialTheme.typography.labelMedium)
        Text(
            text = "热度榜单",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("领航杯" to "9.28", "蓝桥杯" to "4.8", "状元杯" to "3.25")) { style ->
                TravelStyleChip(title = style.first, subtitle = style.second)
            }
        }
    }
}
@Composable
fun TravelStyleChip(title: String, subtitle: String) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge)
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun PracticeCard(){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "实习岗位",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listOf(
                "3-4k" to "软件开发工程师",
                "5-6k" to "算法工程师",
                "1-2k" to "计算机工程师"
            )) { activity ->
                ActivityCard(
                    title = activity.first,
                    subtitle = activity.second,
                    label =  "实习"
                )
            }
        }
    }
}

@Composable
fun ActivityCard(title: String, subtitle: String, label: String? = null) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (label != null) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(4.dp)
                    )
                }
                Column {
                    Text(text = title, style = MaterialTheme.typography.bodySmall)
                    Text(text = subtitle, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun CustomTop() {

    Column(modifier = Modifier.padding(16.dp).statusBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // User profile section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = com.example.company.R.drawable.company_user_icon), // 替换为你的头像资源
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "欢迎回来!", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Text(text = "$username", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            // Notification icon
            IconButton(onClick = { /* TODO: Handle notification click */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.Gray
                )
            }
        }

        // Search bar
        OutlinedTextField(
            value = "",
            onValueChange = { /* TODO: Handle search input */ },
            placeholder = { Text(text = "搜索内容？") },
            trailingIcon = {
                IconButton(onClick = { /* TODO: Handle search click */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = Color(0xFF004ec4),
                                shape = CircleShape
                            )
                            .padding(4.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White, shape = RoundedCornerShape(30.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}


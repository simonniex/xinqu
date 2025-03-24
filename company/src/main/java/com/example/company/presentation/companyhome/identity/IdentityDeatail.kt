package com.example.company.presentation.companyhome.identity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.company.R
import com.example.company.domain.model.CompanyIdentityCard
import com.example.component.BackLine
import com.example.core.nav.Destination


@Destination("company_home_identity_route")
@Composable
fun IdentityDeatail(identityName:String,navController: NavHostController) {
    val teacher = remember {
        listOf(
            CompanyIdentityCard(R.drawable.company_icon_1, "张三", "江苏安全技术职业学院", "高级工程师"),
            CompanyIdentityCard(R.drawable.company_icon_2, "李四", "清华大学", "辅导员"),
            CompanyIdentityCard(R.drawable.company_icon_3, "王五", "东南大学", "副教授"),
            CompanyIdentityCard(R.drawable.company_icon_4, "刘六", "中国矿业大学", "党支部书记")
        )
    }
    val student = remember {
        listOf(
            CompanyIdentityCard(R.drawable.company_icon_5, "小王", "深圳大学", "计算机科学与技术 大二"),
            CompanyIdentityCard(R.drawable.company_icon_6, "小六", "北京大学", "法律系 硕士"),
            CompanyIdentityCard(R.drawable.company_icon_7, "小张", "斯坦福大学", "哲学 大一"),
            CompanyIdentityCard(R.drawable.company_icon_8, "小关", "江苏大学", "食品科技技术 大三")
        )
    }
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        BackLine(navController,identityName)
        PersonGrid(if (identityName=="老师") teacher else student)
    }
}
@Composable
fun PersonGrid(persons: List<CompanyIdentityCard>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(persons) { person ->
            PersonCard(person)
        }
    }
}
@Composable
fun PersonCard(person: CompanyIdentityCard) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(250.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(person.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = person.name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = person.school,
                style = TextStyle(color = Color.Gray, fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = person.position,
                style = TextStyle(color = Color.Gray, fontSize = 14.sp)
            )
        }
    }
}

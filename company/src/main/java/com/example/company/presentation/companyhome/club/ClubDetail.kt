package com.example.company.presentation.companyhome.club

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.company.R
import com.example.component.BackLine
import com.example.core.nav.Destination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@OptIn(ExperimentalMaterial3Api::class)
@Destination("company_home_club_route")
@Composable
fun ClubDetail(
    title: String,
    imageRes: Int,
    phone: String,
    time: String,
    people: Int,
    content: String,
    navController: NavHostController
) {

    Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            BackLine(navController,title)
            // Image
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Create Time
            Text(
                text = "创建时间: $time",
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // People Count
            Text(
                text = "总人数: $people",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contact Info
            Text(
                text = "联系方式: $phone",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Content
            Text(
                text = content,
                style = MaterialTheme.typography.body1
            )
        }
}

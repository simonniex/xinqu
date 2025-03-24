package com.example.student.presentation.virtue.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.component.BackLine
import com.example.core.data.model.Book
import com.example.core.nav.Destination
import com.example.student.presentation.virtue.detail.viewmodel.VirtuePeopleViewModel

@Destination("student_virtue_people_route")
@Composable
fun VirtuePeopleDetail(navController: NavHostController, viewModel: VirtuePeopleViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val bookList by viewModel.bookList.collectAsState()
    val searchIsbn by viewModel.searchIsbn.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        BackLine(navController,"人文素养")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = searchIsbn,
                onValueChange = { viewModel.onIsbnChange(it) },
                placeholder = { Text("输入 ISBN") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            )
            androidx.compose.material3.Button(
                onClick = {
                    viewModel.searchBooksByIsbn() // 搜索
                },
                modifier = Modifier.height(56.dp)
            ) {
                Text("搜索")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)
        ) {
            items(bookList) { book ->
                BookCard(book)
            }
        }
    }
}
@Composable
fun BookCard(book: Book) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(book.img),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "作者: ${book.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = "出版社: ${book.publisher}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = book.summary,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 3, // 设置最大行数
                overflow = TextOverflow.Ellipsis // 超出部分用省略号
            )
        }
    }
}

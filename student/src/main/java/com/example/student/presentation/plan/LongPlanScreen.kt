package com.example.student.presentation.plan

import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.other.util.internet.Message
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun LongPlanScreen(viewModel: LongPlanViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            reverseLayout = false
        ) {
            items(viewModel.messages) { message ->
                MessageBubble(message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.inputText,
                onValueChange = { viewModel.onInputTextChanged(it) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("输入消息") }
            )
            androidx.compose.material3.Button(
                onClick = {
                    viewModel.postMessage()
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("发送")
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.role == "assistant") Arrangement.Start else Arrangement.End
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = if (message.role == "assistant") Color.LightGray else Color.Green,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = message.content,
                color = if (message.role == "assistant") Color.Black else Color.White,
                fontSize = 16.sp
            )
        }
    }
}

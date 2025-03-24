package com.example.core.chat.presentation.talk

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.component.BackLine
import com.example.core.nav.Destination
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    username:String?,
    navController: NavHostController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    println("聂鑫好帅啊$username")


    //从compose环境中获取上下文，可以区别于xml
    val context = LocalContext.current
    //响应key值变化，从而执行副作用的函数。一般在处理compose生命周期的时候常用
    LaunchedEffect(key1 = true){
        //订阅viewModel的toastEvent流，当流中有新事件发出时候
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    println(ChatState().messages)
    //从当前的Compose环境中获取lifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current
    //类似于LaunchedEffect，但是可以监听lifecycleOwner的生命周期
    DisposableEffect(key1 = lifecycleOwner){
        //创建生命周期观察者对象
        val observer = LifecycleEventObserver{
                _, event ->
            if (event == Lifecycle.Event.ON_START){
                viewModel.connectToChatSocket()
            }else if (event == Lifecycle.Event.ON_STOP){
                viewModel.disconnect()
            }
        }
        //添加
        lifecycleOwner.lifecycle.addObserver(observer)
        //用于移除，是DisposableEffect专属的块
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    //从viewModel中获取当前的状态值，并存储在state变量中
    val state = viewModel.state.value
    println("Current messages: ${state.messages}") // 检查消息是否更新

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {
        BackLine(navController,"讨论室")
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            //对话列表
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxSize(),
                reverseLayout = true
            ){
                //具体item，Spacer是空白，可以自定义
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                items(state.messages){message ->
                    val isOwnMessage = message.username == username
                    Box(
                        contentAlignment = if (isOwnMessage) {
                            Alignment.CenterEnd
                        }else Alignment.CenterStart, modifier = Modifier.fillMaxWidth()
                    ){
                        Column(
                            modifier = Modifier
                                .width(200.dp)
                                .drawBehind {
                                    val cornerRadius = 10.dp.toPx()
                                    val triangleHeight = 20.dp.toPx()
                                    val triangleWidth = 25.dp.toPx()
                                    val trianglePath = Path().apply {
                                        if (isOwnMessage) {
                                            moveTo(size.width, size.height - cornerRadius)
                                            lineTo(size.width, size.height + triangleHeight)
                                            lineTo(size.width - triangleWidth, size.height - cornerRadius)
                                            close()
                                        } else {
                                            moveTo(0f, size.height - cornerRadius)
                                            lineTo(0f, size.height + triangleHeight)
                                            lineTo(triangleWidth, size.height - cornerRadius)
                                            close()
                                        }
                                    }
                                    drawPath(
                                        path = trianglePath,
                                        color = if (isOwnMessage) Color.Green else Color.DarkGray
                                    )
                                }
                                .background(
                                    color = if (isOwnMessage) Color.Green else Color.DarkGray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Text(text = message.username, fontWeight = FontWeight.Bold,color = Color.White)
                            Text(text = message.text, color = Color.White)
                            Text(text = message.formattedTime, color = Color.White, modifier = Modifier.align(
                                Alignment.End))
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
            //输入框和按钮
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextField(
                    value = viewModel.messageText.value,
                    onValueChange = viewModel::onMessageChange,
                    placeholder = {
                        Text(text = "请输入消息")
                    },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = viewModel::sendMessage) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "发送消息"
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
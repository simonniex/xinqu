package com.example.core.chat.remote.other

import com.example.core.chat.presentation.talk.Resource
import com.example.core.chat.remote.dto.MessageDto
import com.example.core.data.model.Message
import com.example.core.other.util.MyResource
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val client: HttpClient
) : ChatSocketService {

    private var socket: WebSocketSession?= null

    //启动会话,Session可以使websocket保持长连接
    override suspend fun initSession(username: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?username=$username")
            }
            if (socket?.isActive == true){
                Resource.Success(Unit)
            }else Resource.Error("Couldn't establish connection")
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e.localizedMessage?: "Unknown Error")
        }
    }

    //发送消息
    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    //接收到消息
    @OptIn(ExperimentalSerializationApi::class)
    override fun observeMessages(): Flow<Message> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto.toMessage()
                } ?: flow{}
        }catch (e:Exception){
            e.printStackTrace()
            flow {  }
        }
    }

    //关闭会话
    override suspend fun closeSession() {
        socket?.close()
    }

}
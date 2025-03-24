package com.example.core.chat.remote.other


import com.example.core.chat.remote.dto.MessageDto
import com.example.core.data.model.Message
import io.ktor.client.*
import io.ktor.client.request.*


class MessageServiceImpl(
    private val client: HttpClient
): MessageService {
    override suspend fun getAllMessages(): List<Message> {
        return try {
            //get方法，获取客户端消息
            client.get<List<MessageDto>>(MessageService.Endpoints.GetAllMessages.url).map { it.toMessage() }//map将列表映射到消息
        }catch (e: Exception){
            emptyList()
        }
    }
}
package com.example.core.chat.remote.dto

import com.example.core.data.model.Message
import kotlinx.serialization.Serializable
import java.text.DateFormat
import java.util.*

//确保kotlinx序列化和反序列化的字段一致
@Serializable
data class MessageDto(
    val text:String,
    val timestamp: Long,
    val username:String,
    val id:String
){//函数映射技术
fun toMessage(): Message {
    val date = Date(timestamp)
    val formattedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(date)
    return Message(
        text = text,
        formattedTime = formattedDate,
        username = username
    )
}
}

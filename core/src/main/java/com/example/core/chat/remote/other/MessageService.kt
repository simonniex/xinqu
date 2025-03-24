package com.example.core.chat.remote.other

import com.example.core.data.model.Message

interface MessageService {
    //获取全部消息
    suspend fun getAllMessages(): List<Message>

    //主要是为了只创建一次实例，减少内存占用，提高性能，主要被用于需要使用次数多的类
    companion object{
        const val BASE_URL = "http://192.168.0.104:9090"
    }

    sealed class Endpoints(val url:String){
        //主要是为了单例化和共享状态，主要用于设计各种设计模式
        object GetAllMessages:Endpoints("$BASE_URL/messages")
    }

}
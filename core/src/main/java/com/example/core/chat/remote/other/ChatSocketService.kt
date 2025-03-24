package com.example.core.chat.remote.other

import com.example.core.chat.presentation.talk.Resource
import com.example.core.data.model.Message
import com.example.core.other.util.MyResource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    //建立连接
    suspend fun initSession(username: String):Resource<Unit>

    //发送信息
    suspend fun sendMessage(message: String)

    //观察信息状态
    fun observeMessages(): Flow<Message>


    //关闭连接
    suspend fun closeSession()
    /*
    * 封装接口
    * */
    companion object{
        const val BASE_URL = "ws://192.168.0.104:9090"
    }

    sealed class Endpoints(val url:String){
        object ChatSocket:Endpoints("$BASE_URL/chat-socket")
    }

}
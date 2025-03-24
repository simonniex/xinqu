package com.example.core.other.util.internet
import android.annotation.SuppressLint
import androidx.compose.ui.platform.LocalContext
import com.example.core.data.model.CourseData
import com.example.core.data.model.TalkListItem
import com.example.core.data.model.TextItem
import com.example.core.data.model.Video
import com.example.core.feedback.FeedBackItem
import com.example.core.state.userState
import okhttp3.MediaType.Companion.toMediaType
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

data class MessageRequest(val model: String, val messages: List<Message>)
data class Message(val role: String, val content: String)
data class MessageResponse(val code: Int, val message: String, val choices: List<Choice>)
data class Choice(val message: AssistantMessage)
data class AssistantMessage(val role: String, val content: String)

object OkHttpClientProvider {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // 连接超时
        .readTimeout(30, TimeUnit.SECONDS) // 读取超时
        .writeTimeout(30, TimeUnit.SECONDS) // 写入超时
        .build()

    fun getClient(): OkHttpClient = client
}

fun sendMessage(content: String,userMessage: String, onResponse: (String) -> Unit) {
    val requestData = MessageRequest(
        model = "4.0Ultra",
        messages = listOf(
            Message(role = "system", content = content),
            Message(role = "user", content = userMessage)
        )
    )

    val json = Gson().toJson(requestData)
    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    val request = Request.Builder()
        .url("https://spark-api-open.xf-yun.com/v1/chat/completions")
        .post(requestBody)
        .addHeader("Authorization", "Bearer tKMLdRDhTbWNBzASpfPS:oSqnrkZTbBPqbsurJkvV")
        .build()

    OkHttpClientProvider.getClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败: ${e.localizedMessage ?: "未知错误"}")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val messageResponse = Gson().fromJson(responseBody, MessageResponse::class.java)
                        onResponse(messageResponse.choices[0].message.content)
                    } ?: println("响应体为空")
                } else {
                    println("发送失败: ${response.code} - ${response.message}")
                }
            } finally {
                response.close() // 确保响应体被关闭
            }
        }
    })
}

fun sendText(keyword: String,newItem:TextItem,ip:String) {

    val json = Gson().toJson(newItem)
    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    val request = Request.Builder()
        .url("http://$ip:8001/save-item/") // 修改为实际的保存接口URL
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    OkHttpClientProvider.getClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败: ${e.localizedMessage ?: "未知错误"}")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                if (response.isSuccessful) {
                    println("文本发送成功")
                    // 处理响应数据
                    val responseData = response.body?.string()
                    // 进一步处理 responseData
                } else {
                    println("发送失败: ${response.code} - ${response.message}")
                    // 打印响应体中的详细错误信息
                    response.body?.let { responseBody ->
                        println("响应内容: ${responseBody.string()}")
                    }
                }
            } finally {
                response.close() // 确保响应体被关闭
            }
        }

    })
}

fun sendCourse(newCourse: CourseData,ip: String) {
    val json = Gson().toJson(newCourse)
    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    val request = Request.Builder()
        .url("http://$ip:8002/courses/teacher") // 修改为实际的保存课程接口URL
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    OkHttpClientProvider.getClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败: ${e.localizedMessage ?: "未知错误"}")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                if (response.isSuccessful) {
                    println("课程发送成功")
                } else {
                    println("发送失败: ${response.code} - ${response.message}")
                    // 打印响应体中的详细错误信息
                    response.body?.let { responseBody ->
                        println("响应内容: ${responseBody.string()}")
                    }
                }
            } finally {
                response.close() // 确保响应体被关闭
            }
        }
    })
}

fun sendVideo(title: String, link: String, image: String,ip: String) {
    val newVideo = Video(title = title, name = link, imageRes = image)
    val json = Gson().toJson(newVideo)
    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    val request = Request.Builder()
        .url("http://$ip:8003/videos/") // 修改为实际的保存视频接口URL
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    OkHttpClientProvider.getClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败: ${e.localizedMessage ?: "未知错误"}")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                if (response.isSuccessful) {
                    println("视频发送成功")
                    // 进一步处理 responseData
                } else {
                    println("发送失败: ${response.code} - ${response.message}")
                    // 打印响应体中的详细错误信息
                    response.body?.let { responseBody ->
                        println("响应内容: ${responseBody.string()}")
                    }
                }
            } finally {
                response.close() // 确保响应体被关闭
            }
        }
    })
}

fun sendFeedBack(identity: String, people: String, feedback: FeedBackItem,ip: String) {
    val json = Gson().toJson(feedback)
    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    val request = Request.Builder()
        .url("http://$ip:8004/fankui?identity=$identity&people=$people") // 修改为实际的反馈接口URL
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    OkHttpClientProvider.getClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败: ${e.localizedMessage ?: "未知错误"}")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                if (response.isSuccessful) {
                    println("反馈发送成功")
                } else {
                    println("发送失败: ${response.code} - ${response.message}")
                    response.body?.let { responseBody ->
                        println("响应内容: ${responseBody.string()}")
                    }
                }
            } finally {
                response.close() // 确保响应体被关闭
            }
        }
    })
}


fun sendTalkList(talk: TalkListItem,ip: String) {
    val json = Gson().toJson(talk)
    val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

    val request = Request.Builder()
        .url("http://$ip:8005/talks") // 修改为实际的反馈接口URL
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    OkHttpClientProvider.getClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("请求失败: ${e.localizedMessage ?: "未知错误"}")
        }

        override fun onResponse(call: Call, response: Response) {
            try {
                if (response.isSuccessful) {
                    println("反馈发送成功")
                } else {
                    println("发送失败: ${response.code} - ${response.message}")
                    response.body?.let { responseBody ->
                        println("响应内容: ${responseBody.string()}")
                    }
                }
            } finally {
                response.close() // 确保响应体被关闭
            }
        }
    })
}





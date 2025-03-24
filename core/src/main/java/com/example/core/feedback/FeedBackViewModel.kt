package com.example.core.feedback

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.other.util.internet.fetchFeedBack
import com.example.core.other.util.internet.sendFeedBack
import com.example.core.state.userState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FeedBackViewModel : ViewModel() {
    var ip:String? =""
    private val _feedbackList = MutableStateFlow<List<FeedBackData>>(emptyList())
    val feedbackList: StateFlow<List<FeedBackData>> = _feedbackList.asStateFlow()

    fun fetchFeedbackData1(identity: String, username: String) {
        viewModelScope.launch {
            val feedbacks = fetchFeedBack(identity, username)
            _feedbackList.value = feedbacks ?: emptyList() // 更新状态流
        }
    }

    init {
        getip()
    }

    fun getip(){
        viewModelScope.launch {
            ip = userState.geIp()
        }
    }

    fun sendFB(identity: String, people: String, feedbackContent: String) {
        val feedbackItem = FeedBackItem(
            content = feedbackContent,
            image = null,
            sendTime = getCurrentTime(),
            state = "待处理",
            back = ""
        )

        // 创建反馈数据对象
        val feedbackData = FeedBackData(
            id = getNextId(), // 假设有获取下一个 ID 的函数
            identity = identity,
            people = people,
            fanKui = feedbackItem
        )

        // 调用 sendFeedBackToServer 函数
        sendFeedBackToServer(feedbackData)
    }

    private fun sendFeedBackToServer(feedbackData: FeedBackData) {
        ip?.let {
            sendFeedBack(feedbackData.identity, feedbackData.people, feedbackData.fanKui,
                it
            )
        } // 调用网络请求函数
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }

    private fun getNextId(): Int {
        // 实现自增 ID 的逻辑
        return _feedbackList.value.size + 1 // 示例
    }




}



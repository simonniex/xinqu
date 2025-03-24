package com.example.core.chat.presentation.problem

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.TalkListItem
import com.example.core.feedback.FeedBackData
import com.example.core.other.util.internet.fetchFeedBack
import com.example.core.other.util.internet.fetchTalkList
import com.example.core.other.util.internet.sendTalkList
import com.example.core.state.userState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProblemViewModel : ViewModel(){
    private val  _talkList = MutableStateFlow<List<TalkListItem>>(emptyList())
    val  talkList: StateFlow<List<TalkListItem>> =  _talkList.asStateFlow()
    fun getData() {
        viewModelScope.launch {
            val feedbacks = fetchTalkList()
            _talkList.value = feedbacks ?: emptyList() // 更新状态流
        }
    }

    fun addList(content:String,username:String){
        viewModelScope.launch {
            val ip = userState.geIp()
                val item =  TalkListItem(
                    title = username,
                    salary = content,
                    description = getCurrentTime()
                )
            if (ip != null) {
                sendTalkList(item,ip)
            }
        }
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
}
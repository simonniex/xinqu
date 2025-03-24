package com.example.student.presentation.plan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.other.util.ResultState
import com.example.core.other.util.internet.Message
import com.example.core.other.util.internet.post
import com.example.core.other.util.internet.sendMessage
import com.example.student.domain.model.requestResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class LongPlanViewModel : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    var inputText by mutableStateOf(TextFieldValue(""))


    fun onInputTextChanged(newText: TextFieldValue) {
        inputText = newText
    }

    fun postMessage() {
        if (inputText.text.isNotBlank()) {
            // 显示用户消息
            _messages.add(Message(role = "user", content = inputText.text))
            val currentInput = inputText.text
            inputText = TextFieldValue("")  // 清空输入框

            // 调用 OkHttp 的 sendMessage
            sendMessage("你是一个学校老师，帮助解决学生学习规划",currentInput) { botResponse ->
                // 显示机器人的回复
                _messages.add(Message(role = "assistant", content = botResponse))
            }
        }
    }
}

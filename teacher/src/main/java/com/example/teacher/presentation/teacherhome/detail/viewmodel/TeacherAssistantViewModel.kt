package com.example.teacher.presentation.teacherhome.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.core.other.util.internet.Message
import com.example.core.other.util.internet.sendMessage


class TeacherAssistantViewModel : ViewModel(){
    private val _messages = mutableStateListOf<Message>()
    val courseList: List<Message> = _messages

    var inputText by mutableStateOf(TextFieldValue(""))

    init {
        // 初始化消息
        _messages.addAll(
            listOf(
                Message(role = "assistant", content = "你好，我是你的教学助手,有什么需要帮助的吗?"),
            )
        )
    }


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
            sendMessage("你是一个人工智能助手,帮助老师备课和提供解决方案",currentInput) { botResponse ->
                // 显示机器人的回复
                _messages.add(Message(role = "assistant", content = botResponse))
            }
        }
    }
}

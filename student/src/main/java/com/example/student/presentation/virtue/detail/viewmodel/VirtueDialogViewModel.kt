package com.example.student.presentation.virtue.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.core.other.util.internet.Message
import com.example.core.other.util.internet.sendMessage
import dagger.hilt.android.lifecycle.HiltViewModel

class VirtueDialogViewModel : ViewModel() {
    private val _messages = mutableStateListOf<Message>()
    val messages: List<Message> = _messages

    var inputText by mutableStateOf(TextFieldValue(""))

    init {
        // 初始化消息
        _messages.addAll(
            listOf(
                Message(role = "assistant", content = "你好，我是AI心理健康老师，你可以向我倾诉！"),
                // 其他消息可以在这里添加
            )
        )
    }


    fun onInputTextChanged(newText: TextFieldValue) {
        inputText = newText
    }

    fun postMessage() {
        if (inputText.text.isNotBlank()) {
            // 发送用户消息
            _messages.add(Message(role = "user", content = inputText.text))
            val currentInput=inputText.text
            inputText = TextFieldValue("") // 清空输入框

            // 调用 OkHttp 的 sendMessage
            sendMessage( "你是一个学校心理健康老师，辅导学生心理健康",currentInput) { botResponse ->
                // 显示机器人的回复
                _messages.add(Message(role = "assistant", content = botResponse))
            }
        }
    }

}
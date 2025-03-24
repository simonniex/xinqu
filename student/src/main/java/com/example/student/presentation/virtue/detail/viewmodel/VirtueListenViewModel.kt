package com.example.student.presentation.virtue.detail.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.TextItem
import com.example.core.other.util.internet.fetchText
import com.example.core.other.util.internet.sendText
import com.example.core.state.userState
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class VirtueListenViewModel : ViewModel() {
    var myCode by mutableStateOf("")
    fun onMyCodeChanged(newCode: String) {
        myCode = newCode
    }
    var textFieldValue by mutableStateOf(TextFieldValue(""))

    fun onInputTextChanged(newText: TextFieldValue) {
        textFieldValue = newText
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun postText(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val ip = userState.geIp()
            println(ip)
            val usernameId = userState.getUsernameId() // 使用 userState 获取 usernameId

            if (!usernameId.isNullOrEmpty()) {
                val newItem = TextItem(
                    keyword = usernameId, // 使用获取到的 usernameId 作为 keyword
                    text = textFieldValue.text, // 使用输入的文本作为 text
                    timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )

                if (ip != null) {
                    sendText(usernameId, newItem,ip)
                } // 调用发送函数
                onInputTextChanged(TextFieldValue(""))
                onSuccess()
            } else {
                // 处理 usernameId 为空的情况，可能显示错误提示
                println("UsernameId is null or empty!")
            }
        }
    }

}
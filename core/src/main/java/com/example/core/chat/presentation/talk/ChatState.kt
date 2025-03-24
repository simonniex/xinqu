package com.example.core.chat.presentation.talk

import com.example.core.data.model.Message

data class ChatState(
    val messages : List<Message> = emptyList(),
    val isLoading : Boolean = false
)

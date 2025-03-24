package com.example.student.domain.model

data class requestResponse(
    val code: Int,
    val message: String,
    val sid: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val message: AssistantMessage,
    val index: Int
)

data class AssistantMessage(
    val role: String,
    val content: String
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

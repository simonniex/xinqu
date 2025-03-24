package com.example.core.feedback

data class FeedBackItem(
    val content: String,         // 反馈内容
    val image: String? = null,   // 图片，默认为 null
    val sendTime: String,        // 反馈时间
    val state: String,           // 反馈状态
    val back: String              // 回应
)

data class FeedBackData(
    val id: Int,                 // 自增 ID
    val identity: String,        // 发送者的身份
    val people: String,          // 发送者的姓名
    val fanKui: FeedBackItem       // 反馈内容
)

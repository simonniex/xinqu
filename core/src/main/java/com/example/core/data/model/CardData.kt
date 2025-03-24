package com.example.core.data.model


data class CardData(
    val dayOfWeek:String,//每周当前天数，例如周一，周二......
    val title: String, // 文章标题
    val description: String, // 文章简介
    val type: CardType, // 卡片类型，无图片，单张图片，一组图片
    val imageId : String? = null, // 单张图片，可选且可为空
    val images: List<String>? = null // 一组图片，可选且可为空
)


enum class CardType {
    NO_IMAGE,
    ONE_IMAGE,
    MULTIPLE_IMAGES
}

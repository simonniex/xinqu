package com.example.company.domain.model

data class BannerItem(
    val title: String,//名称
    val imageRes: Int,//图片
    val phone:String,//联系电话
    val time:String,//创建时间
    val people:Int,//人数
    val content:String//简介
)
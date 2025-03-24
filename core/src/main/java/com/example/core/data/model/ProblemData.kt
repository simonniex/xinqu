package com.example.core.data.model

//交流广场问题data类
data class ProblemData(
    val id:Int,//id
    val title:String,//问题标题
    val time:String,//问题发布时间
    val love:Boolean//用户点赞
)

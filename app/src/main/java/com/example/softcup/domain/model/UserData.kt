package com.example.softcup.domain.model

import com.example.core.dao.Company
import com.example.core.dao.Student
import com.example.core.dao.Teacher

// 顶层的 ApiResponse，包含状态和数据部分
data class Response(
    val status: String,
    val data: Data
)

// 包含 Teachers, Students, Companys 的 Data 类
data class Data(
    val teachers: List<Teacher>,
    val students: List<Student>,
    val companys: List<Company>
)


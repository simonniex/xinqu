package com.example.student.domain.model

// 数据模型
data class Project(val name: String, val requirement: String)
data class Exam(val name: String, val location: String, val time: String)
data class Competition(
    val name: String,
    val introduction: String,
    val time: String,
    val isOngoing: Boolean,
    val registrationDeadline: String
)
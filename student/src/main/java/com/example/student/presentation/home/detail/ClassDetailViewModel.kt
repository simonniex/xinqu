package com.example.student.presentation.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.CourseData
import com.example.core.other.util.internet.fetchCourse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassDetailViewModel : ViewModel() {
    private val _courseList = MutableStateFlow<List<CourseData>>(emptyList())
    val courseList: StateFlow<List<CourseData>> = _courseList

    // 获取校园课程和企业课程
    private val _campusCourses = MutableStateFlow<List<CourseData>>(emptyList())
    val campusCourses: StateFlow<List<CourseData>> = _campusCourses

    private val _companyCourses = MutableStateFlow<List<CourseData>>(emptyList())
    val companyCourses: StateFlow<List<CourseData>> = _companyCourses

    // 获取课程数据
    fun getCourse(keyword: String) {
        viewModelScope.launch {
            try {
                val courses = fetchCourse(keyword) // 这里调用 fetchCourse 函数
                if (keyword == "teacher") {
                    _campusCourses.value = courses
                } else if (keyword == "company") {
                    _companyCourses.value = courses
                }
            } catch (e: Exception) {
                // 处理错误
                println("Error fetching courses: ${e.message}")
            }
        }
    }
}

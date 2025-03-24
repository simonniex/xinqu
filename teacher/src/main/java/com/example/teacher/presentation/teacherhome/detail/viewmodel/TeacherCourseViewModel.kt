package com.example.teacher.presentation.teacherhome.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.CourseData
import com.example.core.other.util.internet.fetchCourse
import com.example.core.other.util.internet.sendCourse
import com.example.core.state.userState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TeacherCourseViewModel : ViewModel() {
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val _instructorName = MutableStateFlow("")
    val instructorName: StateFlow<String> = _instructorName

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location




    fun updateInstructorName(name: String) {
        _instructorName.value = name
    }

    fun updateLocation(location: String) {
        _location.value = location
    }

    // 更新输入框的文本
    fun updateInputText(text: String) {
        _inputText.value = text
    }


    // 发布新课程项
    fun addCourse() {
        viewModelScope.launch {
            val ip: String? = userState.geIp()
            println(ip)
            if (_inputText.value.isNotBlank() && _instructorName.value.isNotBlank() && _location.value.isNotBlank()) {
                val newCourse = CourseData(
                    instructorName = _instructorName.value,
                    courseName = _inputText.value,
                    date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), // 示例日期
                    location = _location.value // 使用输入框的值
                )
                if (ip != null) {
                    sendCourse(newCourse,ip)
                } // 发送到服务器
                println(newCourse)
                // 清空输入框
                _inputText.value = ""
                _instructorName.value = ""
                _location.value = ""
            }
        }
    }

}

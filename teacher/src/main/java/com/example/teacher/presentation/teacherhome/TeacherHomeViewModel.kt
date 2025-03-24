package com.example.teacher.presentation.teacherhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dao.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

//data class Response(
//    val status: String,
//    val data: Data
//)
//
//// 包含 Teachers, Students, Companys 的 Data 类
//data class Data(
//    val teachers: List<Teacher>,
//    val students: List<Student>,
//    val companys: List<Company>
//)
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}

@HiltViewModel
class TeacherHomeViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<Resource<List<StudentEntity>>>(Resource.Loading())
    val state: StateFlow<Resource<List<StudentEntity>>> = _state
    private val _studentList = MutableStateFlow<List<StudentEntity>>(emptyList())
    val studentList: StateFlow<List<StudentEntity>> = _studentList

    fun getAllStudentsForTeacher(teacherSchool: String) {
        viewModelScope.launch {
            studentRepository.getAllStudents()
                .map { students ->
                    students.filter { it.school == teacherSchool }
                }
                .collect {
                    _studentList.value = it
                }
        }
    }
    fun getAllStudent() {
        viewModelScope.launch {
            studentRepository.getAllStudents()
                .catch { exception ->
                    _state.value = Resource.Error("Error fetching students: ${exception.message}")
                }
                .collect { students ->
                    _state.value = Resource.Success(students)
                }
        }
    }
}
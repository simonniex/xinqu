package com.example.softcup.presentation.start.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dao.*
import com.example.core.other.util.ResultState
import com.example.softcup.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ResultState<Response>>(ResultState.Loading)
    val state: StateFlow<ResultState<Response>> = _state

    private val url = "http://192.168.0.104:8000/data"

    private val _usernameText = mutableStateOf("")
    private val _passwordText = mutableStateOf("")
    private val _schoolText = mutableStateOf("")
    private val _studentIdText = mutableStateOf("")
    private val _emailPhoneText = mutableStateOf("")
    private val _phoneText = mutableStateOf("")

    val usernameText : State<String> = _usernameText
    val passwordText : State<String> = _passwordText
    val schoolText : State<String> = _schoolText
    val studentIdText : State<String> = _studentIdText
    val emailPhoneText : State<String> = _emailPhoneText
    val phoneText :State<String> = _phoneText



    fun onUsernameChange(newText: String) {
        _usernameText.value = newText
    }

    fun onPasswordChange(newText: String) {
        _passwordText.value = newText
    }

    fun onSchoolChange(newText: String) {
        _schoolText.value = newText
    }

    fun onStudentIdChange(newText: String) {
        _studentIdText.value = newText
    }

    fun onEmailPhoneChange(newText: String) {
        _emailPhoneText.value = newText
    }
    fun onPhoneChange(newText: String){
        _phoneText.value = newText
    }

    fun postData(
        url: String,
        studentData: Map<String, Any>,
        avatarFile: File?,
        clubFile: File?
    ){
        viewModelScope.launch {
            com.example.core.other.util.internet.post<Response>(url, studentData, null,null).collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        _state.value = ResultState.Success(result.data)
                        println("注册成功: ${result.data}")
                    }
                    is ResultState.Error -> {
                        _state.value = ResultState.Error(result.exception, "网络连接失败，请检查服务器")
                        println("注册失败: ${result.message}")
                    }
                    else -> {
                        _state.value = ResultState.Loading
                    }
                }
            }
        }
    }

    fun insertStudent(student: Student) {
        viewModelScope.launch {
            val studentEntity = StudentEntity.from(student) // 转换为 StudentEntity
            studentRepository.insertStudents(listOf(studentEntity))
        }
    }

    fun insertTeacher(teacher: Teacher) {
        viewModelScope.launch {
            val teacherEntity = TeacherEntity.from(teacher) // 转换为 StudentEntity
            teacherRepository.insertTeachers(listOf(teacherEntity))
        }
    }

    fun insertCompany(company: Company) {
        viewModelScope.launch {
            val companyEntity = CompanyEntity.from(company) // 转换为 StudentEntity
            companyRepository.insertCompanies(listOf(companyEntity))
        }
    }
}

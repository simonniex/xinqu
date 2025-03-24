package com.example.softcup.presentation.start.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dao.CompanyRepository
import com.example.core.dao.StudentEntity
import com.example.core.dao.StudentRepository
import com.example.core.dao.TeacherRepository
import com.example.core.other.util.ResultState
import com.example.softcup.util.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {
    // 定义登录状态的 StateFlow
    // 定义登录状态的 StateFlow
    private val _loginState = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val loginState: StateFlow<Resource<Any>> = _loginState

    private val _usernameText = mutableStateOf("")
    private val _passwordText = mutableStateOf("")

    var showPassword by mutableStateOf(false)

    val usernameText : State<String> = _usernameText
    val passwordText : State<String> = _passwordText

    fun onUsernameChange(username: String) {
        _usernameText.value = username
    }
    fun onPasswordChange(password:String){
        _passwordText.value = password
    }


    fun login() {
        viewModelScope.launch {
            // 这里需要判断用户类型并获取相应的数据
            val studentFlow = studentRepository.getStudentByUsername(usernameText.value)
            val teacherFlow = teacherRepository.getTeacherByUsername(usernameText.value)
            val companyFlow = companyRepository.getCompanyByUsername(usernameText.value)

            // 使用结合的方式处理不同类型的用户
            studentFlow.collect { student ->
                if (student != null && student.username == usernameText.value && student.password == passwordText.value) {
                    _loginState.value = Resource.Success(student)
                } else {
                    // 检查老师
                    teacherFlow.collect { teacher ->
                        if (teacher != null && teacher.username == usernameText.value && teacher.password == passwordText.value) {
                            _loginState.value = Resource.Success(teacher)
                        } else {
                            // 检查公司
                            companyFlow.collect { company ->
                                if (company != null && company.username == usernameText.value && company.password == passwordText.value) {
                                    _loginState.value = Resource.Success(company)
                                } else {
                                    _loginState.value = Resource.Error("请先注册")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


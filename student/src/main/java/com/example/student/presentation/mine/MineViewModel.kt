package com.example.student.presentation.mine

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dao.*
import com.example.core.other.util.ResultState
import com.example.core.other.util.toBitmap
import com.example.core.state.userState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

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
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}

@HiltViewModel
class MineViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {
    private val _state1 = MutableStateFlow<ResultState<Response>>(ResultState.Loading)
    val state1: StateFlow<ResultState<Response>> = _state1

    private val _state = MutableStateFlow<Resource<StudentEntity>>(Resource.Loading())
    val state: StateFlow<Resource<StudentEntity>> = _state

    private val _avatarUri = MutableStateFlow<Bitmap?>(null)
    val avatarUri: StateFlow<Bitmap?> = _avatarUri

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    private val _password = MutableStateFlow<String?>(null)
    val password: StateFlow<String?> = _password

    private val _phone = MutableStateFlow<String?>(null)
    val phone: StateFlow<String?> = _phone

    private val _school = MutableStateFlow<String?>(null)
    val school: StateFlow<String?> = _school

    private val _studentId = MutableStateFlow<String?>(null)
    val studentId: StateFlow<String?> = _studentId

    private val _club = MutableStateFlow<String?>(null)
    val club: StateFlow<String?> = _club

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email

    private val _great = MutableStateFlow<String?>(null)
    val great: StateFlow<String?> = _great

    fun UpdateUserIcon(imageUri: Uri){
        viewModelScope.launch {
            val username = userState.getUsernameId()
            println("$username")
            if (username != null) {
                    studentRepository.getStudentByUsername(username).collect{student ->
                        student?.let {
                            val updatedStudent = it.copy(avatar = imageUri.toString())
                            studentRepository.updateStudent(updatedStudent)
                            _state.value = Resource.Success(updatedStudent) // 更新状态
                            userState.setAvatarUri(updatedStudent.avatar) // 更新 userState
                        }

                    }
            }else{
                println("登录信息错误")
            }
        }
    }
    fun updateUserData(avatarUri: Uri?, mapData: Map<String, Any>,context: Context) {
        val avatarFile = avatarUri?.let { uri ->
            createFileFromUri(context, uri) // 将 Uri 转换为 File 对象
        }
        upDataData(
            url = "http://192.168.0.104:8000/update/student/${username.value}",
            mapData = mapData,
            avatarFile = avatarFile,
            clubFile = null
        )
    }



    fun createFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image.jpg") // 适当选择文件名和位置
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun upDataData(
        url: String,
        mapData: Map<String, Any>,
        avatarFile: File?,
        clubFile: File?
    ) {
        viewModelScope.launch {
            com.example.core.other.util.internet.put<Response>(url, mapData, avatarFile,null).collect { result ->
                when (result) {
                    is ResultState.Success -> {
                        _state1.value = ResultState.Success(result.data)
                        println("更新成功: ${result.data}")
                    }
                    is ResultState.Error -> {
                        _state1.value = ResultState.Error(result.exception, "网络连接失败，请检查服务器")
                        println("更新失败: ${result.message}")
                    }
                    else -> {
                        _state1.value = ResultState.Loading
                    }
                }
            }
        }
    }
}
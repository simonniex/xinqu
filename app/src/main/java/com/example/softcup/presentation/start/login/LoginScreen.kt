package com.example.softcup.presentation.start.login

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.core.dao.CompanyEntity
import com.example.core.dao.StudentEntity
import com.example.core.dao.TeacherEntity
import com.example.core.nav.Destination
import com.example.core.nav.Navigator
import com.example.core.state.userState
import com.example.softcup.R
import com.example.softcup.component.SplitLineWithText

import com.example.softcup.util.Resource
import com.example.student.MapActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination("login")
fun LoginScreen(
    navigator: Navigator,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by loginViewModel.loginState.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val content = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var settingsText by remember { mutableStateOf("未设置") }

    val personIcon = remember { Icons.Filled.Person }
    val keyIcon = remember { Icons.Filled.Key }

    LaunchedEffect(loginState) {
        if (loginState is Resource.Success || loginState is Resource.Error) {
            isLoading = false
        }

        when (loginState) {
            is Resource.Success -> {
                val usernameId = when (val data = loginState.data) {
                    is StudentEntity -> {
                        userState.setAvatarUri(data.avatar)
                        userState.setUsernameId(data.username)
                        userState.setSchool(data.school)
                        userState.setPhone(data.phone) // 设置电话
                        userState.setStudentId(data.studentid) // 设置学生 ID
                        userState.setClub(data.club) // 设置俱乐部
                        userState.setEmail(data.email) // 设置邮箱
                        userState.setGreat(data.great.toString()) // 设置价值观

                        "student"
                    }
                    is TeacherEntity -> {
                        userState.setAvatarUri(data.avatar)
                        userState.setUsernameId(data.username)
                        userState.setSchool(data.school)
                        "teacher"
                    }
                    is CompanyEntity -> {
                        userState.setAvatarUri(data.avatar)
                        userState.setUsernameId(data.username)
                        userState.setCompany(data.company)
                        "company"
                    }
                    else -> null
                }

                usernameId?.let {
                    val intent = Intent(content, MapActivity::class.java)
                    content.startActivity(intent)
                    navigator.navigateTo(it, false)
                }
            }
            is Resource.Error -> {
                val errorMessage = (loginState as Resource.Error).message
                Toast.makeText(content, errorMessage, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = {
                showDialog = true
            }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "设置IP",
                    tint = Color.Black
                )
            }
        }
        if (showDialog) {
            SettingsDialog(
                onDismissRequest = { showDialog = false },
                onConfirm = { newText ->
                    settingsText = newText
                    showDialog = false
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(250.dp))
            InputField(
                text = loginViewModel.usernameText.value,
                onTextChange = { loginViewModel.onUsernameChange(it) },
                placeholder = "账号",
                icon = personIcon
            )
            Spacer(modifier = Modifier.height(23.dp))
            InputField(
                text = loginViewModel.passwordText.value,
                onTextChange = { loginViewModel.onPasswordChange(it) },
                placeholder = "密码",
                icon = keyIcon,
                isPassword = true,
                showPassword = loginViewModel.showPassword,
                onShowPasswordChange = { loginViewModel.showPassword = it }
            )
            Spacer(modifier = Modifier.height(26.dp))
            LoginButton(isLoading) {
                isLoading = true
                loginViewModel.login()
            }
        }
    }
}

@Composable
fun InputField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onShowPasswordChange: (Boolean) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(80.dp),
        elevation = 0.dp,
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .heightIn(min = 62.dp)
    ) {
        OutlinedTextField(
            textStyle = TextStyle(fontSize = 18.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            placeholder = { Text(text = placeholder) },
            value = text,
            onValueChange = onTextChange,
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = icon, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(20.dp)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { onShowPasswordChange(!showPassword) }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (showPassword) "Hide Password" else "Show Password"
                        )
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xfff7f8fa))
                .height(60.dp)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun LoginButton(isLoading: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .height(70.dp)
            .padding(10.dp)
            .border(1.dp, Color.White, RoundedCornerShape(4.dp))
            .background(Color(0xff00a2e8), RoundedCornerShape(4.dp))
    ) {
        TextButton(
            modifier = Modifier.fillMaxSize(),
            onClick = { if (!isLoading) onClick() },
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = if (isLoading) "加载中..." else "登录",
                color = Color.White,
                style = TextStyle(fontSize = 24.sp)
            )
        }
    }
}


@Composable
fun SettingsDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val isOk = remember { mutableStateOf<Boolean>(false) }

    if (isOk.value){
        LaunchedEffect(Unit) {
            userState.setIp(text) // 在协程中设置 IP
            onConfirm(text) // 通知外部确认
            isOk.value = false

            println(userState.geIp())
        }
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(text = "设置")
        },
        text = {
            Column {
                Text(text = "请设置您的IP地址:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text(text = "请输入...") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
               isOk.value = true
            }) {
                Text("确定")
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("取消")
            }
        }
    )
}
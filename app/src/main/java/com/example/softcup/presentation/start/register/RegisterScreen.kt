package com.example.softcup.presentation.start.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.other.util.SnackbarUtil
import kotlinx.coroutines.delay
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.example.core.dao.Company
import com.example.core.dao.Student
import com.example.core.dao.StudentEntity
import com.example.core.dao.Teacher
import com.example.core.nav.Navigator
import com.example.core.other.util.ResultState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterScreen(
    navigator: Navigator,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showSnackbar by remember { mutableStateOf(false) }
    var isAccording by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableStateOf(60) }
    var timeLeft1 by remember { mutableStateOf(1) }
    var identity by remember { mutableStateOf("学生") }
    val options = listOf("学生", "教师", "企业职工")
    var expanded by remember { mutableStateOf(false) }
    val resultState by registerViewModel.state.collectAsState()
    var snackbarMessage by remember { mutableStateOf("") }

    if (!isAccording) {
        LaunchedEffect(timeLeft) {
            if (timeLeft > 0) {
                delay(1000L) // Delay for 1 second
                timeLeft -= 1
            } else {
                isAccording = true
                timeLeft = 60
            }
        }
    }
    LaunchedEffect(resultState) {
        when (resultState) {
            is ResultState.Loading -> {
                // 加载中，可以显示进度条或禁用按钮
                showSnackbar = false
            }
            is ResultState.Success -> {
                snackbarMessage = "提交成功！"
                showSnackbar = true
            }
            is ResultState.Error -> {
                // 请求失败，显示错误消息
                snackbarMessage = "提交失败，请重试"
                showSnackbar = true
            }
            else -> {}
        }
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            coroutineScope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = snackbarMessage,
                    actionLabel = "",
                    duration = androidx.compose.material3.SnackbarDuration.Short
                )
                if (result == SnackbarResult.Dismissed) {
                    showSnackbar = false
                    navigator.navigateBack()
                }
                showSnackbar = false
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            val textFieldModifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(
                    color = Color(0xFFE0E0E0), // 灰色背景
                    shape = RoundedCornerShape(8.dp) // 圆角
                )

            val textFieldColors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        readOnly = true,
                        value = identity,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = textFieldColors,
                        modifier = textFieldModifier
                            .clickable { expanded = true }
                            .padding(horizontal = 8.dp, vertical = 8.dp) // 内边距
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        options.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    identity = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
            TextField(
                colors = textFieldColors,
                placeholder = { Text("用户名") },
                value = registerViewModel.usernameText.value,
                onValueChange = { registerViewModel.onUsernameChange(it) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "User Icon")
                },
                modifier = textFieldModifier
                    .padding(horizontal = 8.dp, vertical = 8.dp) // 内边距
            )

            TextField(
                colors = textFieldColors,
                placeholder = { Text("密码") },
                value = registerViewModel.passwordText.value,
                onValueChange = { registerViewModel.onPasswordChange(it) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password Icon")
                },
                modifier = textFieldModifier
                    .padding(horizontal = 8.dp, vertical = 8.dp) // 内边距
            )

            TextField(
                colors = textFieldColors,
                placeholder = { Text(if (identity == "企业职工") "公司" else "学校") },
                value = registerViewModel.schoolText.value,
                onValueChange = { registerViewModel.onSchoolChange(it) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.School, contentDescription = "School Icon")
                },
                modifier = textFieldModifier
                    .padding(horizontal = 8.dp, vertical = 8.dp) // 内边距
            )

            TextField(
                colors = textFieldColors,
                placeholder = { Text(if (identity == "学生") "学号" else if (identity == "教师") "职称" else if (identity == "企业职工") "编号" else "") },
                value = registerViewModel.studentIdText.value,
                onValueChange = { registerViewModel.onStudentIdChange(it) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Badge, contentDescription = "ID Icon")
                },
                modifier = textFieldModifier
                    .padding(horizontal = 8.dp, vertical = 8.dp), // 内边距
            )
            TextField(
                colors = textFieldColors,
                placeholder = { Text("手机号") },
                value = registerViewModel.phoneText.value,
                onValueChange = { registerViewModel.onPhoneChange(it) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone Icon")
                },
                modifier = textFieldModifier
                    .padding(horizontal = 8.dp, vertical = 8.dp) // 内边距
            )
            TextField(
                colors = textFieldColors,
                placeholder = { Text("邮箱") },
                value = registerViewModel.emailPhoneText.value,
                onValueChange = { registerViewModel.onEmailPhoneChange(it) },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon")
                },
                modifier = textFieldModifier
                    .padding(horizontal = 8.dp, vertical = 8.dp) // 内边距
            )
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Start,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                TextField(
//                    colors = textFieldColors,
//                    placeholder = { Text("请输入验证码") },
//                    value = registerViewModel.emailPhoneText.value,
//                    onValueChange = { registerViewModel.onEmailPhoneChange(it) },
//                    modifier = textFieldModifier
//                        .weight(1f)
//                        .height(56.dp)  // 设置统一的高度
//                        .padding(end = 8.dp)  // 设置右侧的间距
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                TextButton(
//                    onClick = { isAccording = false },
//                    modifier = Modifier
//                        .weight(0.5f)
//                        .height(56.dp)  // 设置相同的高度
//                        .background(Color(0xFF00a2e8), shape = MaterialTheme.shapes.small)
//                        .padding(start = 8.dp)  // 设置左侧的间距
//                ) {
//                    Text(text = if (isAccording) "获取验证码" else "$timeLeft" + "秒后重试")
//                }
//            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = {
                    when (identity) {
                        "学生" -> {
                            val student = Student(
                                phone = registerViewModel.phoneText.value,
                                avatar = "null", // Add logic to handle avatar input
                                username = registerViewModel.usernameText.value,
                                password = registerViewModel.passwordText.value,
                                school = registerViewModel.schoolText.value, // Could be an additional input field
                                studentid = registerViewModel.studentIdText.value,
                                club = "null", // Add logic to handle club input if necessary
                                clubImage = "null", // Handle club image logic
                                email = registerViewModel.emailPhoneText.value,
                                great = true // Set based on user input
                            )
                            val studentMap = mapOf(
                                "type" to "student",
                                "phone" to registerViewModel.phoneText.value,
                                "username" to registerViewModel.usernameText.value,
                                "password" to registerViewModel.passwordText.value,
                                "school" to registerViewModel.schoolText.value, // Could be an additional input field
                                "studentid" to registerViewModel.studentIdText.value,
                                "club" to "", // Add logic to handle club input if necessary
                                "email" to registerViewModel.emailPhoneText.value,
                                "great" to "" // Set based on user input
                            )
                            registerViewModel.insertStudent(student)
                            registerViewModel.postData(
                                "http://192.168.0.104:8000/add",
                                studentMap,
                                null,
                                null
                            )
                        }
                        "教师" -> {
                            val teacher = Teacher(
                                phone = registerViewModel.phoneText.value,
                                avatar = "null", // Add logic to handle avatar input
                                username = registerViewModel.usernameText.value,
                                password = registerViewModel.passwordText.value,
                                school = registerViewModel.schoolText.value, // Could be an additional input field
                                position = registerViewModel.studentIdText.value,
                                club = "null", // Add logic to handle club input if necessary
                                clubImage = "null", // Handle club image logic
                                email = registerViewModel.emailPhoneText.value
                            )
                            val teacherMap = mapOf(
                                "type" to "teacher",
                                "phone" to registerViewModel.phoneText.value,
                                "username" to registerViewModel.usernameText.value,
                                "password" to registerViewModel.passwordText.value,
                                "school" to registerViewModel.schoolText.value, // Could be an additional input field
                                "position" to registerViewModel.studentIdText.value,
                                "club" to "", // Add logic to handle club input if necessary
                                "email" to registerViewModel.emailPhoneText.value
                            )
                            registerViewModel.insertTeacher(teacher)
                            registerViewModel.postData(
                                "http://192.168.0.104:8000/add",
                                teacherMap,
                                null,
                                null
                            )
                        }
                        "企业职工" -> {
                            val company = Company(
                                phone = registerViewModel.phoneText.value,
                                avatar = "null", // Add logic to handle avatar input
                                username = registerViewModel.usernameText.value,
                                password = registerViewModel.passwordText.value,
                                company = registerViewModel.schoolText.value, // Could be an additional input field
                                companyId = registerViewModel.studentIdText.value,
                                email = registerViewModel.emailPhoneText.value,
                                great = true
                            )
                            val companyMap = mapOf(
                                "type" to "company",
                                "phone" to registerViewModel.phoneText.value,
                                "username" to registerViewModel.usernameText.value,
                                "password" to registerViewModel.passwordText.value,
                                "company" to registerViewModel.schoolText.value, // Could be an additional input field
                                "companyId" to registerViewModel.studentIdText.value,
                                "email" to registerViewModel.emailPhoneText.value,
                                "great" to "" // Set based on user input
                            )
                            registerViewModel.insertCompany(company)
                            registerViewModel.postData(
                                "http://192.168.0.104:8000/add",
                                companyMap,
                                null,
                                null
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFF00a2e8), shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            ) {
                androidx.compose.material3.Text(
                    text = "注册",
                    color = Color.White,
                    style = TextStyle(fontSize = 20.sp) // 设置字体大小为24sp
                )
            }
        }
        androidx.compose.material3.SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.statusBarsPadding()
        )
    }
}


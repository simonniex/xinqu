package com.example.softcup.presentation.teacher

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.softcup.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.chat.presentation.problem.ProblemScreen
import com.example.core.chat.presentation.talk.ChatScreen
import com.example.core.feedback.FeedBackScreen
import com.example.core.help.AboutUsScreen
import com.example.core.nav.Destination
import com.example.core.nav.Navigator
import com.example.core.setting.SettingScreen
import com.example.softcup.domain.model.BottomNavItem
import com.example.softcup.domain.model.DrawerItemData
import com.example.student.username
import com.example.teacher.presentation.teacherhome.TeacherHomeScreen
import com.example.teacher.presentation.teacherhome.detail.*
import com.example.teacher.presentation.teachermine.TeacherMineScreen
import com.example.teacher.presentation.teachermine.detail.TeacherMineQRScreen
import com.example.teacher.presentation.teachermine.detail.TeacherMineStScreen
import com.example.teacher.presentation.teachermine.detail.TeacherMineXcScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination("student")
@Composable
fun TeacherScreen(navigator: Navigator, viewModel: TeacherViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    // 获取当前目的地,处理回退
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    var currentNavigationIndex by remember { mutableStateOf(0) }
    val scaffoldState = rememberScaffoldState()
    // 观察 LiveData 并在必要时退出应用
    DisposableEffect(viewModel) {
        val observer = Observer<Boolean> { shouldExit ->
            if (shouldExit) {
                (context as? Activity)?.finishAffinity()
                viewModel.resetExitFlag() // 重置退出标志
            }
        }
        viewModel.shouldExit.observeForever(observer)
        onDispose {
            viewModel.shouldExit.removeObserver(observer)
        }
    }
        val items = listOf(
            BottomNavItem("首页", painterResource(R.drawable.ic_home), navController, "teacher_home_route"),
            BottomNavItem("问答", painterResource(R.drawable.ic_problem), navController, "problem_route"),
            BottomNavItem("我的", painterResource(R.drawable.ic_mine), navController, "teacher_mine_route")
        )
        val items_b = listOf(
        painterResource(R.drawable.ic_home_b),
        painterResource(R.drawable.ic_problem_b),
        painterResource(R.drawable.ic_mine_b),
        )
        Scaffold(
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.shadow(20.dp),
                    containerColor = Color.White
                ) {
                    items.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = currentNavigationIndex == index,
                            onClick = {
                                currentNavigationIndex = index
                                // 在此处通过导航控制器导航到相应的目的地
                                navController.navigate(navigationItem.route)
                            },
                            icon = {
                                val iconPainter = if (currentNavigationIndex == index) items_b[index] else navigationItem.icon
                                Icon(
                                    painter = iconPainter,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable(
                                            indication = null, interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            currentNavigationIndex = index
                                            // 在此处通过导航控制器导航到相应的目的地
                                            navController.navigate(navigationItem.route)
                                        },
                                    tint = Color.Unspecified
                                )
                            },
                            label = {
                                androidx.compose.material3.Text(text = navigationItem.title)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.surface
//                            indicatorColor = Color(0xFFEEEEEE)
                            ),
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        ) {
            BackHandler {
                when (currentBackStackEntry?.destination?.route) {
                    "teacher_home_route" -> {
                        // 处理 home_route 的返回逻辑
                        viewModel.requestExit()
                    }
                    "problem_route" -> {
                        // 处理 home_route 的返回逻辑
                        viewModel.requestExit()
                    }
                    "teacher_mine_route" -> {
                        // 处理 home_route 的返回逻辑
                        viewModel.requestExit()
                    }
                    // 添加其他 route 的返回处理逻辑
                    else -> {
                        // 默认返回处理
                        navigator.navigateBack()
                    }
                }
            }
                NavHost(navController = navController, startDestination = "teacher_home_route", modifier = Modifier.padding(it)) {
                    composable("teacher_home_route") {
                        TeacherHomeScreen(navController)
                    }
                    composable("problem_route") {
                        ProblemScreen(username,navController)
                    }
                    composable("teacher_mine_route") {
                        TeacherMineScreen(navController)
                    }
                    composable("teacher_course_detail"){
                        TeacherCourseDetail(navController)
                    }
                    composable("teacher_resource_detail"){
                        TeacherResourceDetail(navController)
                    }
                    composable("teacher_assisant_detail"){
                        TeacherAssistantDetail(navController)
                    }
                    composable("teacher_three_one_screen"){
                        TeacherOneScreen(navController)
                    }
                    composable("teacher_three_two_screen"){
                        TeacherTwoScreen(navController)
                    }
                    composable("teacher_three_screen"){
                        TeacherFourScreen(navController)
                    }
                    composable("teacher_mine_qr"){
                        TeacherMineQRScreen(navController)
                    }
                    composable("teacher_mine_xc"){
                        TeacherMineXcScreen(navController)
                    }
                    composable("teacher_mine_st"){
                        TeacherMineStScreen(navController)
                    }
                    composable("feedback_route"){
                        FeedBackScreen("teacher",username,navController)
                    }
                    composable("about_us"){
                        AboutUsScreen(navController)
                    }
                    composable("Setting_route"){
                        SettingScreen(navController,navigator)
                    }
                    composable(
                        route = "chat_screen/{username}",
                        arguments = listOf(
                            navArgument("username") {
                                type = NavType.StringType
                                nullable = true
                            }
                        )
                    ) { backStackEntry ->
                        // 接收传递的 username
                        val username = backStackEntry.arguments?.getString("username") ?: "Unknown"
                        ChatScreen(username = username,navController)
                    }

                }
        }
}
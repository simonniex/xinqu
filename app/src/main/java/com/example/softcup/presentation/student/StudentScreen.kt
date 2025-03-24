package com.example.softcup.presentation.student

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import com.example.softcup.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.component.CityPicker
import com.example.component.loadJsonFromAssets
import com.example.component.parseProvinces
import com.example.core.chat.presentation.problem.ProblemScreen
import com.example.core.chat.presentation.talk.ChatScreen
import com.example.core.nav.Destination
import com.example.core.nav.Navigator
import com.example.softcup.component.SimonTopAppBar
import com.example.softcup.domain.model.BottomNavItem
import com.example.softcup.domain.model.TopAppBarStyle

import com.example.student.presentation.home.HomeScreen
import com.example.student.presentation.home.detail.ClassDetail
import com.example.student.presentation.home.detail.MyDataDetail
import com.example.student.presentation.home.detail.PractiseDetail
import com.example.student.presentation.home.detail.ResourceDetail
import com.example.student.presentation.mine.MineScreen
import com.example.student.presentation.mine.MineViewModel
import com.example.student.presentation.plan.PlanScreen
import com.example.student.presentation.virtue.VirtueScreen
import com.example.student.presentation.virtue.detail.*
import com.example.student.username
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Destination("student")
@Composable
fun StudentScreen(navigator: Navigator, viewModel: StudentViewModel = viewModel()) {
    val context = LocalContext.current
    // 处理退出逻辑
    LaunchedEffect(viewModel) {
        viewModel.shouldExit.collect { shouldExit ->
            if (shouldExit) {
                (context as? Activity)?.finishAffinity()
                viewModel.resetExitFlag() // 重置退出标志
            }
        }
    }

    StudentNavGraph(
        navController = rememberNavController(),
        viewModel = viewModel,
        navigator = navigator
    )
}

@Composable
fun StudentNavGraph(
    navController: NavHostController,
    viewModel: StudentViewModel,
    navigator: Navigator
) {
    NavHost(
        navController = navController,
        startDestination = "home_route"
    ) {
        composable("home_route") {
            ScaffoldWithBottomNav(navController, viewModel) {
                HomeScreen(navController)
            }
        }
        composable("plan_route") {
            ScaffoldWithBottomNav(navController, viewModel) {
                PlanScreen(
                    tabIndex = viewModel.selectedTabIndex,
                    onTabSelected = { index -> viewModel.updateTabIndex(index) }
                )
            }
        }
        composable("virtue_route") {
            ScaffoldWithBottomNav(navController, viewModel) {
                VirtueScreen(navController)
            }
        }
        composable("problem_route") {
            ScaffoldWithBottomNav(navController, viewModel) {
                ProblemScreen(username,navController)
            }
        }
        composable("mine_route") {
            ScaffoldWithBottomNav(navController, viewModel) {
                MineScreen(viewModel = hiltViewModel<MineViewModel>())
            }
        }
        //student_home
        composable("student_home_class_route"){
            ClassDetail(navController)
        }
        composable("student_home_practise_route"){
            PractiseDetail(navController)
        }
        composable("student_home_resource_route"){
            ResourceDetail(navController)
        }
        composable("student_home_chart_route"){
            MyDataDetail(navController)
        }
        //student_virtue
        composable("student_virtue_heart_route"){
            MentalHealth(navController)
        }
        composable("student_virtue_z_route"){
            VirtueZDetail(navController)
        }
        composable("student_virtue_ccp_route"){
            VirtueCcpDetail(navController)
        }
        composable("student_virtue_people_route"){
            VirtuePeopleDetail(navController)
        }

        composable("student_virtue_heart_Dialog_route"){
            VirtueDialogCard(navController)
        }
        composable("student_virtue_heart_Listen_route"){
            VirtueListenCard(navController)
        }
        composable("student_virtue_heart_counsel_route"){
            VirtueCounsellingCard(navController)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScaffoldWithBottomNav(
    navController: NavHostController,
    viewModel: StudentViewModel,
    content: @Composable () -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // 缓存计算过的结果减少状态更新频率
    var currentNavigationIndex = remember(currentBackStackEntry) {
        when (currentBackStackEntry?.destination?.route) {
            "home_route" -> 0
            "plan_route" -> 1
            "virtue_route" -> 2
            "problem_route" -> 3
            "mine_route" -> 4
            else -> 0
        }
    }

    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isCity by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val provinces = remember(context) { loadJsonFromAssets(context, "city.json")?.let { parseProvinces(it) } ?: emptyList() }

    val items = listOf(
        BottomNavItem("首页", painterResource(R.drawable.ic_home), navController,"home_route"),
        BottomNavItem("规划", painterResource(R.drawable.ic_plan), navController, "plan_route"),
        BottomNavItem("德育", painterResource(R.drawable.ic_virtue), navController, "virtue_route"),
        BottomNavItem("交流", painterResource(R.drawable.ic_problem), navController, "problem_route"),
        BottomNavItem("我的", painterResource(R.drawable.ic_mine), navController, "mine_route")
    )
    val items_a = listOf(
        painterResource(R.drawable.ic_home_a),
        painterResource(R.drawable.ic_plan_a),
        painterResource(R.drawable.ic_virtue_a),
        painterResource(R.drawable.ic_problem_a),
        painterResource(R.drawable.ic_mine_a),
    )
    Scaffold(
        topBar = {
            SimonTopAppBar(
                style = TopAppBarStyle.values()[currentNavigationIndex],
                selectedCity = viewModel.selectedCity,
                onMenuClick = { scope.launch { drawerState.open() } },
                onCityClick = {
                    isCity = true
                    scope.launch { bottomSheetState.show() }
                },
                selectedTabIndex = viewModel.selectedTabIndex,
                onTabSelected = { index -> viewModel.updateTabIndex(index) }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.shadow(20.dp),
                containerColor = Color.White
            ) {
                items.forEachIndexed { index, navigationItem ->
                    val iconPainter = if (currentNavigationIndex == index) items_a[index] else navigationItem.icon
                    NavigationBarItem(
                        selected = currentNavigationIndex == index,
                        onClick = {
                            if (navController.currentDestination?.route != navigationItem.route) {
                                navController.navigate(navigationItem.route) {
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = iconPainter,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable(
                                        indication = null, interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        if (navController.currentDestination?.route != navigationItem.route) {
                                            navController.navigate(navigationItem.route) {
                                                launchSingleTop = true
                                            }
                                        }
                                    },
                                tint = Color.Unspecified
                            )
                        },
                        label = {
                            val textColor = if (currentNavigationIndex == index) Color(0xff00a2e8) else Color.Black
                            androidx.compose.material3.Text(
                                text = navigationItem.title,
                                color = textColor
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.surface
                        ),
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { paddingValues ->

        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
        LaunchedEffect(currentBackStackEntry) {
            currentNavigationIndex = when (currentBackStackEntry?.destination?.route) {
                "home_route" -> 0
                "plan_route" -> 1
                "virtue_route" -> 2
                "problem_route" -> 3
                "mine_route" -> 4
                else -> 0
            }
        }
        BackHandler {
            when (currentBackStackEntry?.destination?.route) {
                "home_route", "plan_route","virtue_route", "problem_route","mine_route" -> {
                    viewModel.requestExit()
                }
                else -> {
                    if (!navController.popBackStack()) {
                        viewModel.requestExit()
                    }
                }
            }
        }
        if (isCity) {
            CityPicker(
                provinces = provinces,
                bottomSheetState = bottomSheetState,
                onDismiss = { isCity = false },
                onCitySelected = { _, _, district ->
                    viewModel.updateCity(district)
                }
            )
        }
    }

}

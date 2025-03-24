package com.example.softcup.presentation.company

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.company.presentation.companyhome.CompanyHomeScreen
import com.example.company.presentation.companyhome.club.ClubDetail
import com.example.company.presentation.companyhome.identity.IdentityDeatail
import com.example.company.presentation.companysupport.CompanySupportScreen
import com.example.core.chat.presentation.problem.ProblemScreen
import com.example.core.chat.presentation.talk.ChatScreen
import com.example.core.nav.CompanyRoutes
import com.example.core.nav.Destination
import com.example.core.nav.Navigator
import com.example.core.other.util.FadeInOutScreen
import com.example.softcup.R
import com.example.softcup.domain.model.BottomNavItem
import com.example.student.username

@Destination("company")
@Composable
fun CompanyScreen(navigator: Navigator, viewModel: CompanyViewModel = viewModel()){
    val navController = rememberNavController()
    val context = LocalContext.current

    // 处理退出逻辑
    DisposableEffect(viewModel) {
        val observer = Observer<Boolean> { shouldExit ->
            if (shouldExit) {
                (context as? Activity)?.finishAffinity()
                viewModel.resetExitFlag()
            }
        }
        viewModel.shouldExit.observeForever(observer)
        onDispose {
            viewModel.shouldExit.removeObserver(observer)
        }
    }

    CompanyNavGraph(navController = navController, viewModel = viewModel)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CompanyNavGraph(
    navController: NavHostController,
    viewModel: CompanyViewModel
) {
    NavHost(
        navController = navController,
        startDestination = CompanyRoutes.HOME,
    ) {
        composable(CompanyRoutes.HOME) {
            ScaffoldWithBottomNav(navController, viewModel) {
                CompanyHomeScreen(navController)
            }
        }
        composable(CompanyRoutes.SUPPORT) {
            ScaffoldWithBottomNav(navController, viewModel) {
                CompanySupportScreen(navController)
            }
        }
        composable(CompanyRoutes.PROBLEM) {
            ScaffoldWithBottomNav(navController, viewModel) {
                ProblemScreen(username,navController)
            }
        }
        //Detail
        composable(
            route = "company_home_club_route/{title}/{imageRes}/{phone}/{time}/{people}/{content}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("imageRes") { type = NavType.IntType },
                navArgument("phone") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("people") { type = NavType.IntType },
                navArgument("content") { type = NavType.StringType }
            )
        ) {
                // 获取传递过来的参数
                val clubName = it.arguments?.getString("title") ?: "Unknown"
                val imageRes = it.arguments?.getInt("imageRes") ?: 0
                val phone = it.arguments?.getString("phone") ?: "Unknown"
                val time = it.arguments?.getString("time") ?: "Unknown"
                val people = it.arguments?.getInt("people") ?: 0
                val content = it.arguments?.getString("content") ?: "No Content"

                ClubDetail(clubName, imageRes, phone, time, people, content, navController)
        }

        composable(
            route = "company_home_identity_route/{identity}",
            arguments = listOf(
                navArgument("identity") { type = NavType.StringType }
            )
        ){
            val identityName = it.arguments?.getString("identity") ?: "Unknown"
            IdentityDeatail(identityName,navController)
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
@Composable
fun ScaffoldWithBottomNav(
    navController: NavHostController,
    viewModel: CompanyViewModel,
    content: @Composable () -> Unit
) {
    // 获取当前目的地,处理回退
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // 根据当前目的地路由动态更新 currentNavigationIndex
    var currentNavigationIndex by remember {
        mutableStateOf(
            when (navController.currentDestination?.route) {
                "company_home_route" -> 0
                "company_support_route" -> 1
                "problem_route" -> 2
                else -> 0
            }
        )
    }

    val items = listOf(
        BottomNavItem("首页", painterResource(R.drawable.ic_home), navController, "company_home_route"),
        BottomNavItem("支持", painterResource(R.drawable.ic_support), navController, "company_support_route"),
        BottomNavItem("交流", painterResource(R.drawable.ic_problem), navController, "problem_route")
    )
    val items_c = listOf(
        painterResource(R.drawable.ic_home_c),
        painterResource(R.drawable.ic_support_a),
        painterResource(R.drawable.ic_problem_c),
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.shadow(20.dp),
                containerColor = Color.White
            ) {
                items.forEachIndexed { index, navigationItem ->
                    val iconPainter = if (currentNavigationIndex == index) items_c[index] else navigationItem.icon
                    NavigationBarItem(
                        selected = currentNavigationIndex == index,
                        onClick = {
                            // 通过判断目标页面来决定是否导航
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
                            val textColor = if (currentNavigationIndex == index) Color(0xff004ec4) else Color.Black
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
    ) {
        LaunchedEffect(currentBackStackEntry) {
            currentNavigationIndex = when (currentBackStackEntry?.destination?.route) {
                "company_home_route" -> 0
                "company_support_route" -> 1
                "problem_route" -> 2
                else -> 0
            }
        }

        // 处理返回按钮的逻辑
        BackHandler {
            when (currentBackStackEntry?.destination?.route) {
                "company_home_route", "company_support_route", "problem_route" -> {
                    viewModel.requestExit()
                }
                else -> {
                    if (!navController.popBackStack()) {
                        viewModel.requestExit()
                    }
                }
            }
        }
        content()
    }
}

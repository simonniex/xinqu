package com.example.softcup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.company.presentation.companyhome.club.ClubDetail
import com.example.core.nav.Navigator
import com.example.core.nav.Route
import com.example.softcup.presentation.company.CompanyScreen
import com.example.softcup.presentation.start.login.LoginScreen
import com.example.softcup.presentation.start.StartScreen
import com.example.softcup.presentation.start.register.RegisterScreen
import com.example.softcup.presentation.student.StudentScreen
import com.example.softcup.presentation.teacher.TeacherScreen

@Composable
fun NavHostWithRoutes(navController: NavHostController) {
    val navigator = rememberNavigator(navController)  // 使用 remember 创建 Navigator
    NavHost(navController = navController, startDestination = Route.Start) {
        composable(Route.Start) { StartScreen(navigator) }
        composable(Route.Login) { LoginScreen(navigator) }
        composable(Route.Student) { StudentScreen(navigator) }
        composable(Route.Teacher) { TeacherScreen(navigator) }
        composable(Route.Company) { CompanyScreen(navigator) }
        composable(Route.Register) { RegisterScreen(navigator) }
    }
}

@Composable
fun rememberNavigator(navController: NavHostController): Navigator {
    return remember(navController) {
        Navigator(navController)
    }
}

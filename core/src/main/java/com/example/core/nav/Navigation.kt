package com.example.core.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class NavigationCommand {
    data class Navigate(val route: String) : NavigationCommand()
    object Back : NavigationCommand()
}

class Navigator(
    private val navController: NavHostController
) {
    fun navigateTo(route: String, addToBackStack: Boolean = true) {
        if (addToBackStack) {
            navController.navigate(route)
        } else {
            navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = false
                }
            }
        }
    }


    fun navigateToHome() {
        navController.navigate(Route.Start) {
            // 清空回退栈，返回到主页
            popUpTo(0) { inclusive = true } // 清空所有栈
            launchSingleTop = true // 确保不创建新的实例
            restoreState = true // 恢复状态
        }
    }

    fun navigateToProfile() {
        navController.navigate(Route.Student)
    }

    fun navigateToAdmin() {
        navController.navigate(Route.Admin)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
    // 添加获取当前 NavBackStackEntry 的方法
    fun getCurrentBackStackEntry(): NavBackStackEntry? {
        return navController.currentBackStackEntry
    }
}

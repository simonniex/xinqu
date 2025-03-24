package com.example.softcup.domain.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController


data class BottomNavItem(
    val title: String,
    var icon: Painter,
    val navController: NavController,
    val route: String
)
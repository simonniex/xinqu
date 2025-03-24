package com.example.core.other.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FadeInOutScreen(content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    // 控制页面淡入淡出
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) ,
        exit =  fadeOut(animationSpec = tween(300))
    ) {
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlideTransition(content: @Composable () -> Unit) {
    AnimatedContent(
        targetState = content,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth }, // 从右边进入
                animationSpec = tween(durationMillis = 2000) // 动画时间设为1000ms
            ) with slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth }, // 向左边退出
                animationSpec = tween(durationMillis = 2000) // 动画时间设为1000ms
            )
        }
    ) {
        content()
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScaleTransition(content: @Composable () -> Unit) {
    AnimatedContent(
        targetState = content,
        transitionSpec = {
            scaleIn(animationSpec = tween(3000)) with scaleOut(animationSpec = tween(3000))
        }
    ) {
        content()
    }
}
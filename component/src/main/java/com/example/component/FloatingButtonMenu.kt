package com.example.component

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlin.math.*


enum class Style

enum class AnimationEffect {
    LIQUID_EXPANSION,
    TOP,
    CENTER
}

data class MenuItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
data class MenuAnimationValues(
    val offsetX: Dp,
    val offsetY: Dp,
    val scale: Float,
    val alpha: Float
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FloatingButtonMenu(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    animationEffect: AnimationEffect,
    adapter: MenuItemAdapter = DefaultMenuItemAdapter(),
    content: @Composable FloatingMenuScope.() -> Unit
) {
    // State for tracking whether the menu is open
    var menuOpen by remember { mutableStateOf(false) }

    // State for tracking the scale of the main FAB
    var fabScaleDown by remember { mutableStateOf(false) }

    // Rotation state for the main FAB
    val rotation by animateFloatAsState(
        targetValue = if (menuOpen) 45f else 0f
    )

    // Scale state for the main FAB
    val fabScale by animateFloatAsState(
        targetValue = if (fabScaleDown) 0.8f else 1f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    // Collect all menu items added in the content scope
    val menuItems = remember { mutableStateListOf<MenuItem>() }
    val scope = remember { FloatingMenuScopeImpl(menuItems) }

    content(scope)

//    // State for tracking visibility of each menu item
//    val visibilityStates = remember {
//        derivedStateOf {
//            menuItems.map { menuOpen }
//        }
//    }

    // State for tracking FAB position
    var fabOffsetX by remember { mutableStateOf(0f) }
    var fabOffsetY by remember { mutableStateOf(0f) }

    // Create a scaffold for displaying the floating action button and its menu
//    Scaffold(
//        modifier = modifier,
//        floatingActionButton = {
            Box(
                modifier = modifier
//                    .fillMaxSize()
                    .offset { IntOffset(fabOffsetX.roundToInt(), fabOffsetY.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            fabOffsetX += dragAmount.x
                            fabOffsetY += dragAmount.y
                        }
                    },
//                contentAlignment = Alignment.BottomEnd
            ) {

                menuItems.forEachIndexed { index, item ->
                    val delay = (index + 1) * 100
                    val radius = 100.dp
                    val angleStep = 45f
                    val angle = 175+index * angleStep
                    val offsetX: Dp
                    val offsetY: Dp
                    val offset: Dp
                    val scale: Float
                    val alpha: Float
                    when (animationEffect) {
                        AnimationEffect.LIQUID_EXPANSION->{
                            offsetX = animateDpAsState(
                                targetValue = if (menuOpen) (radius * cos(Math.toRadians(angle.toDouble())).toFloat()) else 0.dp,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                            offsetY = animateDpAsState(
                                targetValue = if (menuOpen) (radius * sin(Math.toRadians(angle.toDouble())).toFloat()) else 0.dp,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                            scale = animateFloatAsState(
                                targetValue = if (menuOpen) 1f else 0f,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                            alpha = animateFloatAsState(
                                targetValue = if (menuOpen) 1f else 0f,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                        }
                        AnimationEffect.TOP->{
                            offset = animateDpAsState(
                                targetValue = if (menuOpen) (index + 1) * (-60).dp else 0.dp,
                                animationSpec = tween(durationMillis = 300, delayMillis = delay)
                            ).value
                            scale = animateFloatAsState(
                                targetValue = if (menuOpen) 1f else 0f,
                                animationSpec = tween(durationMillis = 300, delayMillis = delay)
                            ).value
                            alpha = animateFloatAsState(
                                targetValue = if (menuOpen) 1f else 0f,
                                animationSpec = tween(durationMillis = 300, delayMillis = delay)
                            ).value
                            offsetX = offset // 使用相同的值来填充 offsetX 和 offsetY 以避免编译错误
                            offsetY = offset // 使用相同的值来填充 offsetX 和 offsetY 以避免编译错误
                        }
                        AnimationEffect.CENTER -> {
                            val targetX = ((index % 3) * 80).dp // 每行三个按钮
                            val targetY = ((index / 3) * 80).dp // 换行
                            offsetX = animateDpAsState(
                                targetValue = if (menuOpen) targetX - (index / 3 * 240).dp else 0.dp,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                            offsetY = animateDpAsState(
                                targetValue = if (menuOpen) targetY else 0.dp,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                            scale = animateFloatAsState(
                                targetValue = if (menuOpen) 1f else 0f,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                            alpha = animateFloatAsState(
                                targetValue = if (menuOpen) 1f else 0f,
                                animationSpec = tween(durationMillis = 600, delayMillis = delay)
                            ).value
                        }
                    }

                    adapter.MenuItemContent(
                        item = item,
                        onClick = {
                            item.onClick()
                            menuOpen = false
                            fabScaleDown = false
                        },
                        modifier = if (animationEffect == AnimationEffect.LIQUID_EXPANSION) {
                            Modifier
                                .offset(x = offsetX, y = offsetY)
                                .scale(scale)
                                .alpha(alpha)
                                .padding(20.dp)
                        } else {
                            Modifier
                                .offset(y = if (orientation == Orientation.Vertical) offsetY else 0.dp, x = if (orientation == Orientation.Horizontal) offsetX else 0.dp)
                                .scale(scale)
                                .alpha(alpha)
                                .padding(20.dp)
                        }
                    )
                }

                FloatingActionButton(
                    onClick = {
                        fabScaleDown = !fabScaleDown
                        menuOpen = !menuOpen
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(20.dp)
                        .scale(fabScale)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Menu",
                        modifier = Modifier.rotate(rotation)
                    )
                }
            }
//        floatingActionButtonPosition = FabPosition.Center,
//        content = {}
}


interface MenuItemAdapter {
    @Composable
    fun MenuItemContent(item: MenuItem, onClick: () -> Unit, modifier: Modifier)
}

class DefaultMenuItemAdapter : MenuItemAdapter {
    @Composable
    override fun MenuItemContent(item: MenuItem, onClick: () -> Unit, modifier: Modifier) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            modifier = modifier
        ) {
            Icon(item.icon, contentDescription = item.label)
        }
    }
}

interface FloatingMenuScope {
    fun menuItem(label: String, icon: ImageVector, onClick: () -> Unit)
}

class FloatingMenuScopeImpl(
    private val menuItems: MutableList<MenuItem>
) : FloatingMenuScope {
    override fun menuItem(label: String, icon: ImageVector, onClick: () -> Unit) {
        if (menuItems.none { it.label == label && it.icon == icon }) {
            menuItems.add(MenuItem(label, icon, onClick))
        }
    }
}

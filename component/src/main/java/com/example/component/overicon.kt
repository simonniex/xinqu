package com.example.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FlippingCard(
    frontIcon: ImageVector,
    backIcon: ImageVector,
    backgroundColor: Color = Color.White
) {
    var rotated by remember { mutableStateOf(false) }

    // Animation for rotation
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(500)
    )

    // Animation for front and back icons
    val animateFront by animateFloatAsState(
        targetValue = if (!rotated) 1f else 0f,
        animationSpec = tween(500)
    )
    val animateBack by animateFloatAsState(
        targetValue = if (rotated) 1f else 0f,
        animationSpec = tween(500)
    )

    // Animation for elevation
    val elevation by animateDpAsState(
        targetValue = if (rotated) 4.dp else 10.dp,
        animationSpec = tween(500)
    )

    Card(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density
            }
            .clickable {
                rotated = !rotated
            },
        shape = RoundedCornerShape(14.dp),
        elevation = elevation,
        backgroundColor = backgroundColor,
        contentColor = Color.White
    ) {
        if (!rotated) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        imageVector = frontIcon,
                        contentDescription = "Front Icon",
                        modifier = Modifier.graphicsLayer { alpha = animateFront },
                        tint = Color.White
                    )
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        imageVector = backIcon,
                        contentDescription = "Back Icon",
                        modifier = Modifier.graphicsLayer { alpha = animateBack },
                        tint = Color.White
                    )
                }
            }
        }
    }
}




package com.example.component

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimonTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit
) {
    val indicatorColor = Color(0xFFA8D8F8).copy(alpha = 0.5f) // 半透明颜色
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp),
        color = Color.White
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .fillMaxHeight()
                        .background(indicatorColor, shape = RoundedCornerShape(30.dp))
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            modifier = Modifier
                .background(Color.Transparent, shape = RoundedCornerShape(30.dp))
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp), // 调整高度
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                )
            }
        }
    }
}

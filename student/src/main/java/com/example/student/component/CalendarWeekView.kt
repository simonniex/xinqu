package com.example.student.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student.domain.model.CalendarDay
import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarWeekView(
    days: List<CalendarDay>,
    selectedDate: LocalDate,
    currentDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        days.forEach { day ->
            val isToday = day.date == currentDate
            val isSelected = day.date == selectedDate
            val backgroundColor = if (isSelected) Color(0xFF004ec4) else Color.Transparent

            // 边框颜色：只有当天日期且未被选中时显示边框
            val borderColor = if (isToday && !isSelected) Color(0xFF004ec4) else Color.Transparent

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor)
                    .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        // 当点击当天日期时，切换到选中状态，边框消失
                        onDateSelected(day.date)
                    }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    fontSize = 14.sp,
                    color = if (isSelected) Color.Black else Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = day.date.dayOfMonth.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = day.lunar,
                    fontSize = 12.sp,
                    color = if (isSelected) Color.White else Color.Gray,
                    textAlign = TextAlign.Center
                )
                if (day.festival != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = day.festival,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White else Color(0xFF004ec4),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
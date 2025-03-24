package com.example.student.util

import kotlinx.datetime.LocalDate

// 动态获取农历日期的函数
fun getLunarDate(date: LocalDate): String {
    // 使用 ChineseCalendar 获取农历日期
    val calendar = java.util.Calendar.getInstance()
    calendar.set(date.year, date.monthNumber - 1, date.dayOfMonth)
    val chineseCalendar = android.icu.util.ChineseCalendar()
    chineseCalendar.timeInMillis = calendar.timeInMillis

    val lunarMonth = chineseCalendar.get(android.icu.util.ChineseCalendar.MONTH) + 1
    val lunarDay = chineseCalendar.get(android.icu.util.ChineseCalendar.DAY_OF_MONTH)

    return "${getLunarMonthName(lunarMonth)}${getLunarDayName(lunarDay)}"
}

// 获取农历月份名称
fun getLunarMonthName(month: Int): String {
    val months = arrayOf("正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月")
    return months[month - 1]
}

// 获取农历日期名称
fun getLunarDayName(day: Int): String {
    val days = arrayOf(
        "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
        "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
        "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
    )
    return days[day - 1]
}

// 动态获取节日信息的函数
fun getFestival(date: LocalDate, lunarDate: String): String? {
    // 公历节日
    val solarFestivals = mapOf(
        "01-01" to "元旦",
        "05-01" to "劳动节",
        "09-10" to "教师节",
        "09-17" to "中秋节",
        "10-01" to "国庆节",
        "10-11" to "重阳节"
    )

    // 农历节日
    val lunarFestivals = mapOf(
        "正月初一" to "春节",
        "五月初五" to "端午节",
        "八月十五" to "中秋节"
    )

    // 二十四节气
    val solarTerms = mapOf(
        "02-04" to "立春",
        "02-19" to "雨水",
        "03-05" to "惊蛰",
        "03-20" to "春分",
        "04-05" to "清明",
        "04-20" to "谷雨",
        "05-06" to "立夏",
        "05-21" to "小满",
        "06-06" to "芒种",
        "06-21" to "夏至",
        "07-07" to "小暑",
        "07-22" to "大暑",
        "08-07" to "立秋",
        "08-22" to "处暑",
        "09-08" to "白露",
        "09-23" to "秋分",
        "10-08" to "寒露",
        "10-23" to "霜降",
        "11-07" to "立冬",
        "11-22" to "小雪",
        "12-07" to "大雪",
        "12-21" to "冬至",
        "01-05" to "小寒",
        "01-20" to "大寒"
    )

    // 检查公历节日
    val solarKey = "%02d-%02d".format(date.monthNumber, date.dayOfMonth)
    solarFestivals[solarKey]?.let { return it }

    // 检查二十四节气
    solarTerms[solarKey]?.let { return it }

    // 检查农历节日
    return lunarFestivals[lunarDate]
}

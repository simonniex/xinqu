package com.example.component.data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerialName
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@Serializable
data class Area(val areaCode: String, val areaName: String)

@Serializable
data class City(
    @SerialName("cityCode") val cityCode: String,
    @SerialName("cityName") val name: String,
    @SerialName("mallAreaList") val districts: List<Area> // 确保与JSON中的键相匹配
)

@Serializable
data class Province(
    @SerialName("provinceCode") val provinceCode: String,
    @SerialName("provinceName") val name: String,
    @SerialName("mallCityList") val cities: List<City> // 这里应该是mallCityList而不是mallAreaList
)

fun Context.readJsonFromAsset(fileName: String): String {
    return assets.open(fileName).use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.readText()
        }
    }
}

fun Context.parseProvincesFromJson(): List<Province> {
    val json = readJsonFromAsset("data.json")
    return Json.decodeFromString<List<Province>>(json)
}

// 使用示例
fun useParsedData(context: Context) {
    val provinces = context.parseProvincesFromJson()
    // 现在可以使用provinces列表进行进一步处理
}
// 现在您可以使用provinces列表进行进一步处理


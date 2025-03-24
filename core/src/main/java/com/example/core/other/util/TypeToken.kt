package com.example.core.other.util


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// 定义一个公共的 TypeToken 函数
inline fun <reified T> getTypeToken(): TypeToken<T> {
    return object : TypeToken<T>() {}
}

inline fun <reified T> parseJson(json: String, gson: Gson): T {
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson<T>(json, type)
}
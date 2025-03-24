package com.example.core.other.util.internet

import com.example.core.other.util.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

val client = OkHttpClient.Builder()
    .cache(Cache(File("cacheDir", "http_cache"), 10 * 1024 * 1024)) // 10MB 缓存
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

val gson = Gson()

// 可选：全局token变量
var token = "Bearer tKMLdRDhTbWNBzASpfPS:oSqnrkZTbBPqbsurJkvV"

// 通用的网络请求执行函数（支持文件上传）
inline fun <reified T> executeRequest(
    method: String,
    url: String,
    body: Map<String, Any>? = null,
    avatarFile: File? = null,
    clubFile: File? = null
): Flow<ResultState<T>> = flow {
    emit(ResultState.Loading)
    try {
        val request = buildMultipartRequest(url, method, body, avatarFile, clubFile)
        val result = client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            response.body?.string()?.let { gson.fromJson(it, T::class.java) }
                ?: throw IOException("Empty response body")
        }
        emit(ResultState.Success(result))
    } catch (e: Exception) {
        emit(ResultState.Error(exception = e, message = e.localizedMessage))
    }
}.flowOn(Dispatchers.IO)

// 构建 Multipart 请求体，支持上传文件和表单字段
fun buildMultipartRequest(
    url: String,
    method: String,
    body: Map<String, Any>? = null,
    avatarFile: File? = null,
    clubFile: File? = null
): Request {
    val requestBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)

    // 添加头像文件到请求体
    avatarFile?.let {
        val fileBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
        requestBodyBuilder.addFormDataPart("avatar", it.name, fileBody)
    }

    // 添加俱乐部图片文件到请求体
    clubFile?.let {
        val fileBody = it.asRequestBody("image/jpeg".toMediaTypeOrNull())
        requestBodyBuilder.addFormDataPart("club_image", it.name, fileBody)
    }

    // 添加其他字段到请求体
    body?.forEach { (key, value) ->
        requestBodyBuilder.addFormDataPart(key, value.toString())
    }

    val requestBody = requestBodyBuilder.build()
    return buildRequest(url, method, requestBody)
}

// 通用的请求构建器
fun buildRequest(url: String, method: String, body: RequestBody?): Request {
    return Request.Builder().url(url).method(method, body).apply {
        if (token.isNotEmpty()) {
            addHeader("Authorization", token)
        }
    }.addHeader("Content-Type", "multipart/form-data").build()
}

// DSL 风格的请求方法，便于流畅调用
inline fun <reified T> get(url: String) = executeRequest<T>("GET", url)

inline fun <reified T> post(
    url: String,
    body: Map<String, Any>? = null,
    avatarFile: File? = null,
    clubFile: File? = null
): Flow<ResultState<T>> {
    return executeRequest(method = "POST", url = url, body = body, avatarFile = avatarFile, clubFile = clubFile)
}

inline fun <reified T> put(
    url: String,
    body: Map<String, Any>? = null,
    avatarFile: File? = null,
    clubFile: File? = null
): Flow<ResultState<T>> {
    return executeRequest(method = "PUT", url = url, body = body, avatarFile = avatarFile, clubFile = clubFile)
}

inline fun <reified T> delete(url: String) = executeRequest<T>("DELETE", url)

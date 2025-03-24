package com.example.core.other.util.internet

import com.example.core.data.model.*
import com.example.core.feedback.FeedBackData
import com.example.core.other.util.parseJson
import com.example.core.state.userState
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.time.LocalDateTime
import java.util.regex.Pattern

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(json: JsonElement, typeOfT: java.lang.reflect.Type, context: JsonDeserializationContext): LocalDateTime {
        return LocalDateTime.parse(json.asString)
    }
}

// 通用的网络请求函数，支持不同的返回类型
suspend fun <T> fetchData(
    url: String,
    parseResponse: (String) -> T // 高阶函数，用来解析响应数据
): T {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    return withContext(Dispatchers.IO) {
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val body = response.body?.string() ?: throw IOException("Empty response body")
            parseResponse(body) // 使用传入的解析逻辑
        }
    }
}

suspend fun fetchNews(): List<NewsItem> {
    val url = "http://v.juhe.cn/toutiao/index?key=f54459875d1b630caff79968ce3c736f&type=top"

    return fetchData(url) { body ->
        val newsResponse: NewsResponse = parseJson(body, gson)
        // 返回解析后的新闻数据
        newsResponse.result.data.map {
            NewsItem(
                title = it.title,
                author_name = it.author_name,
                date = it.date,
                url = it.url,
                thumbnail_pic_s = it.thumbnail_pic_s,
                thumbnail_pic_s02 = it.thumbnail_pic_s02,
                thumbnail_pic_s03 = it.thumbnail_pic_s03
            )
        }
    }
}

suspend fun fetchBook(isbn :String): Book {
    val url = "http://api.tanshuapi.com/api/isbn/v1/index?key=a5d2ebaa876e6cb0a36558053971eedd&isbn=$isbn" // 替换为你的API地址

    return fetchData(url) { body ->
        val bookResponse: BookResponse = parseJson(body, gson)
        // 返回解析后的书籍数据
        Book(
            title = bookResponse.data.title,
            img = bookResponse.data.img,
            author = bookResponse.data.author,
            isbn = bookResponse.data.isbn,
            isbn10 = bookResponse.data.isbn10,
            publisher = bookResponse.data.publisher,
            pubdate = bookResponse.data.pubdate,
            keyword = bookResponse.data.keyword,
            pages = bookResponse.data.pages,
            price = bookResponse.data.price,
            binding = bookResponse.data.binding,
            summary = bookResponse.data.summary
        )
    }
}



suspend fun fetchDaily(): String {
    val url = "https://apis.juhe.cn/fapig/soup/query?key=8590d0c008cb88fcea2af9bf7b43975e"
    return fetchData(url) { responseBody ->
        val gson = Gson()
        val dailyResponse = gson.fromJson(responseBody, DailyResponse::class.java)
        dailyResponse.result.text // 提取文本字段
    }
}

suspend fun fetchText(): List<TextItem> {
    val ip = userState.geIp()
    println(ip)
    val url = "http://$ip:8001/get-items/"
    return fetchData(url) { responseBody ->
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
        val type = object : TypeToken<List<TextItem>>() {}.type
        gson.fromJson(responseBody, type)
    }
}

suspend fun fetchText1(keyword: String): List<TextItem> {
    val ip = userState.geIp()
    println(ip)
    val url = "http://$ip:8001/get-item-by-keyword/?keyword=$keyword"
    return fetchData(url) { responseBody ->
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
        val type = object : TypeToken<List<TextItem>>() {}.type
        gson.fromJson(responseBody, type)
    }
}

suspend fun fetchCourse(keyword: String): List<CourseData> {
    val ip = userState.geIp()
    println(ip)
    val url = "http://$ip:8002/courses/$keyword"
    return fetchData(url) { responseBody ->
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
        val type = object : TypeToken<List<CourseData>>() {}.type
        gson.fromJson(responseBody, type)
    }
}

suspend fun fetchVideos(): List<Video> {
    val ip = userState.geIp()
    println(ip)
    val url = "http://$ip:8003/videos/"
    return fetchData(url) { responseBody ->
        val gson = GsonBuilder().create()
        val type = object : TypeToken<List<Video>>() {}.type
        gson.fromJson(responseBody, type)
    }
}



suspend fun fetchQR(text:String): String? {
    val url = "https://api.cl2wm.cn/api/qrcode/code?text=${text}&mhid=4ROVCFu4nJ4hMHYpKtVXMK0"
    return fetchData1(url) { responseBody ->
        extractQRCodeUrl(responseBody)
    }
}

private suspend fun fetchData1(url: String, parser: (String) -> String?): String? {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && responseBody != null) {
            parser(responseBody)
        } else {
            null
        }
    }
}

private fun extractQRCodeUrl(html: String): String? {
    val regex = """<img\s+[^>]*src="([^"]+)"[^>]*>"""
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(html)

    return if (matcher.find()) {
        matcher.group(1)?.let { src ->
            if (!src.startsWith("http")) {
                "https:$src"
            } else {
                src
            }
        }
    } else {
        null
    }
}


suspend fun fetchFeedBack(identity: String, username: String): List<FeedBackData>? {
    val ip = userState.geIp()
    println(ip)
    val url = "http://$ip:8004/fankui?identity=$identity&people=$username"
    return fetchData(url) { responseBody ->
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
        val type = object : TypeToken<List<FeedBackData>>() {}.type
        gson.fromJson(responseBody, type)
    }
}

suspend fun fetchTalkList(): List<TalkListItem>? {
    val ip = userState.geIp()
    println(ip)
    val url = "http://$ip:8005/talks"
    return fetchData(url) { responseBody ->
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create()
        val type = object : TypeToken<List<TalkListItem>>() {}.type
        gson.fromJson(responseBody, type)
    }
}





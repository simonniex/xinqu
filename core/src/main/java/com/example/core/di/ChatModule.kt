package com.example.core.di

import com.example.core.chat.remote.other.ChatSocketService
import com.example.core.chat.remote.other.ChatSocketServiceImpl
import com.example.core.chat.remote.other.MessageService
import com.example.core.chat.remote.other.MessageServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object ChatModule {
//
//    @Provides
//    @Singleton
//    fun provideHttpClient(): HttpClient {
//        return HttpClient(CIO) {
//            install(Logging)
//            install(WebSockets)
//            install(JsonFeature) {
//                serializer = KotlinxSerializer()
//            }
//        }
//    }
//
//    @Provides
//    @Singleton
//    fun provideHttpClientProvider(httpClient: HttpClient): HttpClientProvider {
//        return HttpClientProvider(httpClient)
//    }
//}
//
//class HttpClientProvider @Inject constructor(val httpClient: HttpClient) {
//
//    suspend inline fun <reified T> get(url: String): T? {
//        return withContext(Dispatchers.IO) {
//            try {
//                httpClient.get<T>(url)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//        }
//    }
//}

package com.example.student.presentation.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.model.Video
import com.example.core.other.util.internet.fetchVideos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResourceDetailViewModel : ViewModel() {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()

    init {
        fetchVideos1()
    }

    private fun fetchVideos1() {
        // 这里可以调用网络请求获取视频数据
        viewModelScope.launch {
            val videoList = fetchVideos() // 该函数应调用 API 获取视频列表
            _videos.value = videoList
        }
    }

}

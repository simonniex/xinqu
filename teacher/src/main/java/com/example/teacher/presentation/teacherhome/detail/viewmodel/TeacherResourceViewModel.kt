package com.example.teacher.presentation.teacherhome.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.other.util.internet.sendVideo
import com.example.core.state.userState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeacherResourceViewModel : ViewModel() {

    private val _resourceTitle = MutableStateFlow("")
    val resourceTitle: StateFlow<String> = _resourceTitle

    private val _resourceLink = MutableStateFlow("")
    val resourceLink: StateFlow<String> = _resourceLink

    private val _imageLink = MutableStateFlow("")
    val imageLink: StateFlow<String> = _imageLink

    fun updateResourceTitle(title: String) {
        _resourceTitle.value = title
    }

    fun updateResourceLink(link: String) {
        _resourceLink.value = link
    }

    fun updateImageLink(link: String) {
        _imageLink.value = link
    }

    fun publishResource() {
        viewModelScope.launch {
            val ip = userState.geIp()
            println(ip)
            // 在这里实现资源发布的逻辑
            val title = _resourceTitle.value
            val link = _resourceLink.value
            val image = _imageLink.value

            if (title.isNotBlank() && link.isNotBlank() && image.isNotBlank()) {

                if (ip != null) {
                    sendVideo(title, link, image,ip)
                }
                println("发布资源: $title, 链接: $link, 图片链接: $image")
                // 清空输入
                _resourceTitle.value = ""
                _resourceLink.value = ""
                _imageLink.value = ""
            } else {
                println("请确保所有字段都已填写")
            }
        }
    }
}
//信息安全
//https://www.icourse163.org/course/FUDAN-1206357811
//https://edu-image.nosdn.127.net/79F9C9AD8A8EEE5D518347EB08F9BA8D.png?imageView&thumbnail=510y288&quality=100

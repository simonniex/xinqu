package com.example.student.presentation.plan

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dao.CardDataEntity
import com.example.core.dao.DiaryRepository
import com.example.core.data.model.CardType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    // 输入框状态
    var textFieldValue by mutableStateOf("")

    var selectedImageUri by mutableStateOf<String?>(null)


    private val _cardData = MutableStateFlow<List<CardDataEntity>>(emptyList()) // 改为列表
    val cardData: StateFlow<List<CardDataEntity>> = _cardData

    // 获取当天的日记
    fun getCardDataForDay(dayOfWeek: String) {
        viewModelScope.launch {
            val data = diaryRepository.getCardDataForDay(dayOfWeek)
            _cardData.value = data // 更新为列表
        }
    }
    // 更新输入框内容
    fun onTextFieldValueChange(newValue: String) {
        textFieldValue = newValue
    }

    // 更新图片选择状态
    fun onImageSelected(uri: String?) {
        selectedImageUri = uri
    }
    fun resetFields() {
        textFieldValue = ""
        selectedImageUri = null
    }


    // 插入或更新日记
    fun insertOrUpdateCardData(cardData: CardDataEntity) {
        viewModelScope.launch {
            diaryRepository.insertCardData(cardData)
        }
    }

    // 清除上周的记录
    fun clearOldEntries() {
        viewModelScope.launch {
            diaryRepository.clearOldEntries()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCardData(dayOfWeek: String) {
        // 获取当前本地时间
        val currentTime = LocalDateTime.now()
        // 格式化时间字符串
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedTime = currentTime.format(formatter)

        val cardData = CardDataEntity(
            dayOfWeek = dayOfWeek,
            title = textFieldValue,  // 使用输入框内容作为标题
            description = formattedTime, // 设置为当前本地时间
            type = if (selectedImageUri != null) CardType.ONE_IMAGE else CardType.NO_IMAGE,
            imageId = selectedImageUri // 直接保存 URI 字符串
        )
        insertOrUpdateCardData(cardData)
    }


}

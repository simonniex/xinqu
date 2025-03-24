package com.example.teacher.presentation.teachermine.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.other.util.internet.fetchQR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeacherMineQRViewModel : ViewModel() {
    private val _qrCodeUrl = MutableStateFlow<String?>(null)
    val qrCodeUrl: StateFlow<String?> = _qrCodeUrl

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    fun updateInputText(newText: String) {
        _inputText.value = newText
    }

    fun fetchQRCode() {
        viewModelScope.launch {
            val url = fetchQR(_inputText.value)
            _qrCodeUrl.value = url

            _inputText.value = ""
        }
    }
}

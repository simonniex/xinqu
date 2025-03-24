package com.example.softcup.presentation.student


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {

    // 退出应用的状态
    private val _shouldExit = MutableStateFlow(false)
    val shouldExit = _shouldExit.asStateFlow()

    // 选中的城市
    private val _selectedCity = MutableStateFlow("请选择城市")
    val selectedCity = _selectedCity.asStateFlow()

    // 底部导航栏的选中索引
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    // 请求退出的逻辑
    fun requestExit() {
        viewModelScope.launch {
            _shouldExit.value = true
        }
    }

    // 重置退出标志
    fun resetExitFlag() {
        _shouldExit.value = false
    }

    // 更新选中的城市
    fun updateCity(city: String) {
        _selectedCity.value = city
    }

    // 更新底部导航栏的选中索引
    fun updateTabIndex(index: Int) {
        _selectedTabIndex.value = index
    }
}

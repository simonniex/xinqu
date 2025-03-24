package com.example.softcup.presentation.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompanyViewModel : ViewModel() {
    private val _shouldExit = MutableLiveData(false)
    val shouldExit: LiveData<Boolean> get() = _shouldExit

    fun requestExit() {
        _shouldExit.value = true
    }

    fun resetExitFlag() {
        _shouldExit.value = false
    }
}
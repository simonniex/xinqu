package com.example.teacher.presentation.teachermine.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.dao.Company
import com.example.core.dao.Student
import com.example.core.dao.Teacher
import com.example.core.dao.TeacherRepository
import com.example.core.other.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

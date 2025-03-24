package com.example.teacher.presentation.teachermine

import androidx.lifecycle.ViewModel
import com.example.core.dao.CompanyRepository
import com.example.core.dao.StudentRepository
import com.example.core.dao.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TeacherMineViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val companyRepository: CompanyRepository
) : ViewModel() {

}

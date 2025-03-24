package com.example.core.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TeacherRepository(private val teacherDao: TeacherDao) {

    suspend fun insertTeachers(teachers: List<TeacherEntity>) {
        teacherDao.insertTeachers(teachers)
    }

    fun getAllTeachers(): Flow<List<TeacherEntity>> {
        return teacherDao.getAllTeachers()
            .flowOn(Dispatchers.IO) // 确保流操作在 IO 线程上执行
    }

    fun getTeacherByUsername(username: String): Flow<TeacherEntity?> {
        return teacherDao.getTeacherByUsername(username)
            .flowOn(Dispatchers.IO) // 确保流操作在 IO 线程上执行
    }

    suspend fun updateTeacher(teacher: TeacherEntity) {
        teacherDao.updateTeacher(teacher)
    }

    suspend fun deleteTeacher(teacher: TeacherEntity) {
        teacherDao.deleteTeacher(teacher)
    }

    suspend fun deleteTeacherByPhone(email: String) {
        teacherDao.deleteTeacherByEmail(email)
    }
}

class StudentRepository(private val studentDao: StudentDao) {

    suspend fun insertStudents(students: List<StudentEntity>) {
        studentDao.insertStudents(students)
    }

    fun getAllStudents(): Flow<List<StudentEntity>> {
        return studentDao.getAllStudents()
            .flowOn(Dispatchers.IO) // 确保流操作在 IO 线程上执行
    }

    fun getStudentByUsername(username: String): Flow<StudentEntity?> {
        return studentDao.getStudentByUsername(username)
            .flowOn(Dispatchers.IO) // 确保流操作在 IO 线程上执行
    }

    suspend fun updateStudent(student: StudentEntity) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: StudentEntity) {
        studentDao.deleteStudent(student)
    }

    suspend fun deleteStudentByPhone(email: String) {
        studentDao.deleteStudentByEmail(email)
    }
}

class CompanyRepository(private val companyDao: CompanyDao) {

    suspend fun insertCompanies(companies: List<CompanyEntity>) {
        companyDao.insertCompanies(companies)
    }

    fun getAllCompanies(): Flow<List<CompanyEntity>> {
        return companyDao.getAllCompanies()
            .flowOn(Dispatchers.IO) // 确保流操作在 IO 线程上执行
    }

    fun getCompanyByUsername(username: String): Flow<CompanyEntity?> {
        return companyDao.getCompanyByUsername(username)
            .flowOn(Dispatchers.IO) // 确保流操作在 IO 线程上执行
    }

    suspend fun updateCompany(company: CompanyEntity) {
        companyDao.updateCompany(company)
    }

    suspend fun deleteCompany(company: CompanyEntity) {
        companyDao.deleteCompany(company)
    }

    suspend fun deleteCompanyByEmail(email: String) {
        companyDao.deleteCompanyByEmail(email)
    }
}



class DiaryRepository(private val cardDataDao: CardDataDao) {

    // 插入新的日记
    suspend fun insertCardData(cardData: CardDataEntity) {
        cardDataDao.insertCardData(cardData)
    }
    // 根据天数查询日记
    suspend fun getCardDataForDay(dayOfWeek: String): List<CardDataEntity> {
        return cardDataDao.getCardDataForDay(dayOfWeek) // 返回列表
    }


    // 清除上周的数据
    suspend fun clearOldEntries() {
        cardDataDao.deleteAllEntries()
    }

    // 获取所有条目
    suspend fun getAllEntries(): List<CardDataEntity> {
        return cardDataDao.getAllCardData()
    }
}

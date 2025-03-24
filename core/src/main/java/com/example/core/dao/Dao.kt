package com.example.core.dao


import androidx.room.*
import com.example.core.data.model.CardData

import kotlinx.coroutines.flow.Flow
@Dao
interface TeacherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeachers(teachers: List<TeacherEntity>)

    @Query("SELECT * FROM teachers")
    fun getAllTeachers(): Flow<List<TeacherEntity>>


    @Query("SELECT * FROM teachers WHERE username = :username LIMIT 1")
    fun getTeacherByUsername(username: String): Flow<TeacherEntity?>

    @Update
    suspend fun updateTeacher(teacher: TeacherEntity)

    @Delete
    suspend fun deleteTeacher(teacher: TeacherEntity)

    @Query("DELETE FROM teachers WHERE email = :email")
    suspend fun deleteTeacherByEmail(email: String)  // 改为通过 email 删除
}

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<StudentEntity>)

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE username = :username LIMIT 1")
    fun getStudentByUsername(username: String): Flow<StudentEntity?>

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)

    @Query("DELETE FROM students WHERE email = :email")
    suspend fun deleteStudentByEmail(email: String)  // 改为通过 email 删除
}

@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanies(companies: List<CompanyEntity>)

    @Query("SELECT * FROM companies")
    fun getAllCompanies(): Flow<List<CompanyEntity>>

    @Query("SELECT * FROM companies WHERE username = :username LIMIT 1")
    fun getCompanyByUsername(username: String): Flow<CompanyEntity?>

    @Update
    suspend fun updateCompany(company: CompanyEntity)

    @Delete
    suspend fun deleteCompany(company: CompanyEntity)

    @Query("DELETE FROM companies WHERE email = :email")
    suspend fun deleteCompanyByEmail(email: String)  // 根据 email 删除公司
}


//PlanScreen

@Dao
interface CardDataDao {

    // 插入新的日记条目
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardData(cardData: CardDataEntity)

    // 根据天数获取当天的所有日记
    @Query("SELECT * FROM diary_entries WHERE dayOfWeek = :dayOfWeek")
    suspend fun getCardDataForDay(dayOfWeek: String): List<CardDataEntity> // 返回列表

    // 获取所有日记
    @Query("SELECT * FROM diary_entries")
    suspend fun getAllCardData(): List<CardDataEntity>

    // 删除所有过期数据（这里按周删除）
    @Query("DELETE FROM diary_entries")
    suspend fun deleteAllEntries()

    // 更新某个条目的日记
    @Update
    suspend fun updateCardData(cardData: CardDataEntity)

    // 删除单个条目
    @Delete
    suspend fun deleteCardData(cardData: CardDataEntity)
}

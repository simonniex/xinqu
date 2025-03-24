package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SchoolDatabase {
        return Room.databaseBuilder(
            context,
            SchoolDatabase::class.java,
            "identity_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabase1(@ApplicationContext context: Context): DiaryDatabase {
        return Room.databaseBuilder(
            context,
            DiaryDatabase::class.java,
            "my_plan_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTeacherDao(db: SchoolDatabase): TeacherDao {
        return db.teacherDao()
    }

    @Provides
    @Singleton
    fun provideStudentDao(db: SchoolDatabase): StudentDao {
        return db.studentDao()
    }
    @Provides
    @Singleton
    fun provideCompanyDao(db: SchoolDatabase): CompanyDao {
        return db.companyDao()  // 假设已经在数据库中定义了 companyDao()
    }


    @Provides
    @Singleton
    fun provideCardDataDao(db: DiaryDatabase):CardDataDao{
        return db.cardDataDao()
    }


    @Provides
    @Singleton
    fun provideTeacherRepository(teacherDao: TeacherDao): TeacherRepository {
        return TeacherRepository(teacherDao)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(studentDao: StudentDao): StudentRepository {
        return StudentRepository(studentDao)
    }

    @Provides
    @Singleton
    fun provideCompanyRepository(companyDao: CompanyDao): CompanyRepository {
        return CompanyRepository(companyDao)
    }

    @Provides
    @Singleton
    fun provideDiaryRepository(cardDataDao:CardDataDao):DiaryRepository{
        return DiaryRepository(cardDataDao)
    }
}
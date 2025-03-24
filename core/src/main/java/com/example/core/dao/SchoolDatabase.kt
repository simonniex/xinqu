package com.example.core.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [TeacherEntity::class, StudentEntity::class,CompanyEntity::class], version = 1, exportSchema = false)
abstract class SchoolDatabase : RoomDatabase() {

    abstract fun teacherDao(): TeacherDao
    abstract fun studentDao(): StudentDao
    abstract fun companyDao(): CompanyDao
    //手动创建数据库实例
//单例模式（INSTANCE）确保数据库对象在应用中只有一个实例，getDatabase 方法用于获取数据库实例。Room.databaseBuilder 用来构建数据库。
    companion object {
        //确保线程安全的单例模式
        @Volatile
        private var INSTANCE: SchoolDatabase? = null//决定是否创建一个新的数据库实例。

        fun getDatabase(context: Context): SchoolDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    "school_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


@Database(entities = [CardDataEntity::class], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun cardDataDao(): CardDataDao
}




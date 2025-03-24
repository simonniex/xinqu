package com.example.core.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.model.CardData
import com.example.core.data.model.CardType

// Data class for Teacher
data class Teacher(
    val email: String,
    val avatar: String,
    val username: String,
    val password: String,
    val school: String,
    val position: String,
    val phone: String,
    val club: String,
    val clubImage: String
)

// Entity class for Teacher
@Entity(tableName = "teachers")
data class TeacherEntity(
    @PrimaryKey val email: String,
    val avatar: String,
    val username: String,
    val password: String,
    val school: String,
    val position: String,
    val phone: String,
    val club: String,
    val clubImage: String
) {
    companion object {
        fun from(teacher: Teacher): TeacherEntity {
            return TeacherEntity(
                email = teacher.email,
                avatar = teacher.avatar,
                username = teacher.username,
                password = teacher.password,
                school = teacher.school,
                position = teacher.position,
                phone = teacher.phone,
                club = teacher.club,
                clubImage = teacher.clubImage
            )
        }
    }

    fun toTeacher(): Teacher {
        return Teacher(
            email = email,
            avatar = avatar,
            username = username,
            password = password,
            school = school,
            position = position,
            phone = phone,
            club = club,
            clubImage = clubImage
        )
    }
}



data class Student(
    val phone: String,
    val avatar: String,
    val username: String,
    val password: String,
    val school: String,
    val studentid: String,
    val club: String,
    val clubImage: String,
    val email: String,
    val great: Boolean
)

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey val email: String, // 使用 email 作为唯一标识符
    val avatar: String,
    val username: String,
    val password: String,
    val school: String,
    val studentid: String,
    val phone: String,
    val club: String,
    val clubImage: String,
    val great: Boolean
) {
    companion object {
        // 从 Student 转换为 StudentEntity
        fun from(student: Student): StudentEntity {
            return StudentEntity(
                email = student.email,
                avatar = student.avatar,
                username = student.username,
                password = student.password,
                school = student.school,
                studentid = student.studentid,
                phone = student.phone,
                club = student.club,
                clubImage = student.clubImage,
                great = student.great
            )
        }
    }

    // 将 StudentEntity 转换为 Student
    fun toStudent(): Student {
        return Student(
            email = email,
            avatar = avatar,
            username = username,
            password = password,
            school = school,
            studentid = studentid,
            phone = phone,
            club = club,
            clubImage = clubImage,
            great = great
        )
    }
}

// Data class for Company
data class Company(
    val email: String,
    val avatar: String,
    val username: String,
    val password: String,
    val company: String,
    val companyId: String,
    val phone: String,
    val great: Boolean
)

// Entity class for Company
@Entity(tableName = "companies")
data class CompanyEntity(
    @PrimaryKey val email: String,
    val avatar: String,
    val username: String,
    val password: String,
    val company: String,
    val companyId: String,
    val phone: String,
    val great: Boolean
) {
    companion object {
        fun from(company: Company): CompanyEntity {
            return CompanyEntity(
                email = company.email,
                avatar = company.avatar,
                username = company.username,
                password = company.password,
                company = company.company,
                companyId = company.companyId,
                phone = company.phone,
                great = company.great
            )
        }
    }

    fun toCompany(): Company {
        return Company(
            email = email,
            avatar = avatar,
            username = username,
            password = password,
            company = company,
            companyId = companyId,
            phone = phone,
            great = great
        )
    }
}


@Entity(tableName = "diary_entries")
data class CardDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null, // 添加 autoGenerate
    val dayOfWeek: String,
    val title: String,
    val description: String,
    val type: CardType,
    val imageId: String? = null,
    val images: String? = null
) {
    companion object {
        // 从 CardData 映射为 CardDataEntity
        fun from(cardData: CardData): CardDataEntity {
            return CardDataEntity(
                dayOfWeek = cardData.dayOfWeek,
                title = cardData.title,
                description = cardData.description,
                type = cardData.type,
                imageId = cardData.imageId,
                images = cardData.images?.joinToString(",") // 将List转为String存储
            )
        }
    }

    // 将 CardDataEntity 转换回 CardData
    fun toCardData(): CardData {
        return CardData(
            dayOfWeek = dayOfWeek,
            title = title,
            description = description,
            type = type,
            imageId = imageId,
            images = images?.split(",") // 将String转回List
        )
    }
}


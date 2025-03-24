package com.example.core.state

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock



class UserState {
    private val mutex = Mutex() // 创建 Mutex 实例
    private var _usernameId: String? = null
    private var _avatarUri: String? = null
    private var _school: String? = null
    private var _company: String? = null

    private var _phone: String? = null
    private var _studentId: String? = null
    private var _club: String? = null
    private var _email: String? = null
    private var _great: String? = null

    private var _password:String?=null

    private var _ip:String?=null

    suspend fun geIp():String?{
        return mutex.withLock { _ip }
    }

    suspend fun setIp(value: String?){
        mutex.withLock { _ip=value }
    }


    suspend fun getUsernameId(): String? {
        return mutex.withLock { _usernameId }
    }

    suspend fun setUsernameId(value: String?) {
        mutex.withLock { _usernameId = value }
    }

    suspend fun getPassword():String?{
        return mutex.withLock { _password }
    }
    suspend fun setPassword(value: String?){
        mutex.withLock { _password = value }
    }

    suspend fun getAvatarUri(): String? {
        return mutex.withLock { _avatarUri }
    }

    suspend fun setAvatarUri(value: String?) {
        mutex.withLock { _avatarUri = value }
    }

    suspend fun getSchool(): String? {
        return mutex.withLock { _school }
    }

    suspend fun setSchool(value: String?) {
        mutex.withLock { _school = value }
    }

    suspend fun getCompany(): String? {
        return mutex.withLock { _company }
    }

    suspend fun setCompany(value: String?) {
        mutex.withLock { _company = value }
    }



    suspend fun getPhone(): String? {
        return mutex.withLock { _phone }
    }

    suspend fun setPhone(value: String?) {
        mutex.withLock { _phone = value }
    }

    suspend fun getStudentId(): String? {
        return mutex.withLock { _studentId }
    }

    suspend fun setStudentId(value: String?) {
        mutex.withLock { _studentId = value }
    }

    suspend fun getClub(): String? {
        return mutex.withLock { _club }
    }

    suspend fun setClub(value: String?) {
        mutex.withLock { _club = value }
    }

    suspend fun getEmail(): String? {
        return mutex.withLock { _email }
    }

    suspend fun setEmail(value: String?) {
        mutex.withLock { _email = value }
    }

    suspend fun getGreat(): String? {
        return mutex.withLock { _great }
    }

    suspend fun setGreat(value: String?) {
        mutex.withLock { _great = value }
    }
}

// 单例对象
val userState = UserState()


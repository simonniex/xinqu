package com.example.core.other.util

sealed class MyResource<T>(val data:T?=null,val message:String?=null){
    class Success<T>(data: T):MyResource<T>(data)
    class Error<T>(message: String,data: T?=null):MyResource<T>(data,message)
}

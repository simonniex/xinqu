package com.example.core.nav

// core模块中的注解定义
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Destination(val route: String)

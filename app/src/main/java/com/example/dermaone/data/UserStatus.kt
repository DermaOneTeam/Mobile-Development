package com.example.dermaone.data

data class UserStatus (
    val email: String,
    val username: String,
    val password: String,
    val id: String,
    val token: String,
    val isLogin: Boolean = false
)
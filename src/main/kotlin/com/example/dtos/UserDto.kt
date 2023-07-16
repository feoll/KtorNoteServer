package com.example.dtos

data class UserDto(
    val id: Int = 0,
    val email: String,
    val hashPassword: String,
    val createdAt: Long = System.currentTimeMillis()
)

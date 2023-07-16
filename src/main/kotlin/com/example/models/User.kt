package com.example.models

data class User(
    val id: Int,
    val email: String,
    val hashPassword: String,
    val createdAt: Long
)

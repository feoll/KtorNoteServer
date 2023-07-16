package com.example.services.user

import com.example.dtos.UserDto

interface UserService {
    suspend fun save(userDto: UserDto): UserDto?
    suspend fun update(userDto: UserDto): UserDto?
    suspend fun findByEmail(email: String): UserDto?
}
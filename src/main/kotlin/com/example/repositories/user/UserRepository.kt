package com.example.repositories.user

import com.example.models.User

interface UserRepository {
    suspend fun save(user: User): User?
    suspend fun update(user: User): User?
    suspend fun findByEmail(email: String): User?
}
package com.example.authentication

import io.ktor.server.auth.*

data class UserPrincipal(
    val id: Int,
    val email: String,
    val hashPassword: String,
    val createdAt: Long
): Principal

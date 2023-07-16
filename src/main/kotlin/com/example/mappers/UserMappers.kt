package com.example.mappers

import com.example.authentication.UserPrincipal
import com.example.authentication.hash
import com.example.database.entities.UserTable
import com.example.dtos.UserDto
import com.example.dtos.request.LoginRequest
import com.example.dtos.request.RegisterRequest
import com.example.models.User
import org.jetbrains.exposed.sql.ResultRow

fun rowToUser(row: ResultRow): User {
    return User(
        id = row[UserTable.id],
        email = row[UserTable.email],
        hashPassword = row[UserTable.hashPassword],
        createdAt = row[UserTable.createdAt]
    )
}

fun userToUserDto(user: User?): UserDto? {
    if(user == null) return null
    return UserDto(
        id = user.id,
        email = user.email,
        hashPassword = user.hashPassword,
        createdAt = user.createdAt
    )
}

fun userDtoToUser(user: UserDto): User {
    return User(
        id = user.id,
        email = user.email,
        hashPassword = user.hashPassword,
        createdAt = user.createdAt
    )
}

fun userDtoToUserPrincipal(user: UserDto): UserPrincipal {
    return UserPrincipal(
        id = user.id,
        email = user.email,
        hashPassword = user.hashPassword,
        createdAt = user.createdAt
    )
}

fun loginRequestToUserDto(loginRequest: LoginRequest): UserDto {
    return UserDto(
        email = loginRequest.email,
        hashPassword = hash(loginRequest.password)
    )
}

fun registerRequestToUserDto(registerRequest: RegisterRequest): UserDto {
    return UserDto(
        email = registerRequest.email,
        hashPassword = hash(registerRequest.password)
    )
}
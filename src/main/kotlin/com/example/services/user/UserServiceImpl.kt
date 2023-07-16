package com.example.services.user

import com.example.dtos.UserDto
import com.example.mappers.userDtoToUser
import com.example.mappers.userToUserDto
import com.example.repositories.user.UserRepository

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override suspend fun save(userDto: UserDto): UserDto? {
        val user = userRepository.save(userDtoToUser(userDto))
        return userToUserDto(user)
    }

    override suspend fun update(userDto: UserDto): UserDto? {
        val user = userRepository.update(userDtoToUser(userDto))
        return userToUserDto(user)
    }

    override suspend fun findByEmail(email: String): UserDto? {
        val user = userRepository.findByEmail(email)
        return userToUserDto(user)
    }
}
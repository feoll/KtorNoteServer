package com.example.plugins


import com.example.authentication.JwtService
import com.example.dtos.UserDto
import com.example.dtos.request.LoginRequest
import com.example.dtos.request.RegisterRequest
import com.example.dtos.response.LoginResponse
import com.example.dtos.response.RegisterResponse
import com.example.mappers.loginRequestToUserDto
import com.example.mappers.registerRequestToUserDto
import com.example.services.user.UserService
import com.example.dtos.response.MessageResponse
import com.example.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureUserRouting(
    userService: UserService
) {
    routing {
        post("/register") {
            val registerRequest = call.receive<RegisterRequest>()
            val userDto: UserDto

            try {
                userDto = registerRequestToUserDto(registerRequest = registerRequest)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Missing some fields"))
                return@post
            }

            if(!isValidEmail(userDto.email)) {
                call.respond(HttpStatusCode.BadRequest, MessageResponse("The email isn't valid"))
                return@post
            }

            val userWithSameEmail = userService.findByEmail(email = userDto.email)
            if(userWithSameEmail != null) {
                call.respond(HttpStatusCode.NotFound, MessageResponse("User with such an email already exists"))
                return@post
            }

            val saveUser = userService.save(userDto = userDto)
            if(saveUser != null) {
                call.respond(HttpStatusCode.OK, RegisterResponse(JwtService.generateToken(saveUser.email)))
            } else {
                call.respond(HttpStatusCode.Conflict, MessageResponse("User not saved"))
            }
        }

        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val userDto: UserDto

            try {
                userDto = loginRequestToUserDto(loginRequest = loginRequest)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Missing some fields"))
                return@post
            }

            if(!isValidEmail(userDto.email)) {
                call.respond(HttpStatusCode.BadRequest, MessageResponse("The email isn't valid"))
                return@post
            }

            val userWithSameEmail = userService.findByEmail(email = userDto.email)
            if(userWithSameEmail != null) {
                call.respond(HttpStatusCode.OK, LoginResponse(JwtService.generateToken(userWithSameEmail.email)))
                return@post
            } else {
                call.respond(HttpStatusCode.Conflict, MessageResponse("User not found"))
            }
        }
    }
}




package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.example.authentication.JwtService
import com.example.dtos.response.MessageResponse
import com.example.mappers.userDtoToUserPrincipal
import com.example.services.user.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureSecurity(
    userService: UserService
) {
    install(Authentication) {
        jwt("jwt") {
            verifier(JwtService.verifier)
            realm="Note Server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userService.findByEmail(email)
                if(user != null) userDtoToUserPrincipal(user) else null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, MessageResponse("Token is not valid or has expired"))
            }
        }
    }
}
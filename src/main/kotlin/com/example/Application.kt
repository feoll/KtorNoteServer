package com.example

import com.example.database.DatabaseFactory
import com.example.plugins.*
import com.example.repositories.note.NoteRepositoryImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.repositories.user.UserRepositoryImpl
import com.example.services.note.NoteServiceImpl
import com.example.services.user.UserServiceImpl

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    val userService = UserServiceImpl(UserRepositoryImpl())
    val noteService = NoteServiceImpl(NoteRepositoryImpl())

    configureContentNegotiation()
    configureSecurity(userService)
    configureUserRouting(userService)
    configureNoteRouting(noteService)
}

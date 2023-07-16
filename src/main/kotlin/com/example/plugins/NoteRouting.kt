package com.example.plugins


import com.example.authentication.UserPrincipal
import com.example.dtos.NoteDto
import com.example.dtos.request.CreateNoteRequest
import com.example.dtos.request.UpdateNoteRequest
import com.example.dtos.response.MessageResponse
import com.example.mappers.createNoteRequestToNoteDto
import com.example.mappers.updateNoteRequestToNoteDto
import com.example.services.note.NoteService
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*

fun Application.configureNoteRouting(
    noteService: NoteService
) {
    routing {
        authenticate("jwt"){
            post("/notes/create") {
                val createNoteRequest = call.receive<CreateNoteRequest>()

                val userPrincipal = call.principal<UserPrincipal>() ?: return@post

                val noteDto: NoteDto
                try {
                    noteDto = createNoteRequestToNoteDto(createNoteRequest, userId = userPrincipal.id)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("Missing some fields"))
                    return@post
                }

                val savedNote = noteService.add(noteDto)
                if(savedNote != null) {
                    call.respond(HttpStatusCode.OK, savedNote)
                } else {
                    call.respond(HttpStatusCode.Conflict, MessageResponse("Note not saved"))
                }
            }

            post("/notes/update") {
                val updateNoteRequest = call.receive<UpdateNoteRequest>()

                val userPrincipal = call.principal<UserPrincipal>() ?: return@post

                val noteDto: NoteDto
                try {
                    noteDto = updateNoteRequestToNoteDto(updateNoteRequest, userId = userPrincipal.id)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("Missing some fields"))
                    return@post
                }

                val note = noteService.get(noteDto.id)
                if(note == null) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("The note isn't exists"))
                    return@post
                }

                if(note.userId != userPrincipal.id) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("Access denied"))
                    return@post
                }

                val updateNote = noteService.update(noteDto)
                if(updateNote != null) {
                    call.respond(HttpStatusCode.OK, updateNote)
                } else {
                    call.respond(HttpStatusCode.Conflict, MessageResponse("Note not updated"))
                }
            }

            delete("/notes/delete") {
                val noteId = call.request.queryParameters["id"]
                if(noteId == null) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("Missing some fields"))
                    return@delete
                }

                val userPrincipal = call.principal<UserPrincipal>() ?: return@delete

                val id: Int
                try {
                    id = noteId.toInt()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("Invalid fields"))
                    return@delete
                }

                val note = noteService.get(id)
                if(note == null) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("The note isn't exists"))
                    return@delete
                }

                if(note.userId != userPrincipal.id) {
                    call.respond(HttpStatusCode.BadRequest, MessageResponse("Access denied"))
                    return@delete
                }

                noteService.delete(id, userPrincipal.id)
                call.respond(HttpStatusCode.OK, MessageResponse("Note deleted successfully"))
            }

            get("/notes") {
                val userPrincipal = call.principal<UserPrincipal>() ?: return@get

                val notes = noteService.getByUserId(userId = userPrincipal.id)
                call.respond(HttpStatusCode.OK, notes)
            }
        }
    }
}

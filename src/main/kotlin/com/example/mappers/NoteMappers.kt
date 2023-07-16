package com.example.mappers

import com.example.database.entities.NoteTable
import com.example.dtos.NoteDto
import com.example.dtos.request.CreateNoteRequest
import com.example.dtos.request.UpdateNoteRequest
import com.example.models.Note
import org.jetbrains.exposed.sql.ResultRow

fun rowToNote(row: ResultRow): Note {
    return Note(
        id = row[NoteTable.id],
        userId = row[NoteTable.userId],
        title = row[NoteTable.title],
        description = row[NoteTable.description],
        createdAt = row[NoteTable.createdAt]
    )
}


fun noteDtoToNote(noteDto: NoteDto): Note {
    return Note(
        id = noteDto.id,
        userId = noteDto.userId,
        title = noteDto.title,
        description = noteDto.description,
        createdAt = noteDto.createdAt
    )
}

fun noteToNoteDto(note: Note?): NoteDto? {
    if(note == null) return null
    return NoteDto(
        id = note.id,
        userId = note.userId,
        title = note.title,
        description = note.description,
        createdAt = note.createdAt
    )
}


fun createNoteRequestToNoteDto(createNoteRequest: CreateNoteRequest, userId: Int): NoteDto {
    return NoteDto(
        userId = userId,
        title = createNoteRequest.title,
        description = createNoteRequest.description
    )
}

fun updateNoteRequestToNoteDto(updateNoteRequest: UpdateNoteRequest, userId: Int): NoteDto {
    return NoteDto(
        id = updateNoteRequest.id,
        userId = userId,
        title = updateNoteRequest.title,
        description = updateNoteRequest.description,
        createdAt = System.currentTimeMillis()
    )
}
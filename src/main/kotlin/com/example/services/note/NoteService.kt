package com.example.services.note

import com.example.dtos.NoteDto

interface NoteService {
    suspend fun add(noteDto: NoteDto): NoteDto?
    suspend fun update(noteDto: NoteDto): NoteDto?
    suspend fun delete(noteId: Int, userId: Int)
    suspend fun getByUserId(userId: Int): List<NoteDto>
    suspend fun get(id: Int): NoteDto?
}
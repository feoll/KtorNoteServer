package com.example.repositories.note

import com.example.models.Note

interface NoteRepository {
    suspend fun add(note: Note): Note?
    suspend fun update(note: Note): Note?
    suspend fun delete(noteId: Int, userId: Int)
    suspend fun getByUserId(userId: Int): List<Note>
    suspend fun get(id: Int): Note?
}
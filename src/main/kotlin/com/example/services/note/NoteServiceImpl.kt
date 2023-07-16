package com.example.services.note

import com.example.dtos.NoteDto
import com.example.mappers.noteDtoToNote
import com.example.mappers.noteToNoteDto
import com.example.repositories.note.NoteRepository

class NoteServiceImpl(
    val noteRepository: NoteRepository
) : NoteService {
    override suspend fun add(noteDto: NoteDto): NoteDto? {
        val note = noteRepository.add(noteDtoToNote(noteDto))
        return noteToNoteDto(note)
    }

    override suspend fun update(noteDto: NoteDto): NoteDto? {
        val note = noteRepository.update(noteDtoToNote(noteDto))
        return noteToNoteDto(note)
    }

    override suspend fun delete(noteId: Int, userId: Int) {
        noteRepository.delete(noteId, userId)
    }

    override suspend fun getByUserId(userId: Int): List<NoteDto> {
        val notes = noteRepository.getByUserId(userId)
        return notes.mapNotNull { noteToNoteDto(it) }
    }

    override suspend fun get(id: Int): NoteDto? {
        val note = noteRepository.get(id)
        return noteToNoteDto(note)
    }
}
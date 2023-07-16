package com.example.repositories.note

import com.example.database.DatabaseFactory.dbQuery
import com.example.database.entities.NoteTable
import com.example.mappers.rowToNote
import com.example.models.Note
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NoteRepositoryImpl : NoteRepository {
    override suspend fun add(note: Note): Note? {
        val statement = dbQuery {
            NoteTable.insert {
                it[userId] = note.userId
                it[title] = note.title
                it[description] = note.description
                it[createdAt] = note.createdAt
            }
        }

        val insertedRow = statement.resultedValues?.get(0) ?: return null

        return rowToNote(insertedRow)
    }

    override suspend fun update(note: Note): Note? {
        val statement = dbQuery {
            NoteTable.update({ NoteTable.id.eq(note.id) }) {
                it[userId] = note.userId
                it[title] = note.title
                it[description] = note.description
                it[createdAt] = note.createdAt
            }
        }

        return if(statement > 0) note else null
    }

    override suspend fun delete(noteId: Int, userId: Int) {
        dbQuery {
            NoteTable.deleteWhere {
                NoteTable.id.eq(noteId) and NoteTable.userId.eq(userId)
            }
        }
    }

    override suspend fun getByUserId(userId: Int): List<Note> {
        val notes = dbQuery {
            NoteTable.select{ NoteTable.userId.eq(userId) }.mapNotNull { rowToNote(it) }
        }
        return notes
    }

    override suspend fun get(id: Int): Note? {
        val note = dbQuery {
            NoteTable.select{ NoteTable.id.eq(id) }.mapNotNull { rowToNote(it) }.singleOrNull()
        }
        return note
    }
}
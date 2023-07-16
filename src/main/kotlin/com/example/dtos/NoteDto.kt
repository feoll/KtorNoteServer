package com.example.dtos

data class NoteDto(
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis()
)

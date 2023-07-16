package com.example.dtos.request

data class UpdateNoteRequest(
    val id: Int,
    val title: String,
    val description: String
)

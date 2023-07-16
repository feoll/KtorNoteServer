package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object NoteTable : Table("notes") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val title = text("title")
    val description = text("description")
    val createdAt = long("createdAt")

    override val primaryKey = PrimaryKey(id)
}
package com.example.database.entities

import org.jetbrains.exposed.sql.Table

object UserTable : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 256).uniqueIndex("email_unique")
    val hashPassword = text("hashPassword")
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}

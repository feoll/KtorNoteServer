package com.example.repositories.user


import com.example.database.DatabaseFactory.dbQuery
import com.example.database.entities.UserTable
import com.example.mappers.rowToUser
import com.example.models.User
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class  UserRepositoryImpl : UserRepository {
    override suspend fun save(user: User): User? {
        val statement = dbQuery {
            UserTable.insert {
                it[email] = user.email
                it[hashPassword] = user.hashPassword
                it[createdAt] = user.createdAt
            }
        }

        val insertedRow = statement.resultedValues?.get(0) ?: return null

        return rowToUser(insertedRow)
    }

    override suspend fun update(user: User): User? {
        val statement = dbQuery {
            UserTable.update({ UserTable.id.eq(user.id) }) {
                it[email] = user.email
                it[hashPassword] = user.hashPassword
                it[createdAt] = user.createdAt
            }
        }

        return if(statement > 0) user else null
    }

    override suspend fun findByEmail(email: String): User? {
        val user = dbQuery {
            UserTable.select{ UserTable.email.eq(email) }.mapNotNull { rowToUser(it) }.singleOrNull()
        }

        return user
    }
}
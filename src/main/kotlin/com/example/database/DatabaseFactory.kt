package com.example.database

import com.example.database.entities.NoteTable
import com.example.database.entities.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(getHikariDataSource())
        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(NoteTable)
        }
    }

    private fun getHikariDataSource(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver" //TODO: hide in a real project
        config.jdbcUrl = "jdbc:postgresql:notev2?user=postgres&password=123456" //TODO: hide in a real project
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
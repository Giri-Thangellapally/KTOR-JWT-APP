package com.example.service

import com.example.db.DatabaseFactory.dbQuery
import com.example.db.UserTable
import com.example.models.CreateUserParams
import com.example.models.LoginRequest
import com.example.models.User
import org.jetbrains.exposed.sql.Except
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class UserServiceImpl : UserService {
    override suspend fun registerUser(params: CreateUserParams): User? {

        var statment: InsertStatement<Number>? = null

        dbQuery {
            statment = UserTable.insert {
                it[email] = params.email
                it[password] = params.password
                it[fullName] = params.fullName
                it[avatar] = params.avatar
            }
        }
        return rowToUser(statment?.resultedValues?.get(0))
    }

    override suspend fun findUserByEmail(email: String): User? {
        val user = dbQuery {
            UserTable.select {
                UserTable.email.eq(email)
            }.map { rowToUser(it) }.singleOrNull()
        }
        return user
    }
    override suspend fun rowToUserForLogin(email: String): User? {
        val user = dbQuery {
            UserTable.select {
                UserTable.email.eq(email)
            }.map { rowToUserForLogin(it) }.singleOrNull()
        }
        return user
    }


    override suspend fun loginUser(loginRequest: LoginRequest): User? {
        return findUserByEmail(loginRequest.email)
    }

    private fun rowToUser(row: ResultRow?): User? {
        return if (row == null)
            null
        else
            User(
                id = row[UserTable.id],
                fullName = row[UserTable.fullName],
                avatar = row[UserTable.avatar],
                email = row[UserTable.email],
                createdAt = row[UserTable.createdAt].toString()
            )

    }
    private fun rowToUserForLogin(row: ResultRow?): User? {
        return if (row == null)
            null
        else
            User(
                id = row[UserTable.id],
                fullName = row[UserTable.fullName],
                avatar = row[UserTable.avatar],
                email = row[UserTable.email],
                password = row[UserTable.password],
                createdAt = row[UserTable.createdAt].toString()
            )

    }

}
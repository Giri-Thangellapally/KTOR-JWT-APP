package com.example.service

import com.example.db.UserTable
import com.example.models.CreateUserParams
import com.example.models.LoginRequest
import com.example.models.User

interface UserService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun findUserByEmail(email:String): User?
    suspend fun rowToUserForLogin(email:String): User?
    suspend fun loginUser(loginRequest: LoginRequest): User?
}
package com.example.repository

import com.example.models.CreateUserParams
import com.example.models.LoginRequest
import com.example.utils.BaseResponse

interface UserRepository {
    suspend fun registerUser(params: CreateUserParams):BaseResponse<Any>

    suspend fun loginUser(loginRequest: LoginRequest):BaseResponse<Any>
}
package com.example.repository

import JwtConfig
import com.example.db.UserTable
import com.example.models.CreateUserParams
import com.example.models.LoginRequest
import com.example.models.SimpleResponse
import com.example.models.User
import com.example.service.UserService
import com.example.utils.BaseResponse

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun registerUser(params: CreateUserParams): BaseResponse<Any> {
        return if (isEmailExist(params.email) != null) {
            BaseResponse.ErrorResponse(message = "Email Already Registered")
        } else {
            val user = userService.registerUser(params)
            if (user != null) {
                //GENERATED AUTH TOKEN
                val token = JwtConfig.instance.createAccessToken(user.id)
                user.authToken = token
                BaseResponse.SuccessResponse(data = user)
            } else {
                BaseResponse.ErrorResponse()
            }
        }
    }

    override suspend fun loginUser(loginRequest: LoginRequest): BaseResponse<Any> {
        val user = rowToUserForLogin(loginRequest.email)
        return if (user != null) {
            if (loginRequest.password == user.password) {
                val loginResult = userService.loginUser(loginRequest)!!
                val token = JwtConfig.instance.createAccessToken(loginResult.id)
                loginResult.authToken = token
                BaseResponse.SuccessResponse(data = SimpleResponse(true, token))
            } else {
                BaseResponse.ErrorResponse(message = "Incorrect Password")
            }
        }else{
            BaseResponse.ErrorResponse(message = "Incorrect Email Address")
        }
    }


    private suspend fun isEmailExist(email: String): User? {
        return userService.findUserByEmail(email)
    }
    private suspend fun rowToUserForLogin(email: String): User? {
        return userService.rowToUserForLogin(email)
    }
}
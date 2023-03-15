package com.example.routes

import com.example.models.CreateUserParams
import com.example.models.LoginRequest
import com.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*


const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"


@Location(REGISTER_REQUEST)
class UserRegisterRoute

@Location(LOGIN_REQUEST)
class UserLoginRoute

fun Application.authRoutes(repository: UserRepository) {

    routing {
        route("/auth") {
            post("/register") {
                val params = call.receive<CreateUserParams>()
                println("${params.email}")
                val result = repository.registerUser(params)
                call.respond(result.statusCode, result)
                println("${result.statusCode}")
            }
            post("/login"){
                val params =call.receive<LoginRequest>()
                val result = repository.loginUser(params)
                call.respond(result.statusCode,result)
            }
        }
    }
}
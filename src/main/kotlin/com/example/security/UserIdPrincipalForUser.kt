package com.example.security

import io.ktor.auth.Principal

data class UserIdPrincipalForUser(val id: Int): Principal, io.ktor.server.auth.Principal
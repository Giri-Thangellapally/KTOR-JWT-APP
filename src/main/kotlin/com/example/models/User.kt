package com.example.models

data class User(
    val id: Int,
    val fullName: String,
    val avatar: String,
    val email: String,
    val password: String? = null,
    var authToken: String? = null,
    val createdAt: String
)

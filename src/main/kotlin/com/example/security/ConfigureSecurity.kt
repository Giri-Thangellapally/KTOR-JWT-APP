package com.example.security

import JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.ConfigureSecurity() {
    JwtConfig.initialize("my-facc-app")

    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asInt()
                if (claim != null){
                    UserIdPrincipalForUser(claim)
                } else {
                    null
                }
            }
        }
    }

}
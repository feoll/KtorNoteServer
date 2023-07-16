package com.example.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

//TODO: hide keys in a real project
object JwtService {
    private val issuer = "Note Server"
    private val subject = "Note Authentication"
    private val jwtSecret = "Secret Test Key"
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()

    fun generateToken(email: String): String {
        return JWT.create()
            .withSubject(subject)
            .withIssuer(issuer)
            .withClaim("email", email)
            .sign(algorithm)
    }
}
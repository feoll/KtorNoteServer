package com.example.authentication

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

//TODO: hide keys in a real project
private val hashKey = "Test Hash Key".toByteArray()
private val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hash(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}
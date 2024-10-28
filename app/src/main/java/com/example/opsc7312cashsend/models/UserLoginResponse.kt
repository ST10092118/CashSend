package com.example.opsc7312cashsend.models

data class UserLoginResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String? = null // Assuming the API returns a token on success
)

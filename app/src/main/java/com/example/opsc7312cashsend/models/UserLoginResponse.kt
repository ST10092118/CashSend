package com.example.opsc7312cashsend.models

data class UserLoginResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String? = null,
    val userId: String? = null // Add userId here
)

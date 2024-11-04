package com.example.opsc7312cashsend.models

data class UserLoginResponse(
    val success: Boolean,
    val message: String? = null,
    val token: String? = null,
    val userId: String? = null // Add userId here
)

//This code was adapted from StackOverflow
//https://stackoverflow.com/questions/29180889/create-an-android-model-class-in-android-studio
//Raiyan Shahid
//https://stackoverflow.com/users/14424782/raiyan-shahid

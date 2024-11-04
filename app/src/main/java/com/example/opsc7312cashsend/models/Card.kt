package com.example.opsc7312cashsend.models

data class Card(
    val userId: String,         // Firebase UID to identify the user
    val cardNumber: String,
    val expiryDate: String,
    val cardHolderName: String
)

//This code was adapted from StackOverflow
//https://stackoverflow.com/questions/29180889/create-an-android-model-class-in-android-studio
//Raiyan Shahid
//https://stackoverflow.com/users/14424782/raiyan-shahid


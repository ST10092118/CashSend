package com.example.opsc7312cashsend.models

data class Card(
    val userId: String,         // Firebase UID to identify the user
    val cardNumber: String,
    val expiryDate: String,
    val cardHolderName: String
)


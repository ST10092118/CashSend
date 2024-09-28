package com.example.opsc7312cashsend

data class Notification(
    val recipient: String,
    val amount: String,
    val time: String,
    val date: String,
    val details: String,
    var isExpanded: Boolean = false
)

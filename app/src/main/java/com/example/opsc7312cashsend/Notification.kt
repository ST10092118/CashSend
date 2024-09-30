package com.example.opsc7312cashsend

data class Notification(
    val recipient: String,
    val amount: String,
    val time: String,
    val date: String,
    val details: String,
    var isExpanded: Boolean = false

    //This code was adapted from Stack Overflow
    //https://stackoverflow.com/questions/29180889/create-an-android-model-class-in-android-studio
    //Raiyan Shahid
    //https://stackoverflow.com/users/14424782/raiyan-shahid
)

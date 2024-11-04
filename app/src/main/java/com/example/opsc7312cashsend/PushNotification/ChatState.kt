package com.example.opsc7312cashsend.PushNotifications

data class ChatState(
    val isEnteringToken: Boolean = true,
    val remoteToken: String? = "",
    val messageText: String = "",
)

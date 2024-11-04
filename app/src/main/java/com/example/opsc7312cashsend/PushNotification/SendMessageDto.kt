package com.example.opsc7312cashsend.PushNotifications


data class SendMessageDto(
    val to: String?,
    val notification: NotificationBody,
)

data class  NotificationBody(
    val title: String,
    val body: String,
)

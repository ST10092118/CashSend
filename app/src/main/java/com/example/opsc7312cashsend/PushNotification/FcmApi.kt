package com.example.opsc7312cashsend.PushNotifications

import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {

    @POST("/send")
    suspend fun sendMessage(@Body body: SendMessageDto)

    @POST("/broadcast")
    suspend fun broadcast(@Body body: SendMessageDto)

    @POST("/updateToken")
    suspend fun updateToken(@Body token: String)
}

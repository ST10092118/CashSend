package com.example.opsc7312cashsend.PushNotifications

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM Token", "Refreshed token: $token")
    }
}

package com.example.opsc7312cashsend.PushNotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.OPSC7312CashSend.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PushNotificationService : FirebaseMessagingService() {

    // Initialize Retrofit for FCM API calls
    private val api: FcmApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // Replace with your backend URL
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(FcmApi::class.java)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()


        // Fetch the current FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("PushNotificationService", "FCM Token: $token")
                // Send token to your server or use it as needed
            } else {
                Log.w("PushNotificationService", "Fetching FCM token failed", task.exception)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "payments_channel",
                "Payment Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for payment status updates"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("PushNotificationService", "New FCM token: $token")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                api.updateToken(token) // Ensure `updateToken` is implemented in `FcmApi`
                Log.d("PushNotificationService", "Token updated successfully")
            } catch (e: Exception) {
                Log.e("PushNotificationService", "Failed to update token", e)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            val notification = NotificationCompat.Builder(this, "payments_channel")
                .setSmallIcon(R.drawable.ic_notifications45)
                .setContentTitle(it.title)
                .setContentText(it.body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notification)
        }
    }
}

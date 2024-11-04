package com.example.opsc7312cashsend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R

class HomeScreenActivity : AppCompatActivity() {

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        // Create the notification channel
        createNotificationChannel()

        // Retrieve userId from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("USER_ID", null)

        if (userId == null) {
            Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show()
            finish() // Close activity if userId is not found
            return
        }

        val btnQRScanner: Button = findViewById(R.id.btn_cards)
        val btnNotifications: Button = findViewById(R.id.btn_notifications)
        val btnProfile: Button = findViewById(R.id.btn_profile)
        val btnSettings: Button = findViewById(R.id.btn_settings)

        // Cards block click listener - Navigate to CardSelectionActivity
        btnQRScanner.setOnClickListener {
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Notifications Block on Home Screen
        btnNotifications.setOnClickListener {
            val intent = Intent(this, NotificationsActivity::class.java)
            intent.putExtra("USER_ID", userId) // Pass the user ID
            startActivity(intent)
        }

        // User profile block click listener
        btnProfile.setOnClickListener {
            val intent = Intent(this, MyAccountActivity::class.java)
            startActivity(intent)
        }

        // Settings block click listener - Navigate to SettingsActivity
        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "payments_channel",
                "Payments Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for payment status"
            }
            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}

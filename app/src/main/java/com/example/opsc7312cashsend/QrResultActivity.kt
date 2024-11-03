package com.example.opsc7312cashsend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class QrResultActivity : AppCompatActivity() {

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_result)

        // Retrieve userId from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("USER_ID", null)

        val qrData = intent.getStringExtra("QR_CODE_DATA") ?: return

        // Sample parsing logic, assuming QR data is a JSON string
        try {
            val jsonObject = JSONObject(qrData)
            val location = jsonObject.optString("location", "Unknown")
            val amount = jsonObject.optString("amount", "0.00")
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time)

            // Create a new Notification object
            val notification = Notification(
                location = location,
                amount = "R $amount",
                time = currentTime,
                date = currentDate
            )

            findViewById<TextView>(R.id.locationTextView).text = "Location: $location"
            findViewById<TextView>(R.id.amountTextView).text = "Amount: R $amount"

            // Handle Cancel button click
            findViewById<Button>(R.id.cancelButton).setOnClickListener {
                val intent = Intent(this, HomeScreenActivity::class.java)
                startActivity(intent)
                finish()
            }

            // Handle Pay button click
            findViewById<Button>(R.id.payButton).setOnClickListener {
                if (userId != null) {
                    saveNotification(userId!!, notification) // Pass userId to saveNotification
                } else {
                    Toast.makeText(this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, AddingCardActivity::class.java)
                startActivity(intent)
                finish()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to parse QR code data", Toast.LENGTH_SHORT).show()
        }
    }

    // Moved saveNotification function to be a class-level function
    private fun saveNotification(userId: String, notification: Notification) {
        val sharedPreferences = getSharedPreferences("NotificationsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Retrieve existing notifications from SharedPreferences
        val gson = Gson()
        val json = sharedPreferences.getString("notifications_$userId", null)
        val type = object : TypeToken<MutableList<Notification>>() {}.type
        val notifications: MutableList<Notification> = gson.fromJson(json, type) ?: mutableListOf()

        // Add the new notification
        notifications.add(notification)

        // Save updated list back to SharedPreferences
        val updatedJson = gson.toJson(notifications)
        editor.putString("notifications_$userId", updatedJson)
        editor.apply()
    }
}

package com.example.opsc7312cashsend

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotificationsActivity : AppCompatActivity() {

    private lateinit var notificationsRecyclerView: RecyclerView
    private lateinit var notificationsAdapter: NotificationsAdapter
    private lateinit var noNotificationsTextView: TextView
    private var allNotifications = mutableListOf<Notification>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        notificationsRecyclerView = findViewById(R.id.rv_notifications)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        noNotificationsTextView = findViewById(R.id.tv_no_notifications)

        // Load user ID from intent or shared preference (replace with actual logic as needed)
        val userId = intent.getStringExtra("USER_ID") ?: return

        // Load stored notifications for this user
        loadUserNotifications(userId)

        // Initialize the adapter with the notification list
        notificationsAdapter = NotificationsAdapter(allNotifications)
        notificationsRecyclerView.adapter = notificationsAdapter

        // Update the "No Notifications" visibility
        updateNoNotificationsText()
    }

    private fun loadUserNotifications(userId: String) {
        val sharedPreferences = getSharedPreferences("NotificationsPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("notifications_$userId", null)
        val type = object : TypeToken<MutableList<Notification>>() {}.type
        allNotifications = gson.fromJson(json, type) ?: mutableListOf()
    }

    private fun updateNoNotificationsText() {
        if (allNotifications.isEmpty()) {
            noNotificationsTextView.visibility = TextView.VISIBLE
            notificationsRecyclerView.visibility = RecyclerView.GONE
        } else {
            noNotificationsTextView.visibility = TextView.GONE
            notificationsRecyclerView.visibility = RecyclerView.VISIBLE
        }
    }
}

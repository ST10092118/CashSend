package com.example.opsc7312cashsend

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NotificationsActivity : AppCompatActivity() {

    private lateinit var notificationsRecyclerView: RecyclerView
    private lateinit var notificationsAdapter: NotificationsAdapter
    private lateinit var noNotificationsTextView: TextView

    private var allNotifications = mutableListOf<Notification>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        // Set up RecyclerView
        notificationsRecyclerView = findViewById(R.id.rv_notifications)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        noNotificationsTextView = findViewById(R.id.tv_no_notifications)

        // Retrieve the notification from the intent if it exists
        val newNotification: Notification? = intent.getParcelableExtra("NEW_NOTIFICATION")
        newNotification?.let {
            allNotifications.add(it) // Add new notification to the list
        }

        notificationsAdapter = NotificationsAdapter(allNotifications)
        notificationsRecyclerView.adapter = notificationsAdapter

        // Handle Back button click
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // Show DatePicker when Filter button is clicked
        findViewById<ImageButton>(R.id.btn_filter).setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create and show a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)

                // Filter notifications by the selected date
                filterNotificationsByDate(formattedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun filterNotificationsByDate(selectedDate: String) {
        val filteredNotifications = allNotifications.filter {
            it.date == selectedDate
        }

        if (filteredNotifications.isEmpty()) {
            noNotificationsTextView.visibility = TextView.VISIBLE
            notificationsRecyclerView.visibility = RecyclerView.GONE
        } else {
            noNotificationsTextView.visibility = TextView.GONE
            notificationsRecyclerView.visibility = RecyclerView.VISIBLE
            notificationsAdapter.updateData(filteredNotifications)
        }
    }
}




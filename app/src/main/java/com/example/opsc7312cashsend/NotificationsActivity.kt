package com.example.opsc7312cashsend

import android.app.DatePickerDialog
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

    // Sample Data with Date and Details field
    private var allNotifications = listOf(
        Notification("McDonald's Waterfall", "R 75,00", "16:37 PM", "18/09/2024", "Paid for a meal at McDonald's"),
        Notification("Jon & Kate's Party", "R 120,00", "16:42 PM", "17/09/2024", "Paid for drinks at Jon & Kate's Party")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        // Set up RecyclerView
        notificationsRecyclerView = findViewById(R.id.rv_notifications)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(this)
        //code was adapted from stack overflow
        //https://stackoverflow.com/questions/71572908/recycle-view-android-studio-search-well-but-just-shows-1-item
        //patel-vicky
        //https://stackoverflow.com/users/6143944/patel-vicky


        noNotificationsTextView = findViewById(R.id.tv_no_notifications)

        // Set up the adapter with all notifications initially
        notificationsAdapter = NotificationsAdapter(allNotifications)
        notificationsRecyclerView.adapter = notificationsAdapter

        // Handle Back button click
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // Show DatePicker when Filter button is clicked
        findViewById<ImageButton>(R.id.btn_filter).setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            //Code was adapted from stack Overflow
            //https://stackoverflow.com/questions/48615980/how-to-set-active-date-tommorow-in-datepicker-android-studio
            //Mahesh Vayak
            //https://stackoverflow.com/users/8143436/mahesh-vayak

            // Create and show a DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Handle the selected date here
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)

                    // Filter notifications by the selected date
                    filterNotificationsByDate(formattedDate)
                },
                year, month, day
            )
            datePickerDialog.show()

            //Code was adapted from stack Overflow
            //https://stackoverflow.com/questions/48615980/how-to-set-active-date-tommorow-in-datepicker-android-studio
            //Mahesh Vayak
            //https://stackoverflow.com/users/8143436/mahesh-vayak
        }
    }

    private fun filterNotificationsByDate(selectedDate: String) {
        val filteredNotifications = allNotifications.filter {
            it.date == selectedDate
        }

        if (filteredNotifications.isEmpty()) {
            // No notifications for this date, shows a suitable message
            noNotificationsTextView.visibility = TextView.VISIBLE
            notificationsRecyclerView.visibility = RecyclerView.GONE
        } else {
            // Display the filtered notifications
            noNotificationsTextView.visibility = TextView.GONE
            notificationsRecyclerView.visibility = RecyclerView.VISIBLE
            notificationsAdapter.updateData(filteredNotifications)
        }
    }
}



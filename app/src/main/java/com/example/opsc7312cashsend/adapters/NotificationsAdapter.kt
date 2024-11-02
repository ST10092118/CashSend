package com.example.opsc7312cashsend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R

class NotificationsAdapter(private var notifications: MutableList<Notification>) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipientTextView: TextView = itemView.findViewById(R.id.tv_recipient)
        val amountTextView: TextView = itemView.findViewById(R.id.tv_amount)
        val timeTextView: TextView = itemView.findViewById(R.id.tv_time)
        // val detailsTextView: TextView = itemView.findViewById(R.id.tv_details)

        fun bind(notification: Notification) {
            recipientTextView.text = notification.location
            amountTextView.text = notification.amount
            timeTextView.text = "${notification.time} - ${notification.date}"
            // detailsTextView.text = notification.details
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size

    fun updateData(newNotifications: List<Notification>) {
        notifications.clear()
        notifications.addAll(newNotifications)
        notifyDataSetChanged()
    }
}

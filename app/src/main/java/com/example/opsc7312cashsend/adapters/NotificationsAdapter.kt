package com.example.opsc7312cashsend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotificationsAdapter(private var notificationList: List<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipientText: TextView = itemView.findViewById(R.id.tv_recipient)
        val amountText: TextView = itemView.findViewById(R.id.tv_amount)
        val timeText: TextView = itemView.findViewById(R.id.tv_time)
        val detailsText: TextView = itemView.findViewById(R.id.tv_details)
        val detailsButton: ImageButton = itemView.findViewById(R.id.btn_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.recipientText.text = notification.recipient
        holder.amountText.text = notification.amount
        holder.timeText.text = notification.time
        holder.detailsText.text = notification.details

        // Show or hide the details based on the expanded state
        if (notification.isExpanded) {
            holder.detailsText.visibility = View.VISIBLE
        } else {
            holder.detailsText.visibility = View.GONE
        }

        // Handle Details button click
        holder.detailsButton.setOnClickListener {
            // Toggle the expanded state
            notification.isExpanded = !notification.isExpanded
            notifyItemChanged(position) // Refresh this item to show/hide details
        }
    }

    override fun getItemCount() = notificationList.size

    // Update data when filtering notifications
    fun updateData(newNotifications: List<Notification>) {
        notificationList = newNotifications
        notifyDataSetChanged()
    }
}



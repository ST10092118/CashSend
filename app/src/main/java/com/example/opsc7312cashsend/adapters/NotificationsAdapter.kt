package com.example.opsc7312cashsend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R

class NotificationsAdapter(private var notificationList: List<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipientText: TextView = itemView.findViewById(R.id.tv_recipient)
        val amountText: TextView = itemView.findViewById(R.id.tv_amount)
        val timeText: TextView = itemView.findViewById(R.id.tv_time)
        val detailsText: TextView = itemView.findViewById(R.id.tv_details)
        val detailsButton: ImageButton = itemView.findViewById(R.id.btn_details)

        //This code was adapted from Stack Overflow
        //https://stackoverflow.com/questions/68339418/cannot-resolve-symbol-viewholder-java-android-studio
        //Brett Hudson
        //https://stackoverflow.com/users/14602853/brett-hudson
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
        //This code was adapted from Stack Overflow
        //https://stackoverflow.com/questions/70012481/android-studio-kotlin-notifyitemchanged-not-calling-onbindviewholder
        //Meggrain
        //https://stackoverflow.com/users/17286933/meggrain
    }

    override fun getItemCount() = notificationList.size

    // Update data when filtering notifications
    fun updateData(newNotifications: List<Notification>) {
        notificationList = newNotifications
        notifyDataSetChanged()
    }
}



package com.example.opsc7312cashsend

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    val location: String,
    val amount: String,
    val time: String,
    val date: String
) : Parcelable


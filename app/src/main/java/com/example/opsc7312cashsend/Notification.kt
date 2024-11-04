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

//This code was adapted from StackOverflow
//https://stackoverflow.com/questions/29180889/create-an-android-model-class-in-android-studio
//Raiyan Shahid
//https://stackoverflow.com/users/14424782/raiyan-shahid


package com.opsc7311.cashsend_opscpart2.database

data class Users(
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null, // Changed from address to dateOfBirth
    val mobileNumber: String? = null,
    val profilePictureUrl: String? = null

    //This code was adapted from Stack Overflow
    //https://stackoverflow.com/questions/29180889/create-an-android-model-class-in-android-studio
    //Raiyan Shahid
    //https://stackoverflow.com/users/14424782/raiyan-shahid

)

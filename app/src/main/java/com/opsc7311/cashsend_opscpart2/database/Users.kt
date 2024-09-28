package com.opsc7311.cashsend_opscpart2.database

data class Users(
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null, // Changed from address to dateOfBirth
    val mobileNumber: String? = null,
    val profilePictureUrl: String? = null // This field can be retained if you plan to use it
)

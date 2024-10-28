package com.example.opsc7312cashsend

import com.example.opsc7312cashsend.models.LoginRequest
import com.example.opsc7312cashsend.models.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class User(
    val userId: String,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val mobileNumber: String
)

data class Card(
    val userId: String,
    val cardNumber: String,
    val expiryDate: String,
    val cardHolderName: String
)

interface ApiService {
    @POST("api/loginUser")
    fun loginUser(@Body request: LoginRequest): Call<UserLoginResponse>

    @POST("api/addUser")
    fun addUser(@Body user: User): Call<Void>

    @POST("api/addCard")
    fun addCard(@Body card: Card): Call<Void>
}

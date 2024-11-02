package com.example.opsc7312cashsend

import com.example.opsc7312cashsend.models.Card
import com.example.opsc7312cashsend.models.LoginRequest
import com.example.opsc7312cashsend.models.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class User(
    val userId: String,
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val mobileNumber: String,
    val password: String
)



interface ApiService {
    @POST("api/loginUser")
    fun loginUser(@Body request: LoginRequest): Call<UserLoginResponse>

    @POST("api/addUser")
    fun addUser(@Body user: User): Call<Void>

    @POST("api/addCard")
    fun addCard(@Body card: Card): Call<Void>

    @GET("api/getCards/{userId}")
    fun getCards(@Path("userId") userId: String): Call<Map<String, Card>>
}
package com.example.opsc7312cashsend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//This code was adapted from StackOverflow
//https://stackoverflow.com/questions/47511962/how-to-send-post-request-in-retrofit-2-android
//Murat Güç
//https://stackoverflow.com/users/7393603/murat-g%c3%bc%c3%a7
interface StripeApiService {
    @POST("create-customer-and-setup-intent")
    fun createCustomerAndSetupIntent(@Body request: CreateCustomerRequest): Call<CreateCustomerResponse>
}

// Request data class for creating a customer and setup intent
data class CreateCustomerRequest(
    val email: String,
)

// Response data class for creating a customer and setup intent
data class CreateCustomerResponse(
    val client_secret: String,
    val customer_id: String,
    val error: ErrorResponse? = null
)

// Error response data class for handling errors
data class ErrorResponse(
    val message: String
)


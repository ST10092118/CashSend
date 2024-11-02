package com.example.opsc7312cashsend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

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


package com.example.opsc7312cashsend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL =
        "https://cashsendapi.onrender.com"  // Check this is correct and accessible

    // Lazy initialization of Retrofit and ApiService
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Ensure your API responses use JSON
            .build()
            .create(ApiService::class.java)
    }
}

object StripeApiInstance {
    private const val BASE_URL = "https://stripe-payment-cvjx.onrender.com"  // URL for Stripe payments

    val api: StripeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeApiService::class.java)
    }
}

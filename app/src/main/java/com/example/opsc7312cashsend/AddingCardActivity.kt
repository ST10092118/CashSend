package com.example.opsc7312cashsend

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmSetupIntentParams
import com.stripe.android.view.CardInputWidget
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException

class AddingCardActivity : AppCompatActivity() {

    private lateinit var stripe: Stripe
    private lateinit var cardInputWidget: CardInputWidget

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_card)

        // Initialize Stripe with your publishable key
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QFjxRC6nbcTFcrEOlRsDQvxY9VCjPw6hHCSiWFHVpRlbFISi0A5r8ZTObhNHW35WZjDBWnEc5kEerVzMrwsxmvs00E42i3IYx"
        )
        stripe = Stripe(
            applicationContext,
            PaymentConfiguration.getInstance(applicationContext).publishableKey
        )

        cardInputWidget = findViewById(R.id.card_input_widget)
        val submitButton = findViewById<Button>(R.id.submit_Card_button)

        // Retrieve email from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val customerEmail = sharedPreferences.getString("user_email", null)

        submitButton.setOnClickListener {
            if (customerEmail != null) {
                // Call createCustomerAndFetchSetupIntent with only the email
                createCustomerAndFetchSetupIntent(customerEmail) { clientSecret ->
                    if (clientSecret != null) {
                        val paymentMethodParams = cardInputWidget.paymentMethodCreateParams
                        if (paymentMethodParams != null) {
                            val confirmParams = ConfirmSetupIntentParams.create(paymentMethodParams, clientSecret)
                            stripe.confirmSetupIntent(this, confirmParams)
                        } else {
                            Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch client secret", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "User email not found. Please log in again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createCustomerAndFetchSetupIntent(
        email: String,
        callback: (String?) -> Unit
    ) {
        val url = "http://10.0.2.2:4242/create-customer-and-setup-intent"
        val client = OkHttpClient()
        val jsonBody = JSONObject().apply {
            put("email", email) // Only include the email in the request body
        }

        val request = Request.Builder()
            .url(url)
            .post(
                RequestBody.create(
                    "application/json".toMediaType(),
                    jsonBody.toString()
                )
            )
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("StripeSetupIntent", "Failed to fetch client secret: ${e.message}")
                runOnUiThread {
                    Toast.makeText(
                        this@AddingCardActivity,
                        "Failed to fetch client secret",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.use {
                        val responseBodyString = response.body?.string() ?: ""
                        val json = JSONObject(responseBodyString)
                        val clientSecret = json.optString("client_secret", null)
                        Log.d("StripeSetupIntent", "Client secret fetched successfully")
                        runOnUiThread {
                            callback(clientSecret)
                        }
                    }
                } else {
                    Log.e("StripeSetupIntent", "Server error: ${response.code}")
                    runOnUiThread {
                        Toast.makeText(
                            this@AddingCardActivity,
                            "Server error: ${response.code}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    callback(null)
                }
            }
        })
    }
}

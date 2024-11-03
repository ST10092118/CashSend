package com.example.opsc7312cashsend

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.OPSC7312CashSend.R
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmSetupIntentParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import com.stripe.android.view.CardInputWidget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddingCardActivity : AppCompatActivity() {

    private lateinit var cardInputWidget: CardInputWidget
    private lateinit var paymentLauncher: PaymentLauncher
    private lateinit var apiService: StripeApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_card)

        // Initialize Stripe with your publishable key
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51QFjxRC6nbcTFcrEOlRsDQvxY9VCjPw6hHCSiWFHVpRlbFISi0A5r8ZTObhNHW35WZjDBWnEc5kEerVzMrwsxmvs00E42i3IYx"
        )

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4242/") // Ensure this matches your server URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(StripeApiService::class.java)

        // Initialize PaymentLauncher
        paymentLauncher = PaymentLauncher.create(
            this,
            PaymentConfiguration.getInstance(applicationContext).publishableKey,
            null,
            ::handlePaymentResult
        )

        cardInputWidget = findViewById(R.id.card_input_widget)
        val submitButton = findViewById<Button>(R.id.submit_Card_button)

        // Retrieve email from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val customerEmail = sharedPreferences.getString("user_email", null)

        // Debug log for user email
        Log.d("AddingCardActivity", "Customer Email: $customerEmail")

        submitButton.setOnClickListener {
            if (customerEmail != null) {
                createCustomerAndFetchSetupIntent(customerEmail) { clientSecret ->
                    if (clientSecret != null) {
                        val paymentMethodParams = cardInputWidget.paymentMethodCreateParams
                        if (paymentMethodParams != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                promptBiometricAuth {
                                    val confirmParams = ConfirmSetupIntentParams.create(
                                        paymentMethodParams,
                                        clientSecret
                                    )
                                    paymentLauncher.confirm(confirmParams)
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Biometric authentication is not supported on this device.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch client secret", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "User email not found. Please log in again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun promptBiometricAuth(callback: () -> Unit) {
        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    callback() // Call the payment function upon success
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(
                        this@AddingCardActivity,
                        "Authentication failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Scan your fingerprint to confirm payment")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun createCustomerAndFetchSetupIntent(
        email: String,
        callback: (String?) -> Unit
    ) {
        val jsonBody = CreateCustomerRequest(email) // CreateCustomerRequest should accept only email now

        // Call to your StripeApiService
        val call: Call<CreateCustomerResponse> = apiService.createCustomerAndSetupIntent(jsonBody)
        call.enqueue(object : Callback<CreateCustomerResponse> {
            override fun onResponse(
                call: Call<CreateCustomerResponse>,
                response: Response<CreateCustomerResponse>
            ) {
                if (response.isSuccessful) {
                    val clientSecret = response.body()?.client_secret
                    callback(clientSecret)
                } else {
                    Log.e(
                        "StripeSetupIntent",
                        "Server error: ${response.code()} ${response.message()} ${response.errorBody()?.string()}"
                    )
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CreateCustomerResponse>, t: Throwable) {
                Log.e("StripeSetupIntent", "Failure: ${t.message}")
                callback(null)
            }
        })
    }

    private fun handlePaymentResult(result: PaymentResult) {
        when (result) {
            is PaymentResult.Failed -> {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Payment failed: ${result.throwable.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Log.e("PaymentIntent", "Payment failed: ${result.throwable}")
                navigateToHome() // Navigate to home on failure
            }

            is PaymentResult.Canceled -> {
                runOnUiThread {
                    Toast.makeText(this, "Payment was canceled", Toast.LENGTH_SHORT).show()
                }
                Log.d("PaymentIntent", "Payment canceled")
                navigateToHome()
            }

            is PaymentResult.Completed -> {
                runOnUiThread {
                    Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show()
                }
                Log.d("PaymentIntent", "Payment successful")
                navigateToHome() // Navigate to home on success

            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

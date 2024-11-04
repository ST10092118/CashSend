package com.example.opsc7312cashsend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.models.Card
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//This code was adapted from StackOverflow and github
//https://stackoverflow.com/questions/76380893/paymentconfiguration-is-not-recognized-in-android-studio-java
//https://github.com/stripe/stripe-android/issues/6250
//karllekko
//https://stackoverflow.com/users/9769731/karllekko

//This code was adapted from StackOverflow
//https://stackoverflow.com/questions/74332153/android-studio-how-to-create-biometricmanager-and-other-bugs
//Antonio
//https://stackoverflow.com/users/15511167/antonio
class AddNewCardActivity : AppCompatActivity() {

    private lateinit var etCardNumber: EditText
    private lateinit var etCardName: EditText
    private lateinit var etExpirationDate: EditText
    private lateinit var etCVV: EditText
    private lateinit var btnAddCard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)

        // Initialize views
        etCardNumber = findViewById(R.id.et_card_number)
        etCardName = findViewById(R.id.et_card_name)
        etExpirationDate = findViewById(R.id.et_expiration_date)
        etCVV = findViewById(R.id.et_cvv)
        btnAddCard = findViewById(R.id.btn_add_card)

        // Add button click listener
        btnAddCard.setOnClickListener {
            addCard()
        }
    }

    private fun addCard() {
        val cardNumber = etCardNumber.text.toString()
        val cardName = etCardName.text.toString()
        val expirationDate = etExpirationDate.text.toString()
        val cvv = etCVV.text.toString()

        // Validate card details
        if (validateCardDetails(cardNumber, cardName, expirationDate, cvv)) {
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (userId != null) {
                val newCard = Card(userId, cardNumber, expirationDate, cardName)

                // Call the Retrofit API to add the card to the server
                RetrofitInstance.api.addCard(newCard).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AddNewCardActivity, "Card added successfully", Toast.LENGTH_SHORT).show()
                            // Navigate back to CardSelectionActivity
                            val intent = Intent(this@AddNewCardActivity, CardSelectionActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@AddNewCardActivity, "Failed to add card: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@AddNewCardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateCardDetails(cardNumber: String, cardName: String, expirationDate: String, cvv: String): Boolean {
        if (cardNumber.length != 16) {
            etCardNumber.error = "Enter a valid 16-digit card number"
            return false
        }
        if (cardName.isEmpty()) {
            etCardName.error = "Enter the cardholder's name"
            return false
        }
        if (!expirationDate.matches(Regex("(0[1-9]|1[0-2])/[0-9]{2}"))) {
            etExpirationDate.error = "Enter a valid expiration date (MM/YY)"
            return false
        }
        if (cvv.length != 3) {
            etCVV.error = "Enter a valid 3-digit CVV"
            return false
        }
        return true
    }
}

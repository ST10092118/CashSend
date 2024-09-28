package com.example.opsc7312cashsend

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.opsc7312cashsend.models.Card

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
            // Add the new card to the repository
            val newCard = Card(cardName, cardNumber, expirationDate)
            CardRepository.addCard(newCard)

            // Show confirmation and close activity
            Toast.makeText(this, "Card added successfully", Toast.LENGTH_SHORT).show()
            finish() // Return to the previous screen (CardSelectionActivity)
        }
    }

    private fun validateCardDetails(cardNumber: String, cardName: String, expirationDate: String, cvv: String): Boolean {
        if (cardNumber.length != 16 || !TextUtils.isDigitsOnly(cardNumber)) {
            etCardNumber.error = "Enter a valid 16-digit card number"
            return false
        }
        if (TextUtils.isEmpty(cardName)) {
            etCardName.error = "Enter the cardholder's name"
            return false
        }
        if (!expirationDate.matches(Regex("(0[1-9]|1[0-2])/[0-9]{2}"))) {
            etExpirationDate.error = "Enter a valid expiration date (MM/YY)"
            return false
        }
        if (cvv.length != 3 || !TextUtils.isDigitsOnly(cvv)) {
            etCVV.error = "Enter a valid 3-digit CVV"
            return false
        }
        return true
    }
}




package com.example.opsc7312cashsend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.adapters.CardAdapter
import com.example.opsc7312cashsend.models.Card
import com.google.firebase.auth.FirebaseAuth
import com.opsc7311.cashsend_opscpart2.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardSelectionActivity : AppCompatActivity() {

    //This code was adapted from StackOverflow
    //https://stackoverflow.com/questions/53976480/android-retrofit-display-in-recyclerview
    //Martin Zeitler
    //https://stackoverflow.com/users/549372/martin-zeitler
    private lateinit var recyclerViewCards: RecyclerView
    private lateinit var btnAddCard: ImageButton
    private lateinit var btnBack: ImageButton
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_selection)

        // Initialize views
        btnBack = findViewById(R.id.btn_back)
        btnAddCard = findViewById(R.id.addCardImage)
        recyclerViewCards = findViewById(R.id.recycler_view_cards)

        recyclerViewCards.layoutManager = LinearLayoutManager(this)

        // Add click listeners
        btnBack.setOnClickListener {
            finish()
        }

        btnAddCard.setOnClickListener {
            val intent = Intent(this, AddNewCardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        checkAndLoadCards()
    }

    // Check user authentication and load cards
    private fun checkAndLoadCards() {
        auth.currentUser?.let {
            Log.d("CardSelectionActivity", "User is authenticated with UID: ${it.uid}")
            reloadUserAndLoadCards(it.uid)
        } ?: run {
            Log.e("CardSelectionActivity", "User is not authenticated. Session may have expired.")
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }
    }

    // Reloads user session to ensure it's active and then loads cards
    private fun reloadUserAndLoadCards(userId: String) {
        auth.currentUser?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful && auth.currentUser != null) {
                Log.d("CardSelectionActivity", "User reloaded successfully. Loading cards for UID: $userId")
                loadCards(userId)
            } else {
                Log.e("CardSelectionActivity", "Failed to reload user. Redirecting to login.")
                Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            }
        }
    }

    private fun loadCards(userId: String) {
        RetrofitInstance.api.getCards(userId).enqueue(object : Callback<Map<String, Card>> {
            override fun onResponse(call: Call<Map<String, Card>>, response: Response<Map<String, Card>>) {
                if (response.isSuccessful) {
                    val cardMap = response.body() ?: emptyMap()
                    val cardList = cardMap.values.toList()
                    Log.d("CardSelectionActivity", "Loaded cards: $cardList")

                    if (cardList.isNotEmpty()) {
                        recyclerViewCards.adapter = CardAdapter(cardList) { selectedCard ->
                            Toast.makeText(this@CardSelectionActivity, "Selected: ${selectedCard.cardHolderName}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@CardSelectionActivity, "No cards found.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("CardSelectionActivity", "Failed to load cards: ${response.message()}, Code: ${response.code()}")
                    Toast.makeText(this@CardSelectionActivity, "Failed to load cards: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, Card>>, t: Throwable) {
                Log.e("CardSelectionActivity", "Error: ${t.message}", t)
                Toast.makeText(this@CardSelectionActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Navigate to the login activity
    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardSelectionActivity : AppCompatActivity() {

    private lateinit var recyclerViewCards: RecyclerView
    private lateinit var btnAddCard: ImageButton
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_selection)

        // Initialize views
        btnBack = findViewById(R.id.btn_back)
        btnAddCard = findViewById(R.id.addCardImage)
        recyclerViewCards = findViewById(R.id.recycler_view_cards)

        recyclerViewCards.layoutManager = LinearLayoutManager(this)

        // Load cards when activity is created
        loadCards()

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
        loadCards()
    }

    private fun loadCards() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
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
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

}

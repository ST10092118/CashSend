package com.example.opsc7312cashsend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.adapters.CardAdapter

class CardSelectionActivity : AppCompatActivity() {

    private lateinit var recyclerViewCards: RecyclerView
    private lateinit var btnAddCard: Button
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_selection)

        // Initialize views
        btnBack = findViewById(R.id.btn_back)
        btnAddCard = findViewById(R.id.btn_add_card)
        recyclerViewCards = findViewById(R.id.recycler_view_cards)

        recyclerViewCards.layoutManager = LinearLayoutManager(this)
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
        val cardList = CardRepository.getCardList()
        recyclerViewCards.adapter = CardAdapter(cardList) { selectedCard ->
            // Handle card selection logic here (e.g., store or display the selected card)
            Toast.makeText(this, "Selected: ${selectedCard.cardName}", Toast.LENGTH_SHORT).show()
        }
    }
}




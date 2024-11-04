package com.example.opsc7312cashsend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.models.Card

class CardAdapter(
    private val cardList: List<Card>,
    private val onCardSelected: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var selectedCard: Card? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    //this code was adapted from GeeksForGeeks
    //https://www.geeksforgeeks.org/how-to-select-single-radiobutton-in-android-recyclerview/

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.bind(card)

        // Set the selected card when item is clicked
        holder.itemView.setOnClickListener {
            selectedCard = card
            notifyDataSetChanged()
            onCardSelected(card)
        }
        // Update the RadioButton's checked state based on selection
        holder.radioButton.isChecked = card == selectedCard
    }

    override fun getItemCount(): Int = cardList.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardHolderName: TextView = itemView.findViewById(R.id.cardHolderName)
        val maskedCardNumber: TextView = itemView.findViewById(R.id.maskedCardNumber)
        val cardImage: ImageView = itemView.findViewById(R.id.cardImage)
        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)

        fun bind(card: Card) {
            cardHolderName.text = card.cardHolderName
            maskedCardNumber.text = "**** **** **** ${card.cardNumber.takeLast(4)}"

            // Set a sample card image, e.g., Visa (can be customized based on card type if available)
            cardImage.setImageResource(R.drawable.visa) // Use the appropriate image here
        }
    }
}

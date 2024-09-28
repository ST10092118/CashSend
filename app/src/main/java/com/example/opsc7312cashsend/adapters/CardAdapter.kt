package com.example.opsc7312cashsend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.OPSC7312CashSend.R
import com.example.opsc7312cashsend.models.Card

class CardAdapter(private val cardList: List<Card>, private val onCardSelected: (Card) -> Unit) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // To track the selected card

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]
        holder.tvCardName.text = card.cardName
        holder.tvCardNumber.text = card.cardNumber

        // Show the selection indicator for the selected card
        holder.ivSelectedIndicator.visibility = if (position == selectedPosition) View.VISIBLE else View.GONE

        // Handle card click
        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition) // Remove the indicator from the previously selected card
            notifyItemChanged(selectedPosition) // Show the indicator for the newly selected card
            onCardSelected(card) // Trigger the selection callback
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCardName: TextView = itemView.findViewById(R.id.tv_card_name)
        val tvCardNumber: TextView = itemView.findViewById(R.id.tv_card_number)
        val ivSelectedIndicator: ImageView = itemView.findViewById(R.id.iv_selected_indicator)
    }
}


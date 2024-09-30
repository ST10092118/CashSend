package com.example.opsc7312cashsend
import com.example.opsc7312cashsend.models.Card

object CardRepository {

    // Initialize with card data
    private val cardList = mutableListOf(
        Card("Visa", "**** **** **** 1234", "07/25"),
        Card("Mastercard", "**** **** **** 9876", "10/23")
    )

    fun getCardList(): MutableList<Card> {
        return cardList
    }

    fun addCard(card: Card) {
        cardList.add(card)
    }
}

//This code was adapted from Stack overflow
//https://stackoverflow.com/questions/53436771/what-are-android-studio-repositories-and-how-do-they-work


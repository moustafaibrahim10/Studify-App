package com.example.controller

import com.example.data.DataRepository
import com.example.model.Deck
import com.example.model.Flashcard
import com.example.model.Subject

class DeckController {

    fun addDeck(subject: Subject, title: String): Deck {
        val deck = Deck(title, subject.name)
        subject.decks.add(deck)
        DataRepository.addDeck(deck)
        return deck
    }

    fun removeDeck(subject: Subject, deck: Deck) {
        subject.decks.remove(deck)
        DataRepository.removeDeck(deck)
    }

    fun addFlashcard(deck: Deck, question: String, answer: String) {
        val card = Flashcard(question, answer)
        deck.cards.add(card)
    }

    fun removeFlashcard(deck: Deck, card: Flashcard) {
        deck.cards.remove(card)
    }

    fun getDeckByTitle(title: String): Deck? {
        return DataRepository.getDeckByTitle(title)
    }
}

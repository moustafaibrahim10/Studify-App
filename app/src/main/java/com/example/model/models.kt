package com.example.model

import java.time.LocalDate

//Subjects Model
data class Subject(
    val name: String,
    var progress: Int = 0,
    val tasks: MutableList<Task> = mutableListOf(),
    val decks: MutableList<Deck> = mutableListOf()
)

//FlashcardsDeck Model
data class Deck(
    val title: String,
    val cards: MutableList<Flashcard> = mutableListOf()
)

//Flashcards Model
data class Flashcard(
    val question: String,
    val answer: String
)

//Task Model
data class Task(
    var title: String,
    var due: LocalDate,
    var completed: Boolean = false
)


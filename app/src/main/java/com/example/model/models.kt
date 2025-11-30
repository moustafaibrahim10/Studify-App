package com.example.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.time.LocalDate

data class User(
    val username: String,
    val email: String,
    var password: String,
    val subjects: MutableList<Subject> = mutableStateListOf(),
    val tasks: MutableList<Task> = mutableStateListOf(),
    val decks: MutableList<Deck> = mutableStateListOf()
)





//Subjects Model
data class Subject(
    val name: String,
    val tasks: MutableList<Task> = mutableStateListOf(),
    val decks: MutableList<Deck> = mutableStateListOf()
){
    var currentprogress by mutableStateOf(0)

}

//FlashcardsDeck Model
data class Deck(
    val title: String,
    val subject: String,
    val cards: MutableList<Flashcard> = mutableStateListOf()
)

//Flashcards Model
data class Flashcard(
    val question: String,
    val answer: String
)

//Task Model
data class Task(
    var title: String,
    var subject: String,
    var due: LocalDate,
    var completed: Boolean = false
){
    var isDone by mutableStateOf(completed)
}


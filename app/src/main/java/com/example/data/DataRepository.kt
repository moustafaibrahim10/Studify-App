package com.example.data

import androidx.compose.runtime.mutableStateListOf
import com.example.model.Subject
import com.example.model.Deck
import com.example.model.Task

object DataRepository {

    val subjects = mutableStateListOf<Subject>()
    val decks = mutableStateListOf<Deck>()
    val tasks = mutableStateListOf<Task>()

    // SUBJECTS
    fun addSubject(subject: Subject) {
        subjects.add(subject)
    }

    fun removeSubject(subject: Subject) {
        subjects.remove(subject)
    }

    fun getSubjectByName(name: String): Subject? {
        return subjects.find { it.name == name }
    }

    // DECKS
    fun addDeck(deck: Deck) {
        decks.add(deck)
    }

    fun removeDeck(deck: Deck) {
        decks.remove(deck)
    }

    fun getDeckByTitle(title: String): Deck? {
        return decks.find { it.title == title }
    }

    // TASKS
    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun removeTask(task: Task) {
        tasks.remove(task)
    }
}

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

    fun addSubjectTask(subject: Subject?, task: Task) {
        subjects.find { it == subject }?.tasks?.add(task)
    }
    fun addSubjectDeck(subject: Subject?, deck: Deck) {
        subjects.find { it == subject }?.decks?.add(deck)
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

        getSubjectByName(task.subject)?.tasks?.add(task)
            ?: subjects.add(Subject(task.subject, mutableStateListOf(task)))
    }

    fun removeTask(task: Task) {
        tasks.remove(task)
    }
}

package com.example.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import com.example.model.Subject
import com.example.model.Deck
import com.example.model.Flashcard
import com.example.model.Task
import com.example.model.User
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
object DataRepository {

    val users = mutableStateListOf<User>()
    var currentUser: User? = null
    init {
        // Dummy tasks
        val mathTask1 = Task(
            title = "Solve chapter 3 problems",
            subject = "Math",
            due = LocalDate.now().plusDays(1),
            completed = false
        )

        val physicsTask1 = Task(
            title = "Revise Newton’s Laws",
            subject = "Physics",
            due = LocalDate.now().plusDays(7),
            completed = false
        )

        // Dummy flashcards
        val mathDeck = Deck(
            title = "Math Basics",
            subject = "Math",
            cards = mutableStateListOf(
                Flashcard("What is 2+2?", "4"),
                Flashcard("Derivative of x²?", "2x")
            )
        )

        val physicsDeck = Deck(
            title = "Physics Concepts",
            subject = "Physics",
            cards = mutableStateListOf(
                Flashcard("Define velocity.", "Rate of change of displacement."),
                Flashcard("What is inertia?", "Resistance to change in motion.")
            )
        )

        // Dummy subjects
        val mathSubject = Subject(
            name = "Math",
            tasks = mutableStateListOf(mathTask1),
            decks = mutableStateListOf(mathDeck)
        )

        val physicsSubject = Subject(
            name = "Physics",
            tasks = mutableStateListOf(physicsTask1),
            decks = mutableStateListOf(physicsDeck)
        )

        // Final dummy user
        val dummy = User(
            username = "testuser",
            email = "test@example.com",
            password = "123456",
            subjects = mutableStateListOf(mathSubject, physicsSubject),
            tasks = mutableStateListOf(mathTask1, physicsTask1),
            decks = mutableStateListOf(mathDeck, physicsDeck)
        )

        users.add(dummy)
    }


    fun login(username: String, password: String): Boolean {
        val user = users.find { it.username == username && it.password == password }
        return if (user != null) {
            currentUser = user
            true
        } else false
    }

    fun createAccount(email:String,username: String, password: String): Boolean {
        if (users.any { it.username == username }) return false // Username exists
        val newUser = User(
            username = username,
            password = password,
            subjects = mutableStateListOf(),
            tasks = mutableStateListOf(),
            decks = mutableStateListOf(),
            email = email
        )
        users.add(newUser)
        currentUser = newUser
        return true
    }
    fun addSubject(subject: Subject) {
        currentUser?.subjects?.add(subject)
    }

    fun removeSubject(subject: Subject) {
        currentUser?.subjects?.remove(subject)
    }

    fun addSubjectTask(subject: Subject?, task: Task) {
        currentUser?.subjects?.find { it == subject }?.tasks?.add(task)
    }

    fun addSubjectDeck(subject: Subject?, deck: Deck) {
        currentUser?.subjects?.find { it == subject }?.decks?.add(deck)
    }


    fun getSubjectByName(name: String): Subject? {
        return currentUser?.subjects?.find { it.name == name }
    }

    // DECKS
    fun addDeck(deck: Deck) {
        currentUser?.decks?.add(deck)

        getSubjectByName(deck.subject)?.decks?.add(deck)
            ?: currentUser?.subjects?.add(
                Subject(deck.subject, decks = mutableStateListOf(deck))
            )
    }

    fun removeDeck(deck: Deck) {
        currentUser?.decks?.remove(deck)
    }

    fun getDeckByTitle(title: String): Deck? {
        return currentUser?.decks?.find { it.title == title }
    }

    // TASKS
    fun addTask(task: Task) {
        currentUser?.tasks?.add(task)

        getSubjectByName(task.subject)?.tasks?.add(task)
            ?: currentUser?.subjects?.add(
                Subject(task.subject, tasks = mutableStateListOf(task))
            )
    }

    fun removeTask(task: Task) {
        currentUser?.tasks?.remove(task)
    }

    fun addFlashcardToDeck(deckName: String, question: String, answer: String) {
        val deck = getDeckByTitle(deckName)
        deck?.cards?.add(Flashcard(question, answer))
    }
}

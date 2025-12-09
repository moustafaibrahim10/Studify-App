package com.example.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
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
        // ----------------- Tasks -----------------
        val mathTasks = mutableListOf(
            Task("Solve chapter 3 problems", "Math", LocalDate.now().plusDays(1), false),
            Task("Prepare for algebra quiz", "Math", LocalDate.now().plusDays(3), true),
            Task("Review trigonometry formulas", "Math", LocalDate.now().plusDays(2), true),
            Task("Practice integrals exercises", "Math", LocalDate.now().plusDays(5), false)
        )

        val physicsTasks = mutableListOf(
            Task("Revise Newton’s Laws", "Physics", LocalDate.now().plusDays(7), false),
            Task("Complete lab report on motion", "Physics", LocalDate.now().plusDays(2), true),
            Task("Study electricity chapter", "Physics", LocalDate.now().plusDays(4), false),
            Task("Prepare for momentum test", "Physics", LocalDate.now().plusDays(1), true)
        )

        val csTasks = mutableListOf(
            Task("Implement stack in Java", "Computer Science", LocalDate.now().plusDays(4), true),
            Task("Study OOP principles", "Computer Science", LocalDate.now().plusDays(1), true),
            Task("Write LinkedList implementation", "Computer Science", LocalDate.now().plusDays(3), false),
            Task("Review recursion examples", "Computer Science", LocalDate.now().plusDays(2), true)
        )

        val englishTasks = mutableListOf(
            Task("Read Chapter 5 of novel", "English", LocalDate.now().plusDays(3), true),
            Task("Write essay on symbolism", "English", LocalDate.now().plusDays(5), true),
            Task("Grammar exercises page 32", "English", LocalDate.now().plusDays(2), true)
        )

        // ----------------- Flashcard Decks -----------------
        val mathDeck = Deck("Math Basics", "Math", mutableStateListOf(
            Flashcard("What is 2+2?", "4"),
            Flashcard("Derivative of x²?", "2x"),
            Flashcard("Integral of 1/x?", "ln|x| + C"),
            Flashcard("sin²θ + cos²θ = ?", "1")
        ))

        val physicsDeck = Deck("Physics Concepts", "Physics", mutableStateListOf(
            Flashcard("Define velocity.", "Rate of change of displacement."),
            Flashcard("What is inertia?", "Resistance to change in motion."),
            Flashcard("State Newton's 2nd Law.", "F = ma"),
            Flashcard("Unit of force?", "Newton (N)")
        ))

        val csDeck = Deck("OOP Principles", "Computer Science", mutableStateListOf(
            Flashcard("What is encapsulation?", "Wrapping data & methods in a class"),
            Flashcard("What is inheritance?", "A class can inherit properties of another class"),
            Flashcard("Polymorphism meaning?", "Ability of object to take many forms")
        ))

        val csDeck2 = Deck("Data Structures", "Computer Science", mutableStateListOf(
            Flashcard("What is a stack?", "LIFO data structure"),
            Flashcard("What is a queue?", "FIFO data structure"),
            Flashcard("Binary Tree property?", "Left < Root < Right")
        ))

        val englishDeck = Deck("English Vocabulary", "English", mutableStateListOf(
            Flashcard("Aberration meaning?", "A departure from what is normal"),
            Flashcard("Cacophony meaning?", "Harsh, discordant sound")
        ))

        // ----------------- Subjects -----------------
        val mathSubject = Subject("Math", tasks = mathTasks.toMutableStateList(), decks = mutableStateListOf(mathDeck))
        val physicsSubject = Subject("Physics", tasks = physicsTasks.toMutableStateList(), decks = mutableStateListOf(physicsDeck))
        val csSubject = Subject("Computer Science", tasks = csTasks.toMutableStateList(), decks = mutableStateListOf(csDeck, csDeck2))
        val englishSubject = Subject("English", tasks = englishTasks.toMutableStateList(), decks = mutableStateListOf(englishDeck))

        // ----------------- Calculate currentprogress -----------------
        fun calculateProgress(subject: Subject) {
            val completed = subject.tasks.count { it.completed }
            val total = subject.tasks.size
            subject.currentprogress = if (total == 0) 0 else (completed * 100) / total
        }

        val allSubjects = listOf(mathSubject, physicsSubject, csSubject, englishSubject)
        allSubjects.forEach { calculateProgress(it) }

        // ----------------- Dummy User -----------------
        val dummy = User(
            username = "testuser",
            email = "test@example.com",
            password = "123456",
            subjects = allSubjects.toMutableStateList(),
            tasks = (mathTasks + physicsTasks + csTasks + englishTasks).toMutableStateList(),
            decks = mutableStateListOf(mathDeck, physicsDeck, csDeck, csDeck2, englishDeck)
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

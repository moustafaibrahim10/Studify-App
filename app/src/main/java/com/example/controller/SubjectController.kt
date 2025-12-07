package com.example.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.DataRepository
import com.example.model.Deck
import com.example.model.Subject
import com.example.model.Task

class SubjectController {

    @RequiresApi(Build.VERSION_CODES.O)
    fun addSubject(name: String): Subject {
        val subject = Subject(name)
        DataRepository.addSubject(subject)
        return subject
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun removeSubject(subject: Subject) {
        DataRepository.removeSubject(subject)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSubjectByName(name: String): Subject? {
        return DataRepository.getSubjectByName(name)
    }

    fun updateProgress(subject: Subject) {
        val total = subject.tasks.size
        val completed = subject.tasks.count { it.completed }
        subject.currentprogress = if (total > 0) completed * 100 / total else 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask(subject: Subject, task: Task) {
        subject.tasks.add(task)
        DataRepository.addTask(task)
        updateProgress(subject)
    }

    fun addDeck(subject: Subject, deck: Deck) {
        subject.decks.add(deck)
        DataRepository.addDeck(deck)
    }
}

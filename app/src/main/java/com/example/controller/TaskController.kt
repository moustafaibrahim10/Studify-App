package com.example.controller

import com.example.data.DataRepository
import com.example.model.Subject
import com.example.model.Task
import java.time.LocalDate

class TaskController(private val subjectController: SubjectController) {

    fun markTaskComplete(subject: Subject, task: Task, completed: Boolean) {
        task.completed = completed
        subjectController.updateProgress(subject)
    }

    fun editTask(task: Task, newTitle: String, newDue: LocalDate) {
        task.title = newTitle
        task.due = newDue
    }

    fun deleteTask(subject: Subject, task: Task) {
        subject.tasks.remove(task)
        DataRepository.removeTask(task)
        subjectController.updateProgress(subject)
    }
}

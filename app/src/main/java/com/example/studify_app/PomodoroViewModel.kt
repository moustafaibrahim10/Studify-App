package com.example.studify_app // تأكد إن السطر ده موجود ومطابق لاسم الباكدج عندك

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PomodoroViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("pomodoro_prefs", Context.MODE_PRIVATE)

    private val _studyLength = MutableStateFlow(prefs.getInt("study_length", 25))

    val studyLength = _studyLength.asStateFlow()

    fun updateStudyLength(newLength: Int) {
        viewModelScope.launch {
            _studyLength.value = newLength
            prefs.edit().putInt("study_length", newLength).apply()
        }
    }
}

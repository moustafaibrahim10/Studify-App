package com.example.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.composetest.ui.theme.SubjectDetailsScreen
import com.example.composetest.ui.theme.SubjectsScreen
import com.example.composetest.ui.theme.appCurrentScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                if (appCurrentScreen == "subjects") {
                    SubjectsScreen()
                } else {
                    SubjectDetailsScreen()
                }
            }
        }
    }
}

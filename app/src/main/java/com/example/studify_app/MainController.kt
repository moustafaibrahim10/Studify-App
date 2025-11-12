package com.example.studify_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainController : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen(navController)
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = when (currentRoute) {
        "home", "subjects", "calendar", "pomodoro", "profile" -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pomodoro", // تقدر تغيرها لو عايز تبدأ بالهوم
            modifier = Modifier.padding(innerPadding)
        ) {

            // الصفحات الرئيسية
            composable("home") { /* TODO: HomeScreen(navController) */ }
            composable("subjects") { /* TODO: SubjectsScreen(navController) */ }
            composable("calendar") { /* TODO: CalendarScreen(navController) */ }
            composable("pomodoro") { PomodoroScreen(navController) }
            composable("profile") { ProfilePic(navController) }

            // شاشات فرعية (من غير ناف بار)
            composable("Counter") { CounterScreen(navController) }
            composable("sessionComplete") { CompleteSessionScreen(navController) }
            composable("addSession") { AddSessionScreen(navController) }
            composable("addFlashCard") { /* TODO: AddFlashCardScreen(navController) */ }

            composable("settings") { SettingsScr(navController) }
            composable("edit") { EditScr(navController) }
        }
    }

}

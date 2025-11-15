package com.example.studify_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetest.ui.theme.DeckUi
import com.example.composetest.ui.theme.SubjectDetailsScreen
import com.example.composetest.ui.theme.SubjectUi
import com.example.composetest.ui.theme.SubjectsScreen
import com.example.composetest.ui.theme.TaskUi
import com.example.finalfinalefinal.deck_details_screen
import com.example.finalfinalefinal.decks_list
import com.example.finalfinalefinal.flashCard_testing_screen
import com.example.finalfinalefinal.flashcardsAnalytics
import com.example.finalfinalefinal.routs
import com.example.studify_app.screens.auth.ForgotPasswordScreen
import com.example.studify_app.screens.auth.LoginScreen
import com.example.studify_app.screens.auth.RegisterScreen
import com.example.studify_app.screens.home.HomeScreen
import com.example.studify_app.screens.onboarding.WelcomeScreen
import com.example.studify_app.screens.onboarding.introFlashcards
import com.example.studify_app.screens.onboarding.introPomodoro
import com.example.studify_app.screens.onboarding.introTasks

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
            startDestination = "home", // تقدر تغيرها لو عايز تبدأ بالهوم
            modifier = Modifier.padding(innerPadding)
        ) {

            // الصفحات الرئيسية
            composable("home") { HomeScreen(navController)}
            composable("subjects") {  com.example.composetest.ui.theme.SetSub() }
            composable("tasks") {  com.example.composetest.ui.theme.SetSub() }
            composable("calendar") { /* TODO: CalendarScreen(navController) */ }
            composable("pomodoro") { PomodoroScreen(navController) }
            composable("profile") { /* TODO: ProfileScreen(navController) */ }
            composable("progress") { ProgressScreen(navController) }

            // شاشات فرعية (من غير ناف بار)
            composable("Counter") { CounterScreen(navController) }
            composable("sessionComplete") { CompleteSessionScreen(navController) }
            composable("addSession") { AddSessionScreen(navController) }
            composable("welcomeScreen") {
                WelcomeScreen(onGetStartedClick = { navController.navigate("introTasks") })
            }
            composable("introTasks") {
                introTasks(
                    onNextClick = { navController.navigate("introPomodoro") },
                    onSkipClick = { navController.navigate("introFlashcards") }
                )
            }
            composable("introPomodoro") {
                introPomodoro(
                    onNextClick = { navController.navigate("introFlashcards") },
                    onSkipClick = { navController.navigate("introFlashcards") }
                )
            }
            composable("introFlashcards") {
                introFlashcards(
                    onGetStartedClick = { navController.navigate("login") }
                )
            }
            composable("login") {
                LoginScreen(
                    onLoginClick = { email, password -> navController.navigate("home") },
                    onRegisterClick = { navController.navigate("register") },
                    onForgotPasswordClick = { navController.navigate("forgotPassword") }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegisterClick = { name, email, password ->

                        navController.navigate("login")
                    },
                    onLoginClick = { navController.navigate("login") }
                )
            }
            composable("forgotPassword") {
                ForgotPasswordScreen(
                    onBackClick = { navController.navigateUp() },
                    onResetPasswordClick = { email ->

                        navController.navigateUp()
                    }
                )
            }
            composable(routs.deckList) {
                decks_list(navController)
            }
            composable(route=routs.deckDetails) {
                deck_details_screen( navController)
            }
            composable(routs.flashCardTesting) {
                flashCard_testing_screen(navController)
            }
            composable(routs.flashcardsAnalytics) {
                flashcardsAnalytics(navController)
            }
            composable(routs.addFlashCard) {
                AddFlashCard(navController)
            }
            composable(routs.statisticsScreen) {
                StatisticsScreen(navController)
            }
        }
    }
}

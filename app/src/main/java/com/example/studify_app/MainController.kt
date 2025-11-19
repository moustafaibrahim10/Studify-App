package com.example.studify_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.composetest.ui.theme.SubjectTaskUi
import com.example.composetest.ui.theme.TaskDetailsScreen
import com.example.composetest.ui.TasksScreen
import com.example.composetest.ui.TaskUi
import com.example.composetest.ui.theme.DeckUi
import com.example.composetest.ui.theme.SubjectDetailsScreen
import com.example.composetest.ui.theme.SubjectUi
import com.example.composetest.ui.theme.SubjectsScreen

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

        val subjects = listOf(
            SubjectUi("Mathematics", 60, 5, 20),
            SubjectUi("Physics", 45, 3, 15),
            SubjectUi("Chemistry", 75, 7, 25),
            SubjectUi("Biology", 55, 4, 18)
        )

        val decks = listOf(
            DeckUi("Chapter 3", 15),
            DeckUi("Chapter 4", 20),
            DeckUi("Chapter 5", 18)
        )

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                // Tasks state قائمة متغيرة قابلة للتحديث مباشرة
                val tasks = remember {
                    mutableStateListOf(
                        TaskUi("Math", "Cell Structure", "Due: Apr 20"),
                        TaskUi("Science", "Photosynthesis", "Due: Apr 22"),
                        TaskUi("Biology", "Genetics", "Due: Apr 25")
                    )
                }

                // SubTasks ما زالت موجودة للـ SubjectDetailsScreen
                val tasksSubjects = remember {
                    mutableStateListOf(
                        SubjectTaskUi("Math", "Cell Structure", false),
                        SubjectTaskUi("Science", "Photosynthesis", false),
                        SubjectTaskUi("Biology", "Genetics", false)
                    )
                }

                MainScaffold(
                    navController = navController,
                    subjects = subjects,
                    tasks = tasks,
                    decks = decks,
                    subTasks = tasksSubjects
                )
            }
        }
    }
}

@Composable
fun MainScaffold(
    navController: NavHostController,
    subjects: List<SubjectUi>,
    tasks: MutableList<TaskUi>,
    decks: List<DeckUi>,
    subTasks: MutableList<SubjectTaskUi>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = when (currentRoute) {
        "home", "subjects", "calendar", "pomodoro", "profile" -> true
        else -> false
    }

    Scaffold(
        bottomBar = { if (showBottomBar) BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            // Onboarding screens
            composable("welcomeScreen") { WelcomeScreen(onGetStartedClick = { navController.navigate("introTasks") }) }
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
            composable("introFlashcards") { introFlashcards(onGetStartedClick = { navController.navigate("login") }) }

            // Auth screens
            composable("login") {
                LoginScreen(
                    onLoginClick = { _, _ -> navController.navigate("home") },
                    onRegisterClick = { navController.navigate("register") },
                    onForgotPasswordClick = { navController.navigate("forgotPassword") }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegisterClick = { _, _, _ -> navController.navigate("login") },
                    onLoginClick = { navController.navigate("login") }
                )
            }
            composable("forgotPassword") {
                ForgotPasswordScreen(
                    onBackClick = { navController.navigateUp() },
                    onResetPasswordClick = { _ -> navController.navigateUp() }
                )
            }

            // Main screens
            composable("home") { HomeScreen(navController) }

            composable("tasks") {
                TasksScreen(
                    navController = navController,
                    tasks = tasks,
                    onAddTask = { /* handled inside TasksScreen */ }
                )
            }

            composable("subjects") {
                SubjectsScreen(
                    subjects = subjects,
                    onSubjectClick = { s -> navController.navigate("subject/${s.name}") }
                )
            }

            composable(
                route = "subject/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val progress = subjects.find { it.name == name }?.progress ?: 0

                SubjectDetailsScreen(
                    navController,
                    subjectName = name,
                    initialProgress = progress,
                    initialTasks = subTasks,
                    initialDecks = decks,
                    onBack = { navController.popBackStack() },
                    onStartPomodoro = { navController.navigate("pomodoro") },
                )
            }

            composable(
                "taskDetail/{title}/{subject}/{due}",
                arguments = listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("subject") { type = NavType.StringType },
                    navArgument("due") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val subject = backStackEntry.arguments?.getString("subject") ?: ""
                val due = backStackEntry.arguments?.getString("due") ?: ""

                TaskDetailsScreen(
                    title = title,
                    subject = subject,
                    due = due,
                    onBackToTasks = { navController.popBackStack() },
                    onMarkComplete = {
                        // حذف المهمة من قائمة Tasks الأصلية
                        tasks.removeAll { it.title == title && it.due == due }
                        navController.popBackStack()
                    }
                )
            }

            // Decks & Flashcards
            composable(routs.deckList) { decks_list(navController) }
            composable(route = routs.deckDetails) { deck_details_screen(navController) }
            composable(routs.flashCardTesting) { flashCard_testing_screen(navController) }
            composable(routs.flashcardsAnalytics) { flashcardsAnalytics(navController) }
            composable(routs.statisticsScreen) { StatisticsScreen(navController) }

            // Other screens
            composable("calendar") { /* TODO */ }
            composable("pomodoro") { PomodoroScreen(navController) }
            composable("profile") { ProfilePic(navController) }
            composable("progress") { ProgressScreen(navController) }
            composable("settings") { SettingsScr(navController) }
            composable("edit") { EditScr(navController) }
            composable("Counter") { CounterScreen(navController) }

        }
    }
}

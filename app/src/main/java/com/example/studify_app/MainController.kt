package com.example.studify_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
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
            startDestination = "welcomeScreen",
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
                )
            }

            composable("subjects") {
                SubjectsScreen(
                    navController = navController,
                    onSubjectClick = { s ->
                        navController.navigate("subject/${s.name}")
                    }
                )
            }

            composable(
                route = "subject/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                SubjectDetailsScreen(
                    navController = navController,
                    subjectName = name,
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

                )
            }

            // Decks & Flashcards
            composable("deckList") { decks_list(navController) }
            composable(
                route = "deckDetails/{deckName}",
                arguments = listOf(navArgument("deckName") { type = NavType.StringType })
            ) { backStackEntry ->
                val deckName = backStackEntry.arguments?.getString("deckName") ?: ""
                deck_details_screen(navController, deckName)
            }
            composable(
                route = "deckTesting/{deckName}",
                arguments = listOf(navArgument("deckName") { type = NavType.StringType })
            ) { backStackEntry ->
                val deckName = backStackEntry.arguments?.getString("deckName") ?: ""
                flashCard_testing_screen(navController, deckName)
            }
            composable("flashcardsAnalytics") { flashcardsAnalytics(navController) }
            composable("statisticsScreen") { StatisticsScreen(navController) }


            // Other screens
            composable("calendar") { DayDetailsScreenContent(navController) }
            composable("pomodoro") { PomodoroScreen(navController) }
            composable("profile") { ProfilePic(navController) }
            composable("progress") { ProgressScreen(navController) }
            composable("settings") { SettingsScr(navController) }
            composable("edit") { EditScr(navController) }
            composable(
                "Counter/{totalSeconds}",
                arguments = listOf(navArgument("totalSeconds") { type = NavType.IntType })
            ) { backStackEntry ->
                val totalSeconds = backStackEntry.arguments?.getInt("totalSeconds") ?: 1500
                CounterScreen(navController, totalSeconds)
            }


        }
    }
}

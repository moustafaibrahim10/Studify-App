//package com.example.composetest
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material3.MaterialTheme
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//import com.example.composetest.ui.theme.SubjectDetailsScreen
//import com.example.composetest.ui.TaskUi
//import com.example.composetest.ui.theme.DeckUi
//import com.example.composetest.ui.theme.SubjectsScreen
//import com.example.composetest.ui.theme.SubjectUi
//import com.example.finalfinalefinal.decks_list
//import com.example.finalfinalefinal.navigation
//import com.example.finalfinalefinal.routs
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val subjects = listOf(
//            SubjectUi("Mathematics", 60, 5, 20),
//            SubjectUi("Physics", 45, 3, 15),
//            SubjectUi("Chemistry", 75, 7, 25),
//            SubjectUi("Biology", 55, 4, 18),
//        )
//
//        setContent {
//            MaterialTheme {
//                val nav = rememberNavController()
//                NavHost(navController = nav, startDestination = "subjects") {
//
//                    composable("subjects") {
//                        SubjectsScreen(
//                            subjects = subjects,
//                            onSubjectClick = { s ->
//                                nav.navigate("subject/${s.name}")
//                            }
//                        )
//                    }
//
//                    composable(
//                        route = "subject/{name}",
//                        arguments = listOf(navArgument("name") { type = NavType.StringType })
//                    ) { backStackEntry ->
//                        val name = backStackEntry.arguments?.getString("name") ?: ""
//                        val tasks = listOf(
//                            TaskUi("Cell Structure", "Due: Apr 20"),
//                            TaskUi("Photosynthesis", "Due: Apr 22"),
//                            TaskUi("Genetics", "Due: Apr 25"),
//                        )
//                        val decks = listOf(
//                            DeckUi("Chapter 3", 15),
//                            DeckUi("Chapter 4", 20),
//                            DeckUi("Chapter 5", 18),
//                        )
//                        val progress = subjects.find { it.name == name }?.progress ?: 0
//
//                        SubjectDetailsScreen(
//                            subjectName = name,
//                            initialProgress = progress,
//                            initialTasks = tasks,
//                            initialDecks = decks,
//                            onBack = { nav.popBackStack() },
//                            onOpenDeck = { /* nav to deck if needed */ },
//                            onStartPomodoro = { /* TODO: open pomodoro */ },
//                            onflashcardsclick = ({ nav.navigate(routs.deckList) })
//                        )
//                    }
//                    composable(routs.deckList) {
//                        decks_list(navController = nav)
//                    }
//                }
//            }
//            navigation()
//        }
//    }
//}

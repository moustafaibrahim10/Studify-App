package com.example.studify_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.studify_app.screens.auth.ForgotPasswordScreen
import com.example.studify_app.screens.auth.LoginScreen
import com.example.studify_app.screens.auth.RegisterScreen
import com.example.studify_app.screens.home.HomeScreen
import com.example.studify_app.screens.onboarding.WelcomeScreen
import com.example.studify_app.screens.onboarding.introTasks
import com.example.studify_app.screens.onboarding.introPomodoro
import com.example.studify_app.screens.onboarding.introFlashcards


@Composable
fun AppNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
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

        composable("home") {
            HomeScreen(navController)
        }

    }
}
package com.example.studify_app.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify_app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: (email: String, password: String) -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    // States
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Colors
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF67C090), Color(0xFFE6F4EA))
    )
    val primaryGreen = Color(0xFF4CAF50)
    val darkText = Color(0xFF1E1E1E)
    val errorRed = Color(0xFFD32F2F)
    val subtitleColor = Color(0xFF666666)

    // Validation function
    fun validateInput(): Boolean {
        return when {
            email.isBlank() -> {
                errorMessage = "Please enter your email"
                false
            }
            !isValidEmail(email) -> {
                errorMessage = "Please enter a valid email address"
                false
            }
            password.isBlank() -> {
                errorMessage = "Please enter your password"
                false
            }
            password.length < 6 -> {
                errorMessage = "Password must be at least 6 characters"
                false
            }
            else -> {
                errorMessage = null
                true
            }
        }
    }

    // Handle login
    fun performLogin() {
        if (validateInput()) {
            isLoading = true
            onLoginClick(email, password)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Studify",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = darkText
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Welcome Title
            Text(
                text = "Welcome Back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to continue your journey",
                fontSize = 14.sp,
                color = subtitleColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Error Message
            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = errorRed,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (errorMessage != null) errorMessage = null
                },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (errorMessage != null) errorMessage = null
                },
                label = { Text("Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = subtitleColor
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot Password
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    "Forgot Password?",
                    color = darkText,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = { performLogin() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryGreen,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Log In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Register Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Don\'t have an account?",
                    color = subtitleColor,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onRegisterClick,
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        "Create an account",
                        color = primaryGreen,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// Extension function for email validation
private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return emailRegex.matches(email)
}
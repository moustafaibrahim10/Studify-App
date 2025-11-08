package com.example.studify_app.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify_app.R


private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return emailRegex.matches(email)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onResetPasswordClick: (email: String) -> Unit
) {
    // States
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    // Colors
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF67C090), Color(0xFFE6F4EA))
    )
    val primaryGreen = Color(0xFF4CAF50)
    val darkText = Color(0xFF1E1E1E)
    val errorRed = Color(0xFFD32F2F)
    val successGreen = Color(0xFF388E3C)
    val subtitleColor = Color(0xFF666666)

    // Validation function
    fun validateInput(): Boolean {
        return when {
            email.isBlank() -> {
                errorMessage = "Please enter your email"
                false
            }
            !isValidEmail(email) -> { // هنا هتشتغل عادي
                errorMessage = "Please enter a valid email address"
                false
            }
            else -> {
                errorMessage = null
                true
            }
        }
    }

    // Handle reset password
    fun performResetPassword() {
        if (validateInput()) {
            isLoading = true
            // Simulate API call
            successMessage = "Reset link sent to your email"
            isLoading = false
            onResetPasswordClick(email)
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
            // Header with Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = darkText
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Back to Login",
                    color = darkText,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Title
            Text(
                text = "Forgot Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter your email to receive a reset link",
                fontSize = 16.sp,
                color = subtitleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Success Message
            successMessage?.let { message ->
                Text(
                    text = message,
                    color = successGreen,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            // Error Message
            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = errorRed,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (errorMessage != null) errorMessage = null
                    if (successMessage != null) successMessage = null
                },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Reset Password Button
            Button(
                onClick = { performResetPassword() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryGreen,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading && email.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Send Reset Link",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Info Text
            Text(
                text = "You'll receive an email with instructions to reset your password. Please check your spam folder if you don't see it.",
                color = subtitleColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
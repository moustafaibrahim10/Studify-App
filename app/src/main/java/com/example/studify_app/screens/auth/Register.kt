package com.example.studify_app.screens.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DataRepository
import com.example.studify_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterClick: (name: String, email: String, password: String) -> Unit,
    onLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF67C090), Color(0xFFE6F4EA))
    )
    val primaryGreen = Color(0xFF4CAF50)
    val darkText = Color(0xFF1E1E1E)
    val errorRed = Color(0xFFD32F2F)
    val subtitleColor = Color(0xFF666666)

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateInput(): Boolean {
        return when {
            name.isBlank() -> {
                errorMessage = "Please enter your full name"
                false
            }
            name.length < 2 -> {
                errorMessage = "Name must be at least 2 characters"
                false
            }
            email.isBlank() -> {
                errorMessage = "Please enter your email"
                false
            }
            !isValidEmail(email) -> {
                errorMessage = "Please enter a valid email address"
                false
            }
            existingEmail(email) -> {
                errorMessage = "Email already exists"
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
            confirmPassword.isBlank() -> {
                errorMessage = "Please confirm your password"
                false
            }
            password != confirmPassword -> {
                errorMessage = "Passwords do not match"
                false
            }
            else -> {
                errorMessage = null
                true
            }
        }
    }

    // Handle registration
    fun performRegistration() {
        if (validateInput()) {
            isLoading = true
            onRegisterClick(name, email, password)
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
                .verticalScroll(rememberScrollState())
                .imePadding()
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Studify",
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                fontSize = 28.sp,
                color = darkText
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Create Your Account",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                color = darkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Join us and organize your study life",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.lexend)),
                color = subtitleColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = errorRed,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_semibold)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (errorMessage != null) errorMessage = null
                },
                label = { Text("Full Name",fontFamily = FontFamily(Font(R.font.lexend))) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (errorMessage != null) errorMessage = null
                },
                label = { Text("Email",fontFamily = FontFamily(Font(R.font.lexend))) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (errorMessage != null) errorMessage = null
                },
                label = { Text("Password",fontFamily = FontFamily(Font(R.font.lexend))) },
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
                shape = RoundedCornerShape(12.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    if (errorMessage != null) errorMessage = null
                },
                label = { Text("Confirm Password",fontFamily = FontFamily(Font(R.font.lexend))) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { confirmPasswordVisible = !confirmPasswordVisible },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                            tint = subtitleColor
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Black
                )
            )

            Text(
                text = "Password must be at least 6 characters",
                color = subtitleColor,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.lexend)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { performRegistration() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryGreen,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading && name.isNotBlank() && email.isNotBlank() &&
                        password.isNotBlank() && confirmPassword.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Sign Up",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_semibold))
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account?",
                    color = subtitleColor,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onLoginClick,
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        "Log in",
                        color = primaryGreen,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.lexend_semibold))
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return emailRegex.matches(email)
}


@RequiresApi(Build.VERSION_CODES.O)
private fun existingEmail(email: String): Boolean {
    val existingemail=DataRepository.users.any{ it.email== email }
    return existingemail
}
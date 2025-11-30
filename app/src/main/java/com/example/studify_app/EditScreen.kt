package com.example.studify_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.DataRepository

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditScr(navController: NavController) {

    val user = DataRepository.currentUser!!

    // Default values from user
    var username by remember { mutableStateOf(user?.username ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .height(56.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Black)
            }

            Text(
                text = "Edit Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.profileowl),
            contentDescription = "Profile Pic",
            modifier = Modifier.size(128.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = user?.username ?: "",
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Username
        LabeledField("Username", username) { username = it }

        // Email
        LabeledField("Email", email) { email = it }

        // New Password
        LabeledField("New Password (optional)", password, isPassword = true) { password = it }

        // Confirm Password
        LabeledField("Confirm Password", confirmPassword, isPassword = true) { confirmPassword = it }

        Spacer(modifier = Modifier.height(10.dp))

        // Error message
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
            )
            Spacer(modifier = Modifier.height(5.dp))
        }

        // Success msg
        successMessage?.let {
            Text(
                text = it,
                color = Color(0xFF4CAF50),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
            )
            Spacer(modifier = Modifier.height(5.dp))
        }

        // Save button
        Button(
            onClick = {

                // Validate email
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessage = "Please enter a valid email."
                    return@Button
                }

                // Validate password match (only if password is written)
                if (password.isNotEmpty() && password != confirmPassword) {
                    errorMessage = "Passwords do not match."
                    return@Button
                }

                errorMessage = null

                // Update user info
                user.let {
                    it.username = username
                    it.email = email
                    if (password.isNotEmpty()) {
                        it.password = password
                    }
                }

                successMessage = "Profile updated successfully!"

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF66BB6A),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Save Changes",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold))
            )
        }
    }
}

@Composable
fun LabeledField(
    label: String,
    value: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Text(
        text = label,
        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
        fontSize = 16.sp,
        modifier = Modifier.padding(horizontal = 24.dp)
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedContainerColor = Color(0xFFE8F2F0),
            unfocusedContainerColor = Color(0xFFE8F2F0),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}



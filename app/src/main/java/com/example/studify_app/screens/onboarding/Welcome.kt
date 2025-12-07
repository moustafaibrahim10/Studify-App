package com.example.studify_app.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studify_app.R



@Composable
fun WelcomeScreen(
    onGetStartedClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF67C090), // light mint green top
            Color(0xFFE6F4EA)  // soft white bottom
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.owl_onboarding),
                contentDescription = "Owl Mascot",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(340.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Studify",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                color = Color(0xFF1E1E1E),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Organize your study life effortlessly.",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.lexend)),
                color = Color(0xFF4A4A4A),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onGetStartedClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50), // Green button
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.lexend_semibold))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
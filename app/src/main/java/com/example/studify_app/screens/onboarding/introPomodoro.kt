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
fun introPomodoro(
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF67C090), Color(0xFFE6F4EA))
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
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.owl_pomodoro),
                contentDescription = "Pomodoro",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(340.dp)
            )

            Text(
                text = "Pomodoro",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.lexend_bold)),
                color = Color(0xFF1E1E1E)
            )

            Text(
                text = "Stay focused using built-in study sessions.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.lexend)),
                color = Color(0xFF4A4A4A),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Dot()
                Dot(active = true)
                Dot()
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onNextClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Next", color = Color.White, fontSize = 16.sp,fontFamily = FontFamily(Font(R.font.lexend_semibold)))
                }

                TextButton(onClick = onSkipClick) {
                    Text("Skip", color = Color(0xFF1E1E1E),fontFamily = FontFamily(Font(R.font.lexend_semibold)))
                }
            }
        }
    }
}
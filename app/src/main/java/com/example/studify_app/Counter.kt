package com.example.studify_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.NotificationHelper
import com.example.SoundHelper
import com.example.data.DataRepository
import kotlinx.coroutines.delay
@androidx.annotation.RequiresPermission(android.Manifest.permission.POST_NOTIFICATIONS)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CounterScreen(navController: NavController, totalTime: Int) {

    val context = LocalContext.current
    val user = DataRepository.currentUser!!

    var timeLeft by remember { mutableStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning, timeLeft) {
        if (isRunning && timeLeft > 0) {
            while (isRunning && timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
        }
        if (timeLeft == 0) {

//            if (user.notificationsEnabled) {
//                NotificationHelper.showSessionCompleteNotification(context)
//            }

            if (user.soundEnabled) {
                SoundHelper.playEndSound(context)
            }

            user.totalsessions+=0.5f

            navController.navigate("sessionComplete") {
                popUpTo("Pomodoro") { inclusive = false }
            }
        }

    }

    val progress = timeLeft / totalTime.toFloat()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(26.dp))

            Text(
                text = "Studify",
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                    textAlign = TextAlign.Center
                )
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .size(26.dp)
                    .clickable {
                        isRunning = false
                        navController.navigate("Pomodoro") {
                            popUpTo("Counter") { inclusive = true }
                        }
                    },
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(240.dp)) {
                    drawArc(
                        color = Color(0xFFE6E6E6),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 35f, cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = Color(0xFF67C090),
                        startAngle = -90f,
                        sweepAngle = 360 * progress,
                        useCenter = false,
                        style = Stroke(width = 40f, cap = StrokeCap.Round)
                    )
                }

                Text(
                    text = String.format("%02d:%02d:%02d", timeLeft / 3600, (timeLeft % 3600) / 60, timeLeft % 60),
                    style = TextStyle(
                        color = Color(0xFF111111),
                        fontSize = 38.sp,
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier
                        .width(220.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090))
                ) {
                    Text(
                        if (isRunning) "Pause" else "Resume",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                    )
                }

                Button(
                    onClick = {
                        isRunning = false
                        navController.navigate("Pomodoro") {
                            popUpTo("Counter") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .width(220.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEAF3F2))
                ) {
                    Text("Stop", color = Color.Black,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                    )
                }
            }
        }
    }
}

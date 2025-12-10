package com.example.studify_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.data.DataRepository
import com.example.data.DataRepository.currentUser


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePic(navController: NavHostController) {
    val currentUser = DataRepository.currentUser!!
    val tasks = remember { derivedStateOf { currentUser.subjects.flatMap { it.tasks } } }


    val totalTasks = tasks.value.size
    val completedTasks = tasks.value.count { it.isDone }
    val assignmentsProgress = if (totalTasks > 0) completedTasks / totalTasks.toFloat() else 0f


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .height(56.dp)
        ) {
            IconButton(
                onClick = { navController.navigate("edit") },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
            Text(
                text = "Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = { navController.navigate("settings") },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.setting_away),
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.profileowl),
            contentDescription = "Profile Pic",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = currentUser.username,
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
            fontSize = 22.sp,
            color = Color(0XFF000000)
        )
        Text(
            text = currentUser?.email ?: "",
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
            fontSize = 16.sp,
            color = Color(0xFF2F7D66)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Weekly Goals",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.Black,
                modifier = Modifier.padding(top = 35.dp, bottom = 16.dp)
            )

            GoalItem(
                icon = R.drawable.pomodoroic,
                title = "Pomodoro",
                description = "You completed ${currentUser.totalsessions.toInt()} sessions today",
                progress = -1f
            )

            GoalItem(
                icon = R.drawable.assic,
                title = "Tasks",
                description = "You finish ${(assignmentsProgress * 100).toInt()}% of your tasks",
                progress = assignmentsProgress
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GoalItem(icon: Int, title: String, description: String, progress: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF4F8F7), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFE8F2F0), shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = description,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (progress >= 0) {
                LinearProgressIndicator(
                    progress = { progress },
                    color = Color(0xFF4D9987),
                    trackColor = Color(0xFFE0F2EF),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(15.dp))
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
        if (progress >= 0) {
            Text(
                text = "${(progress * 100).toInt()}%",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                color = Color(0xFF4D9987),
                modifier = Modifier.padding(bottom = 10.dp, end = 10.dp)
            )
        }
    }
}


package com.example.studify_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class DayDetailsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DayDetailsScreenContent(navController)
        }
    }
}

@Composable
fun DayDetailsScreenContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Day Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(48.dp)) // للموازنة فقط
        }

        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Tasks",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(17.dp))

        TaskItem("Complete Math Assignment", "Due Today")
        Spacer(modifier = Modifier.height(16.dp))

        TaskItem("Read Chapter 3 of History Textbook", "Due Today")
        Spacer(modifier = Modifier.height(16.dp))

        TaskItem("Review Science Notes", "Due Today")

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Events",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        EventItem("Math Class", "10:00 AM - 11:00 AM")

        EventItem("History Study Group", "1:00 PM - 2:00 PM")
    }
}

@Composable
fun TaskItem(title: String, subtitle: String) {
    var checked by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Gray,
                checkedColor = Color(0xFF67C090),
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, fontSize = 16.sp)
            Text(subtitle, fontSize = 13.sp, color = Color(0xFF67C090))
        }
    }
}

@Composable
fun EventItem(title: String, time: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(title, fontSize = 18.sp)
            Text(time, fontSize = 13.sp, color = Color(0xFF67C090))
        }
    }
}

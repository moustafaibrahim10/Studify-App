package com.example.composetest.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ScreenBg = Color(0xFFF9F9F9)
private val MintBtn  = Color(0xFF67C090)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    title: String,
    subject: String,
    due: String,
    onBackToTasks: () -> Unit,
    onMarkComplete: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackToTasks) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = ScreenBg
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabeledReadOnlyField(label = "Task Name", value = title)
            LabeledReadOnlyField(label = "Subject", value = subject)
            LabeledReadOnlyField(label = "Due Date", value = due)

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = onMarkComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MintBtn),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    "Mark as Complete",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun LabeledReadOnlyField(label: String, value: String) {
    Column {
        Text(label, fontSize = 14.sp)
        Spacer(Modifier.height(6.dp))
        Surface(
            color = Color(0xFFEFF5F3),
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 0.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(Color.Transparent)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(value, color = Color(0xFF728C82))
            }
        }
    }
}

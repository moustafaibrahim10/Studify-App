package com.example.composetest.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DataRepository
import com.example.model.Task
import com.example.studify_app.R

private val ScreenBg = Color(0xFFF9F9F9)
private val MintBtn  = Color(0xFF67C090)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    title: String,
    subject: String,
    due: String,
    onBackToTasks: () -> Unit,
    onMarkComplete: () -> Unit = {}
) {
    val user = DataRepository.currentUser!!
    val subjectObj = user.subjects.find { it.name == subject }
    val task = subjectObj?.tasks?.find { it.title == title }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold))) },
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
                onClick = {
                    if (task != null) {
                        task.isDone = true
                        onMarkComplete()
                    }
                },
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
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                )
            }
        }
    }
}

@Composable
private fun LabeledReadOnlyField(label: String, value: String) {
    Column {
        Text(label, fontSize = 14.sp,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
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
                Text(value, color = Color(0xFF728C82),fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
            }
        }
    }
}

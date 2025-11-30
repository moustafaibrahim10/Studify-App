package com.example.studify_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.DataRepository
import com.example.model.Task
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Same colors from TasksScreen
private val ScreenBg = Color(0xFFF9F9F9)
private val Accent = Color(0xFF2F7D66)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailsScreen(
    navController: NavController,
    initialDate: LocalDate = LocalDate.now()
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    val currentUser = DataRepository.currentUser!!

    // Filter tasks for the selected date
    val tasksForDay = remember(selectedDate, currentUser.tasksHash()) {
        currentUser.subjects.flatMap { it.tasks }.filter { it.due == selectedDate }
    }

    // Progress
    val total = tasksForDay.size
    val done = tasksForDay.count { it.isDone }
    val progress = if (total > 0) done / total.toFloat() else 0f

    // Date picker
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Column(
                        modifier = Modifier.clickable { showDatePicker = true }.padding(10.dp)
                    ) {
                        Text(
                            selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd")),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            )
        },
        containerColor = ScreenBg
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            // ---------- PROGRESS CARD ----------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        "$done out of $total completed",
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = progress,
                        color = Accent,
                        trackColor = Color(0xFFE8EEF0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------- TASK LIST ----------
            if (tasksForDay.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No tasks on this date.",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(tasksForDay) { task ->

                        TaskRowCard(task = task)
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskRowCard(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(Modifier.weight(1f)) {

                Text(
                    task.subject,
                    color = Accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    task.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    task.due.toString(),
                    color = Color(0xFF4CAF50),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Checkbox(
                checked = task.isDone,
                onCheckedChange = { checked ->
                    task.isDone = checked
                    task.completed = checked
                }
            )
        }
    }
}


/**
 * A trick: this forces recomposition when tasks update.
 * Useful because mutableState inside Task doesn't update lists automatically.
 */
fun Any.tasksHash(): Int = this.hashCode()

package com.example.composetest.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DateRange
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
import androidx.navigation.NavHostController
import com.example.data.DataRepository
import com.example.model.Subject
import com.example.model.Task
import java.net.URLEncoder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// --- Data Class ---
data class TaskUi(
    val subject: String,
    val title: String,
    val due: String
)

// --- Colors ---
private val ScreenBg = Color(0xFFF9F9F9)
private val Accent = Color(0xFF2F7D66)
private val Mint = Color(0xFFE5F4EF)
private val MintButton = Color(0xFF67C090)

// --- Main Screen ---
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    navController: NavHostController
) {
    val tasks = DataRepository.tasks // Already a mutableStateListOf from repository
    var showAddSheet by remember { mutableStateOf(false) }

    // --- Progress Calculation ---
    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.isDone }
    var progress = if (totalTasks > 0) completedTasks / totalTasks.toFloat() else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Tasks", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.Close, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showAddSheet = true }) {
                        Icon(Icons.Outlined.Add, contentDescription = "Add Task")
                    }
                }
            )
        },
        containerColor = ScreenBg
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(ScreenBg)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            // --- Progress Bar Block ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("$completedTasks out of $totalTasks completed", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
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

            Spacer(Modifier.height(12.dp))

            // --- Task List ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                            .clickable {
                                val titleEncoded = URLEncoder.encode(task.title, "UTF-8")
                                val subjectEncoded = URLEncoder.encode(task.subject, "UTF-8")
                                val dueEncoded = URLEncoder.encode(task.due.toString(), "UTF-8")
                                navController.navigate("taskDetail/$titleEncoded/$subjectEncoded/$dueEncoded")
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = task.subject,
                                color = Accent,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = task.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Text(
                                text = task.due.toString(),
                                color = Color(0xFF4CAF50),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        // --- Checkbox for Completion ---
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { checked ->
                                task.isDone = checked

                                val doneCount = tasks.count { it.isDone } ?: 0
                                val total = tasks.size ?: 0
                                progress = (if (total > 0) (doneCount * 100 / total) else 0).toFloat()


                            }
                        )
                    }
                }
            }
        }
    }

    // --- Bottom Sheet for Adding Tasks ---
    if (showAddSheet) {
        AddTaskSheet(
            onDismiss = { showAddSheet = false },
            onConfirm = { title, subject, deadline ->
                if (title.isNotBlank() && subject.isNotBlank()) {
                    val newTask = Task(
                        subject = subject.trim(),
                        title = title.trim(),
                        due = LocalDate.parse(deadline.trim(), DateTimeFormatter.ofPattern("MMM d, yyyy"))
                    )

                    DataRepository.addTask(newTask)

                }
                showAddSheet = false
            }
        )
    }
}

// --- Add Task Sheet ---
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskSheet(
    onDismiss: () -> Unit,
    onConfirm: (title: String, subject: String, deadline: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close")
                }
                Text("Add Task", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.size(40.dp))
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = deadline,
                    onValueChange = {},
                    label = { Text("Deadline") },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "Select Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }

            Button(
                onClick = { onConfirm(title, subject, deadline) },
                enabled = title.isNotBlank() && subject.isNotBlank() && deadline.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MintButton),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add Task", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(12.dp))
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            deadline = convertMillisToDate(selectedMillis)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun convertMillisToDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}

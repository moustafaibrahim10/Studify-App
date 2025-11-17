package com.example.composetest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TaskUi(
    val subject: String,
    val title: String,
    val due: String
)

private val ScreenBg = Color(0xFFF9F9F9)
private val Accent = Color(0xFF2F7D66)
private val Mint = Color(0xFFE5F4EF)
private val MintButton = Color(0xFF67C090)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {

    val items = remember {
        mutableStateListOf(
            TaskUi("Math", "Complete Calculus Assignment", "Due: Tomorrow"),
            TaskUi("History", "Read Chapter 5", "Due: Friday"),
            TaskUi("Science", "Lab Report on Photosynthesis", "Due: Next Week")
        )
    }

    var showAddSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks", fontWeight = FontWeight.Bold) },
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                AppFilterChip("All", true)
                AppFilterChip("Today", false)
                AppFilterChip("By Subject", false)
            }

            Spacer(Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(items) { task ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                            .clickable { /* something */ }
                    ) {
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
                            text = task.due,
                            color = Color(0xFF4CAF50),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        AddTaskSheet(
            onDismiss = { showAddSheet = false },
            onConfirm = { title, subject, deadline ->
                if (title.isNotBlank() && subject.isNotBlank()) {
                    items.add(
                        TaskUi(
                            subject = subject.trim(),
                            title = title.trim(),
                            due = deadline.trim()
                        )
                    )
                }
                showAddSheet = false
            }
        )
    }
}

@Composable
private fun AppFilterChip(text: String, selected: Boolean) {
    Surface(
        color = if (selected) Mint else Color.White,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 13.sp,
                color = if (selected) Accent else Color.Gray
            )
        }
    }
}

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
                Text(
                    "Add Task",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
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
            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Deadline") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = { onConfirm(title, subject, deadline) },
                enabled = title.isNotBlank() && subject.isNotBlank(),
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
}

@Preview(showBackground = true, name = "Tasks Screen")
@Composable
fun TasksScreenPreview() {
    MaterialTheme {
        TasksScreen()
    }
}

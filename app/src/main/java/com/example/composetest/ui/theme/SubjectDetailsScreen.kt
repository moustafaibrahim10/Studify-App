package com.example.composetest.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable


private val CardTint = Color(0xFFD5E6DF)
private val ScreenBg = Color(0xFFF9F9F9)
private val Accent   = Color(0xFF2F7D66)

data class TaskUi(
    val title: String,
    val due: String,
    val done: Boolean = false
)

data class DeckUi(
    val title: String,
    val cardsCount: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailsScreen(
    subjectName: String,
    initialProgress: Int = 0,
    initialTasks: List<TaskUi> = emptyList(),
    initialDecks: List<DeckUi> = emptyList(),
    onBack: () -> Unit,
    onOpenDeck: (DeckUi) -> Unit = {},
    onStartPomodoro: () -> Unit = {},
    onTaskClick: (TaskUi) -> Unit = {}
) {
    var progress by remember { mutableStateOf(initialProgress.coerceIn(0, 100)) }
    val tasks = remember { mutableStateListOf<TaskUi>().also { it.addAll(initialTasks) } }
    val decks = remember { mutableStateListOf<DeckUi>().also { it.addAll(initialDecks) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(subjectName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = ScreenBg
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            // Progress
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text("Progress: $progress%", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progress / 100f },
                        color = Accent,
                        trackColor = Color(0xFFE8EEF0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                }
            }

            // Tasks header + Add
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        onClick = {
                            // إضافة عنصر تجريبي سريع
                            tasks.add(TaskUi("New task", "Due: Apr 30"))
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) { Text("Add Task") }
                }
            }

            // Tasks list
            items(tasks, key = { it.title + it.due }) { t ->
                TaskRow(
                    task = t,
                    onCheckedChange = { checked ->
                        val idx = tasks.indexOf(t)
                        if (idx >= 0) tasks[idx] = t.copy(done = checked)
                        // حدّثي التقدم بشكل بسيط (اختياري)
                        val doneCount = tasks.count { it.done }
                        val ratio = if (tasks.isNotEmpty()) doneCount * 100 / tasks.size else initialProgress
                        progress = ratio
                    },
                    onClick = { onTaskClick(t) }
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Flashcards Decks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        onClick = {
                            decks.add(DeckUi("New deck", 10))
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) { Text("Add deck") }
                }
            }

            items(decks, key = { it.title }) { d ->
                DeckRow(deck = d, onClick = { onOpenDeck(d) })
            }

            item {
                Button(
                    onClick = onStartPomodoro,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardTint),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Start Pomodoro", color = Color.Black, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: TaskUi,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit = {}
) {
    Surface(
        color = Color(0xFFF7FBFA),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(task.title, fontWeight = FontWeight.SemiBold)
                Text(task.due, fontSize = 12.sp, color = Color(0xFF3AA77E))
            }
            Checkbox(
                checked = task.done,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun DeckRow(deck: DeckUi, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${deck.title} - ${deck.cardsCount} cards", fontWeight = FontWeight.Medium)
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1F5F4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.ChevronRight, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Subject Details – Preview")
@Composable
fun SubjectDetailsScreenPreview() {
    val tasks = listOf(
        TaskUi("Cell Structure", "Due: Apr 20"),
        TaskUi("Photosynthesis", "Due: Apr 22"),
        TaskUi("Genetics", "Due: Apr 25"),
    )
    val decks = listOf(
        DeckUi("Chapter 3", 15),
        DeckUi("Chapter 4", 20),
        DeckUi("Chapter 5", 18),
    )

    MaterialTheme {
        SubjectDetailsScreen(
            subjectName = "Biology",
            initialProgress = 70,
            initialTasks = tasks,
            initialDecks = decks,
            onBack = {}
        )
    }
}


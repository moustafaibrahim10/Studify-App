// SubjectDetailsScreen.kt
package com.example.composetest.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.NavHostController
import com.example.data.DataRepository
import com.example.finalfinalefinal.routs
import com.example.model.Deck
import com.example.model.Task
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val CardTint = Color(0xFFD5E6DF)
private val ScreenBg = Color(0xFFF9F9F9)
private val Accent = Color(0xFF2F7D66)

private val MintButton = Color(0xFF67C090)


data class SubjectTaskUi(val title: String, val due: String, val done: Boolean = false)
data class DeckUi(val title: String, val cardsCount: Int)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailsScreen(
    navController: NavHostController,
    subjectName: String,
    onBack: () -> Unit,
    onStartPomodoro: () -> Unit = {}
) {
    var subject by remember { mutableStateOf(DataRepository.getSubjectByName(subjectName)) }

    var showAddTaskSheet by remember { mutableStateOf(false) }
    var showAddDeckSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(subjectName, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back") } }
            )
        },
        containerColor = ScreenBg
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Progress
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White).padding(16.dp)
                ) {
                    Text("Progress: ${subject?.currentprogress}%", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = ((subject?.currentprogress ?: 0) / 100f),
                        color = Accent,
                        trackColor = Color(0xFFE8EEF0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                }
            }

            // Tasks Header + Add
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tasks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        onClick = { showAddTaskSheet = true },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add Task")
                    }
                }
            }

            // Tasks List
            items(subject?.tasks ?: emptyList(), key = { it.title + it.due.toString() }) { task ->
                TaskRow(
                    task = task,
                    onCheckedChange = { checked ->
                        task.isDone = checked

                        val doneCount = subject?.tasks?.count { it.isDone } ?: 0
                        val total = subject?.tasks?.size ?: 0
                        subject?.currentprogress = if (total > 0) (doneCount * 100 / total) else 0
                    },
                    onClick = {
                        navController.navigate(
                            "taskDetail/${task.title}/${subject?.name ?: ""}/${task.due}"
                        )
                    }
                )
            }

            // Decks Header + Add
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp).clickable { navController.navigate(routs.deckList) },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Flashcards Decks", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        onClick = { showAddDeckSheet = true },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add Deck")
                    }
                }
            }

            items(subject?.decks ?: emptyList(), key = { it.title }) { deck ->
                DeckRow(deck = deck, onClick = {  navController.navigate("deckDetails/${deck.title}")})
            }
            item {
                Button(
                    onClick = onStartPomodoro,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardTint),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Start Pomodoro", color = Color.Black, fontWeight = FontWeight.SemiBold) }
            }
        }
    }

    if (showAddTaskSheet) {
        AddTaskSheet(
            onDismiss = { showAddTaskSheet = false },
            onConfirm = { title, due ->
                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
                val date = LocalDate.parse(due, formatter)
                    DataRepository.addSubjectTask(subject, Task(title, date))
                DataRepository.addTask(Task(title, date))
                    showAddTaskSheet = false


                val doneCount = subject?.tasks?.count { it.isDone } ?: 0
                val total = subject?.tasks?.size ?: 0
                subject?.currentprogress = if (total > 0) (doneCount * 100 / total) else 0
            }
        )
    }

    if (showAddDeckSheet) {
        AddDeckSheet(
            onDismiss = { showAddDeckSheet = false },
            onConfirm = { name ->
                DataRepository.addSubjectDeck(subject, Deck(name))
                DataRepository.addDeck(Deck(name))
                showAddDeckSheet = false
            }
        )
    }

}

@Composable
private fun TaskRow(task: Task, onCheckedChange: (Boolean) -> Unit, onClick: () -> Unit = {}) {
    Surface(color = Color(0xFFF7FBFA), shape = RoundedCornerShape(12.dp), tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(task.title, fontWeight = FontWeight.SemiBold)
                Text(task.due.toString(), fontSize = 12.sp, color = Color(0xFF3AA77E))
            }
            Checkbox(
                checked = task.isDone,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun DeckRow(deck: Deck, onClick: () -> Unit) {
    Surface(color = Color.White, shape = RoundedCornerShape(12.dp), tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("${deck.title} - ${deck.cards.size} cards", fontWeight = FontWeight.Medium)
            Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFFF1F5F4)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Outlined.ChevronRight, contentDescription = null) }
        }
    }
}

// --- Add Task Sheet (No Subject Field) ---
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskSheet(
    onDismiss: () -> Unit,
    onConfirm: (title: String, deadline: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    // Date Picker State
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
            // Header
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

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Deadline Field
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

                // Transparent clickable layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }

            Spacer(Modifier.height(4.dp))

            // Add Button
            Button(
                onClick = { onConfirm(title, deadline) },
                enabled = title.isNotBlank() && deadline.isNotBlank(),
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

    // Date Picker Dialog
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
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy") // مثال: Nov 28, 2025
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeckSheet(
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add Deck", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Deck Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onConfirm(name) },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add")
                }
            }
        }
    }
}


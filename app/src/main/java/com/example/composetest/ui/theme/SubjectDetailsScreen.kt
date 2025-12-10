// SubjectDetailsScreen.kt
package com.example.composetest.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.data.DataRepository
import com.example.finalfinalefinal.routs
import com.example.model.Deck
import com.example.model.Task
import com.example.studify_app.R
import com.example.studify_app.mintGreen
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val CardTint = Color(0xFF67C090)
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
    val subject = DataRepository.getSubjectByName(subjectName)
    val doneCount = subject?.tasks?.count { it.isDone } ?: 0
    val total = subject?.tasks?.size ?: 0
    subject?.currentprogress = if (total > 0) (doneCount * 100 / total) else 0
    val sortedTasks = DataRepository.getSubjectByName(subjectName)?.tasks?.sortedBy { it.due } ?: emptyList()


    var showAddTaskSheet by remember { mutableStateOf(false) }
    var showAddDeckSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(subjectName, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold))) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back") } }
            )
        }
        ,containerColor = ScreenBg,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onStartPomodoro,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardTint),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Start Pomodoro",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                    )
                }
            }
        }
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
                    Text("Progress: ${subject?.currentprogress}%", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
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
                    Text("Tasks", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                    OutlinedButton(
                        onClick = { showAddTaskSheet = true },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add Task",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), color = Color.Black)
                    }
                }
            }

            // Tasks List
            items(sortedTasks, key = { it.title + it.due.toString() }) { task ->
                var showDeleteTaskDialog by remember { mutableStateOf(false) }

                if (showDeleteTaskDialog) {
                    DeleteTaskDialog(
                        task = task,
                        onDismiss = { showDeleteTaskDialog = false },
                        onConfirm = {
                            deleteTaskCompletely(subject!!, task)
                            showDeleteTaskDialog = false
                        }
                    )
                }


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
                    },
                    onLongPress = { showDeleteTaskDialog = true }
                )
            }

            // Decks Header + Add
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Flashcards Decks", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                    OutlinedButton(
                        onClick = { showAddDeckSheet = true },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Add Deck",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), color = Color.Black)
                    }
                }
            }

            items(subject?.decks ?: emptyList(), key = { it.title }) { deck ->

                var showDeleteDeckDialog by remember { mutableStateOf(false) }

                if (showDeleteDeckDialog) {
                    DeleteDeckDialog(
                        deck = deck,
                        onDismiss = { showDeleteDeckDialog = false },
                        onConfirm = {
                            deleteDeckCompletely(subject!!, deck)
                            showDeleteDeckDialog = false
                        }
                    )
                }
                DeckRow(deck = deck, onClick = {  navController.navigate("deckDetails/${deck.title}")}, onLongPress = { showDeleteDeckDialog = true })
            }
        }
    }

    if (showAddTaskSheet) {
        AddTaskSheet(
            onDismiss = { showAddTaskSheet = false },
            onConfirm = { title, due ->
                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
                val date = LocalDate.parse(due, formatter)
                DataRepository.addTask(Task(title, subjectName, date))
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
                DataRepository.addDeck(Deck(name, subjectName))
                showAddDeckSheet = false
            }
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskRow(task: Task, onCheckedChange: (Boolean) -> Unit, onClick: () -> Unit = {}, onLongPress: () -> Unit = {}) {
    Surface(color = Color(0xFFF7FBFA), shape = RoundedCornerShape(12.dp), tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            )

    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(task.title, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                Text(task.due.toString(), fontSize = 12.sp, color = Color(0xFF3AA77E),fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
            }
            Checkbox(
                checked = task.isDone,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF67C090),
                    uncheckedColor = Color.Gray,
                    checkmarkColor = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DeckRow(deck: Deck, onClick: () -> Unit, onLongPress: () -> Unit = {}) {
    Surface(color = Color.White, shape = RoundedCornerShape(12.dp), tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongPress)
    ) {
        Row(modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("${deck.title} - ${deck.cards.size} cards", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
            Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFFF1F5F4)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Outlined.ChevronRight, contentDescription = null) }
        }
    }
}

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
                Text("Add Task", style = MaterialTheme.typography.titleMedium,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                Spacer(Modifier.size(40.dp))
            }

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Black
                )

            )

            // Deadline Field
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = deadline,
                    onValueChange = {},
                    label = { Text("Deadline",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "Select Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color.Black
                    )

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
                Text("Add Task", color = Color.White, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
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
                    Text("OK", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
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
            Text("Add Deck", style = MaterialTheme.typography.titleMedium,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), color = Color.Black)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Deck Name",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color.Black
                )
            )

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text("Cancel",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), color = Color.Black)
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onConfirm(name) },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mintGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text("Add",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), color = Color.White)
                }
            }
        }
    }
}



@Composable
fun DeleteTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Task?") },
        text = { Text("This will remove '${task.title}' from this subject and from your total tasks.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color= Accent)
            }
        }
    )
}



@Composable
fun DeleteDeckDialog(
    deck: Deck,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Deck?") },
        text = { Text("This will remove '${deck.title}' and all its flashcards.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color= Accent)
            }
        }
    )
}



@RequiresApi(Build.VERSION_CODES.O)
fun deleteTaskCompletely(subject: com.example.model.Subject, task: Task) {
    val user = DataRepository.currentUser ?: return

    subject.tasks.remove(task)

    user.tasks.remove(task)

    val doneCount = subject.tasks.count { it.isDone }
    val total = subject.tasks.size
    subject.currentprogress = if (total > 0) (doneCount * 100 / total) else 0
}



@RequiresApi(Build.VERSION_CODES.O)
fun deleteDeckCompletely(subject: com.example.model.Subject, deck: Deck) {
    val user = DataRepository.currentUser ?: return

    subject.decks.remove(deck)
    user.decks.remove(deck)
}



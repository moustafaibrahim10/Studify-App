@file:Suppress("UnusedImport")

package com.example.composetest.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SubjectUi(
    val name: String,
    val progress: Int,
    val tasksCount: Int,
    val flashcardsCount: Int
)

private val CardTint = Color(0xFFD5E6DF)
private val ScreenBg = Color(0xFFF9F9F9)
private val Accent   = Color(0xFF2F7D66)
private val Mint     = Color(0xFF67C090)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(
    subjects: List<SubjectUi>,
    onSubjectClick: (SubjectUi) -> Unit = {},
) {
    val items = remember { mutableStateListOf<SubjectUi>().also { it.addAll(subjects) } }
    var showAddSheet by rememberSaveable { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Subjects", fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Outlined.Person, contentDescription = "Profile")
                        }
                    }
                )
            },
            // ⬇️ شيلنا الـ bottomBar
            containerColor = ScreenBg,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddSheet = true },
                    containerColor = Accent,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(18.dp)
                ) { Icon(Icons.Outlined.Add, contentDescription = "Add") }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Subjects",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                }

                items(items) { s ->
                    SubjectCard(
                        subject = s,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onSubjectClick(s) }
                    )
                }

                item { Spacer(Modifier.height(72.dp)) }
            }
        }
    }
//كومنت 
    if (showAddSheet) {
        AddSubjectSheet(
            onDismiss = { showAddSheet = false },
            onConfirm = { name, progress, tasks, flashcards ->
                if (name.isNotBlank()) {
                    items.add(
                        SubjectUi(
                            name = name.trim(),
                            progress = progress.coerceIn(0, 100),
                            tasksCount = tasks.coerceAtLeast(0),
                            flashcardsCount = flashcards.coerceAtLeast(0)
                        )
                    )
                }
                showAddSheet = false
            }
        )
    }
}

@Composable
private fun SubjectCard(
    subject: SubjectUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.5.dp),
        modifier = modifier.clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .background(CardTint)
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text("${subject.progress}%", color = Accent, fontSize = 13.sp)
            Spacer(Modifier.height(2.dp))
            Text(subject.name, fontWeight = FontWeight.Black, fontSize = 18.sp)

            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { subject.progress / 100f },
                color = Accent,
                trackColor = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(6.dp))
            )

            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "${subject.tasksCount} tasks", color = Accent, fontSize = 13.sp)
                Text(text = "  |  ${subject.flashcardsCount} flashcards", color = Accent, fontSize = 13.sp)
            }
        }
    }
}

@Preview(name = "Subjects - Light", showBackground = true)
@Preview(name = "Subjects - Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SubjectsPreview() {
    val demo = listOf(
        SubjectUi("Mathematics", 60, 5, 20),
        SubjectUi("Physics", 45, 3, 15),
        SubjectUi("Chemistry", 75, 7, 25),
        SubjectUi("Biology", 55, 4, 18),
    )
    MaterialTheme {
        SubjectsScreen(subjects = demo)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSubjectSheet(
    onDismiss: () -> Unit,
    onConfirm: (name: String, progress: Int, tasks: Int, flashcards: Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var name by remember { mutableStateOf("") }
    var progressText by remember { mutableStateOf("0") }
    var tasksText by remember { mutableStateOf("0") }
    var flashText by remember { mutableStateOf("0") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Add Subject", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = progressText,
                    onValueChange = { progressText = it.filter { ch -> ch.isDigit() }.take(3) },
                    label = { Text("Progress %") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    value = tasksText,
                    onValueChange = { tasksText = it.filter { ch -> ch.isDigit() }.take(4) },
                    label = { Text("Tasks") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            OutlinedTextField(
                value = flashText,
                onValueChange = { flashText = it.filter { ch -> ch.isDigit() }.take(5) },
                label = { Text("Flashcards") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cancel") }
                Button(
                    onClick = {
                        val p = progressText.toIntOrNull() ?: 0
                        val t = tasksText.toIntOrNull() ?: 0
                        val f = flashText.toIntOrNull() ?: 0
                        onConfirm(name, p, t, f)
                    },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Accent)
                ) { Text("Add") }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
    navController: NavController,
    tasks: List<TaskUi>,
    onAddTask: () -> Unit = {}
) {
    val items = remember { mutableStateListOf<TaskUi>().also { it.addAll(tasks) } }
    var showAddSheet by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") } // All, Today, By Subject

    val filteredTasks = remember(selectedFilter, items.toList()) {
        when (selectedFilter) {
            "Today" -> {
                // التأكد من تنسيق تاريخ اليوم ليطابق التنسيق المحفوظ (مثال: Nov 28)
                val todayStr = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d"))
                items.filter { it.due.contains(todayStr) }
            }
            "By Subject" -> items.sortedBy { it.subject }
            else -> items
        }
    }

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
            // شريط الفلاتر
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                AppFilterChip("All", selectedFilter == "All") { selectedFilter = "All" }
                AppFilterChip("Today", selectedFilter == "Today") { selectedFilter = "Today" }
                AppFilterChip("By Subject", selectedFilter == "By Subject") { selectedFilter = "By Subject" }
            }

            Spacer(Modifier.height(12.dp))

            // قائمة المهام
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (filteredTasks.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = when (selectedFilter) {
                                        "Today" -> "No tasks for today 🎯"
                                        "By Subject" -> "No tasks available"
                                        else -> "No tasks available"
                                    },
                                    color = Color.Gray,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                } else {
                    items(filteredTasks) { task ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .padding(12.dp)
                                .clickable {
                                    // التنقل للتفاصيل (تأكد من وجود الـ Route هذا في الـ NavHost الخاص بك)
                                    val encodedTitle = URLEncoder.encode(task.title, "UTF-8")
                                    val encodedSubject = URLEncoder.encode(task.subject, "UTF-8")
                                    val encodedDue = URLEncoder.encode(task.due, "UTF-8")
                                    navController.navigate("taskDetail/$encodedTitle/$encodedSubject/$encodedDue")
                                }
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
    }

    if (showAddSheet) {
        AddTaskSheet(
            onDismiss = { showAddSheet = false },
            onConfirm = { title, subject, deadline ->
                if (title.isNotBlank() && subject.isNotBlank()) {
                    items.add(TaskUi(subject = subject.trim(), title = title.trim(), due = deadline.trim()))
                }
                showAddSheet = false
            }
        )
    }
}

@Composable
private fun AppFilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        color = if (selected) Mint else Color.White,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.clickable(onClick = onClick)
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

// --- Add Task Sheet with Date Pick
// er ---
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

    // Date Picker States
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
                .navigationBarsPadding() // عشان الكيبورد
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
                Spacer(Modifier.size(40.dp)) // موازنة المساحة
            }

            // Fields
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

            // Deadline Field - تم التعديل هنا
            // بدل الـ InteractionSource المعقد، استخدمنا Box فوق الحقل أو enabled=false مع clickable
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = deadline,
                    onValueChange = { },
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
                // طبقة شفافة فوق التيكست فيلد عشان نضمن إن أي ضغطة تفتح التاريخ
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }

            Spacer(Modifier.height(4.dp))

            // Action Button
            Button(
                onClick = { onConfirm(title, subject, deadline) },
                // الزرار مش هيشتغل غير لما كل البيانات تكون موجودة
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

    // Date Picker Dialog Logic
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        // هنا اللعبة كلها: لازم نتأكد إن القيمة مش null
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

// دالة مساعدة لتحويل الوقت من Milliseconds إلى نص
@RequiresApi(Build.VERSION_CODES.O)
private fun convertMillisToDate(millis: Long): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy") // مثال: Nov 28, 2025
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
        .format(formatter)
}
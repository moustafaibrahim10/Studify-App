@file:Suppress("UnusedImport")

package com.example.composetest.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.controller.SubjectController
import com.example.data.DataRepository
import com.example.finalfinalefinal.decks_list
import com.example.finalfinalefinal.routs
import com.example.model.Subject
import com.example.studify_app.R


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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(
    navController: NavController,
    onSubjectClick: (Subject) -> Unit = {},
) {

    val subjects = DataRepository.currentUser?.subjects ?: emptyList()

    var showAddSheet by rememberSaveable { mutableStateOf(false) }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(text = "Subjects", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)))
                        }
                    }
                )
            },
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
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                    )
                }

                items(subjects) { subject ->
                    SubjectCard(
                        subject = subject,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onSubjectClick(subject) }
                    )
                }

                item { Spacer(Modifier.height(72.dp)) }
            }
        }
    }

    if (showAddSheet) {
        AddSubjectSheet(
            onDismiss = { showAddSheet = false },
            onConfirm = { name ->
                DataRepository.addSubject(Subject(name))
                showAddSheet = false
            }
        )
    }
}

@Composable
private fun SubjectCard(
    subject: Subject,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val doneCount = subject?.tasks?.count { it.isDone } ?: 0
    val total = subject?.tasks?.size ?: 0
    subject?.currentprogress = if (total > 0) (doneCount * 100 / total) else 0

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
            Text("${subject.currentprogress}%", color = Accent, fontSize = 13.sp,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
            Spacer(Modifier.height(2.dp))
            Text(subject.name, fontWeight = FontWeight.Black, fontSize = 18.sp,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))

            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { subject.currentprogress / 100f },
                color = Accent,
                trackColor = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(6.dp))
            )

            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${subject.tasks.size} tasks", color = Accent, fontSize = 13.sp,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                Text("  |  ${subject.decks.sumOf { it.cards.size }} flashcards", color = Accent, fontSize = 13.sp,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddSubjectSheet(
    onDismiss: () -> Unit,
    onConfirm: (name: String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add Subject", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
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

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text("Cancel", color = Color.Black,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onConfirm(name) },
                    enabled = name.isNotBlank(),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Accent)
                ) {
                    Text("Add",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)))
                }
            }
        }
    }
}

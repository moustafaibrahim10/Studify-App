package com.example.finalfinalefinal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.DataRepository
import com.example.model.Deck
import com.example.model.Subject
import com.example.studify_app.R

private val Accent = Color(0xFF2F7D66)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun decks_list(
    navController: NavController,
    deckclick: () -> Unit = { }
) {
    val user = DataRepository.currentUser!!
    val decks = remember { derivedStateOf { user.subjects.flatMap { it.decks } } }

    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var addDeckError by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Flashcards",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            adddeckbtn { isSheetOpen = true }
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                "Decks",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                fontSize = 20.sp
            )

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(decks.value) { deck ->
                    var showDeleteDeckDialog by remember { mutableStateOf(false) }

                    if (showDeleteDeckDialog) {
                        DeleteDeckDialog(
                            deck = deck,
                            onDismiss = { showDeleteDeckDialog = false },
                            onConfirm = {
                                val realSubject = user.subjects.find { it.name == deck.subject }

                                if (realSubject != null) {
                                    deleteDeckCompletely(realSubject, deck)
                                }
                                showDeleteDeckDialog = false
                            }
                        )
                    }

                    deckshape(
                        deck = deck,
                        deckclick = {
                            navController.navigate("deckDetails/${deck.title}")
                        },
                        onLongPress = { showDeleteDeckDialog = true }
                    )
                }
            }
        }
    }

    if (isSheetOpen) {
        AddDeckSheet(
            onDismiss = { isSheetOpen = false },
            onConfirm = { title, subject ->

                val trimmedTitle = title.trim()
                val trimmedSubject = subject.trim()
                

                val newDeck = Deck(
                    title = trimmedTitle,
                    subject = trimmedSubject
                )

                val existingSubject = user.subjects.find {
                    it.name.equals(trimmedSubject, ignoreCase = true)
                }

                if (existingSubject != null) {
                    existingSubject.decks.add(newDeck)
                    user.decks.add(newDeck)
                } else {
                    val newSubject = Subject(
                        name = trimmedSubject,
                        decks = mutableListOf(newDeck)
                    )
                    user.subjects.add(newSubject)
                    user.decks.add(newDeck)
                }

                addDeckError = null
                isSheetOpen = false
            }


        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun deckshape(deck: Deck, deckclick: () -> Unit, onLongPress: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFCEE1DE), shape = RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = { deckclick() },
                onLongClick = onLongPress
            )
            .padding(16.dp)
            .height(80.dp)
    ) {
        Column {
            Text(
                text = deck.subject,
                color = Accent,
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
            )
            Text(
                text = deck.title,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${deck.cards.size} flashcards",
                color = Color(0xFF2F7D66),
                fontFamily = FontFamily(
                    Font(
                        R.font.plus_jakarta_sans_semibold
                    )
                )
            )
        }
    }
}

@Composable
fun adddeckbtn(adddeckclick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            onClick = adddeckclick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090))
        ) {
            Icon(Icons.Default.Add, contentDescription = "ADD DECK")
            Spacer(Modifier.width(8.dp))
            Text(
                "Add New Deck",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeckSheet(
    onDismiss: () -> Unit,
    onConfirm: (title: String, subject: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close")
                }
                Text(
                    "Add Deck",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                )
                Spacer(Modifier.size(40.dp))
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(
                        "Deck Title",
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                    )
                },
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

            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = {
                    Text(
                        "Subject",
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                    )
                },
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

            Button(
                onClick = { onConfirm(title, subject) },
                enabled = title.isNotBlank() && subject.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F7D66))
            ) {
                Text(
                    "Add Deck",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                )
            }
            Spacer(Modifier.height(12.dp))
        }
    }
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
                Text("Cancel", color = Color(0xFF2F7D66))
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
fun deleteDeckCompletely(subject: com.example.model.Subject, deck: Deck) {
    val user = DataRepository.currentUser ?: return

    subject.decks.remove(deck)
    user.decks.remove(deck)
}



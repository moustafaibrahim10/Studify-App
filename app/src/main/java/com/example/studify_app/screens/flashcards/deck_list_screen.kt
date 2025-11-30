package com.example.finalfinalefinal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.DataRepository
import com.example.model.Deck

private val Accent = Color(0xFF2F7D66)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun decks_list(
    navController: NavController,
    deckclick: () -> Unit = { }
) {
    // ✔ Pull decks from the currently logged-in user
    val user = DataRepository.currentUser!!
    val decks = user.decks

    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Flashcards",
                        fontWeight = FontWeight.SemiBold,
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
            Text("Decks", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(decks) { deck ->
                    deckshape(deck = deck) {
                        navController.navigate("deckDetails/${deck.title}")
                    }
                }
            }
        }
    }

    // ✔ Bottom sheet handles adding to user.decks
    if (isSheetOpen) {
        AddDeckSheet(
            onDismiss = { isSheetOpen = false },
            onConfirm = { title, subject ->
                user.decks.add(Deck(title = title, subject = subject))
                isSheetOpen = false
            }
        )
    }
}

@Composable
fun deckshape(deck: Deck, deckclick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFCEE1DE), shape = RoundedCornerShape(12.dp))
            .clickable { deckclick() }
            .padding(16.dp)
            .height(60.dp)
    ) {
        Column {
            Text(
                text = deck.subject,
                color = Accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(text = deck.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(4.dp))
            Text(text = "${deck.cards.size} flashcards", color = Color(0xFF67C090))
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
            Text("Add New Deck", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp))
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
                Text("Add Deck", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.size(40.dp))
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Deck Title") },
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

            Button(
                onClick = { onConfirm(title, subject) },
                enabled = title.isNotBlank() && subject.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F7D66))
            ) {
                Text("Add Deck", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

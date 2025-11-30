package com.example.finalfinalefinal



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun decks_list(
    navController: NavController,
    deckclick: () -> Unit = { }
) {
    val decks = DataRepository.decks

    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var newDeckName by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Flashcards", fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
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
                items(decks){ deck ->
                    deckshape(deck = deck, deckclick = { navController.navigate("deckDetails/${deck.title}")})
                }
            }
        }
    }

    if (isSheetOpen) {
        AddDeckSheet(
            onDismiss = { isSheetOpen = false },
            onConfirm = { title, subject ->
                DataRepository.addDeck(Deck(title = title, subject = subject))
                isSheetOpen = false
            }
        )
    }
}

@Composable
fun deckshape(deck: Deck, deckclick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFCEE1DE), shape = RoundedCornerShape(12.dp))
            .clickable { deckclick() }
            .padding(16.dp)
            .height(60.dp)

    ){
        Column {
            Text(
                text = deck.subject,
                color = Accent,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(text = deck.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(4.dp))
            Text(text = "10 flashcards", color = Color(0xFF67C090))
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
    ){
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

            // Header with Cancel button
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

            // Deck Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Deck Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Subject Name
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Subject") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Confirm Button
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















//
//
//fun historyQuestions(): List<String>{
//    return listOf(
//        "History Q1",
//        "History Q2",
//        "History Q3",
//        "History Q4",
//        "History Q5"
//
//    )
//}
//fun historyAnswers(): List<String>{
//    return listOf(
//        "History A1",
//        "History A2",
//        "History A3",
//        "History A4",
//        "History A5"
//
//    )
//}
//
//
//
//fun PhysicsQuestions(): List<String>{
//    return listOf(
//        "Physics Q1",
//        "Physics Q2",
//        "Physics Q3",
//        "Physics Q4",
//        "Physics Q5",
//        "Physics Q6",
//        "Physics Q7",
//        "Physics Q8"
//
//    )
//}
//fun PhysicsAnswers(): List<String>{
//    return listOf(
//        "Physics A1",
//        "Physics A2",
//        "Physics A3",
//        "Physics A4",
//        "Physics A5",
//        "Physics A6",
//        "Physics A7",
//        "Physics A8"
//
//    )
//}
//
//
//
//
//fun chimestryQuestions(): List<String>{
//    return listOf(
//        "chimestry Q1",
//        "chimestry Q2",
//    )
//}
//fun chimestryAnswers(): List<String>{
//    return listOf(
//        "chimestry A1",
//        "chimestry A2",
//    )
//}
//
//
//
//fun BiologyQuestions(): List<String>{
//    return listOf(
//        "Biology Q1",
//        "Biology Q2",
//        "Biology Q3",
//        "Biology Q4",
//        "Biology Q5",
//        "Biology Q6",
//        "Biology Q7",
//        "Biology Q8",
//        "Biology Q9",
//        "Biology Q10",
//        "Biology Q11",
//        "Biology Q12"
//    )
//}
//fun BiologyAnswers(): List<String>{
//    return listOf(
//        "Biology A1",
//        "Biology A2",
//        "Biology A3",
//        "Biology A4",
//        "Biology A5",
//        "Biology A6",
//        "Biology A7",
//        "Biology A8",
//        "Biology A9",
//        "Biology A10",
//        "Biology A11",
//        "Biology A12"
//    )
//}

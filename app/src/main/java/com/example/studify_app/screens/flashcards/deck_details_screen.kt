package com.example.finalfinalefinal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.DataRepository
import com.example.studify_app.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun deck_details_screen(
    navController: NavController,
    deckName: String,
    onstartreviewClick: () -> Unit = { navController.navigate("deckTesting/$deckName") }
) {
    val deck = DataRepository.getDeckByTitle(deckName)
        ?: return Text("Deck not found")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        deckName,
                        fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                startreviewBtn(onstartreviewClick)
                addflashcardBtn {
                    navController.navigate("AddFlashCard/$deckName")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(deck.cards) { card ->
                    question_form(card.question)
                }
            }
        }
    }
}


@Composable
fun question_form(question: String){
    Box(
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ){
        Column {
            Text(text = question,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), fontSize = 16.sp, color = Color(0xFF000000)
            )
            Spacer(Modifier.height(4.dp))
        }
    }

}

@Composable
fun startreviewBtn(onstartreviewClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onstartreviewClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Start Review", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), modifier = Modifier.padding(10.dp))

        }
    }
}

@Composable
fun addflashcardBtn(onaddflashcardClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onaddflashcardClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Add Flashcard", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), modifier = Modifier.padding(10.dp))

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFlashcardSheet(
    onDismiss: () -> Unit,
    onConfirm: (question: String, answer: String) -> Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add Flashcard",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                label = { Text("Question",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
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

            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                label = { Text("Answer",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
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
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { onConfirm(question, answer) },
                    enabled = question.isNotBlank() && answer.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                }
            }
        }
    }
}






fun Questionsdt(): List<String>{
    return listOf(
        "Question 1",
        "Question 2",
        "Question 3",
        "Question 4",
        "Question 5",
        "Question 6",
        "Question 7",
        "Question 8",
        "Question 9",
        "Question 10"
    )
}
fun Answersdt(): List<String>{
    return listOf(
        "Answer 1",
        "Answer 2",
        "Answer 3",
        "Answer 4",
        "Answer 5",
        "Answer 6",
        "Answer 7",
        "Answer 8",
        "Answer 9",
        "Answer 10",
    )
}

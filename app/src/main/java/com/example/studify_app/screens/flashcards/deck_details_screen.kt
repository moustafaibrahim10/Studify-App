package com.example.finalfinalefinal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun deck_details_screen(
    navController: NavController,
    deckName: String,
    onstartreviewClick: () -> Unit={navController.navigate(routs.flashCardTesting)}
){
//    var isAddFlashcardDialogOpen by rememberSaveable { mutableStateOf(false) }
    var frontSide by remember { mutableStateOf("") }
    var backSide by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("") }

//    addFlashcarDialog(
//        frontSide = frontSide,
//        onFrontSideChange = {frontSide=it},
//        backSide = backSide,
//        onBackSideChange = {backSide = it},
//        tag = tag,
//        onTagChange = {tag = it},
//        onDismiss = {isAddFlashcardDialogOpen=false},
//        onConfirm = {isAddFlashcardDialogOpen=false},
//        isOpen = isAddFlashcardDialogOpen
//    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deck Details", fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Column (
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                startreviewBtn(onstartreviewClick)
                addflashcardBtn {
                    navController.navigate(routs.addFlashCard)
                }

            }
        }
    ){
            padding ->
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
                items(Questionsdt()){question->
                    question_form(question)
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
            Text(text = question, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF000000)
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
            Text("Start Review", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp))

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
            Text("Add Flashcard", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp))

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
        "Anwer 1",
        "Anwer 2",
        "Anwer 3",
        "Anwer 4",
        "Anwer 5",
        "Anwer 6",
        "Anwer 7",
        "Anwer 8",
        "Anwer 9",
        "Anwer 10",
    )
}

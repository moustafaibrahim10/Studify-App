package com.example.studify_app

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.data.DataRepository

val mintGreen = Color(0xFF66BB6A)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddFlashCard(
    navController: NavHostController,
    deckName: String
) {
    var front by remember { mutableStateOf("") }
    var back by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(18.dp),
            text = "Add New Flashcard",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // FRONT
        TextField(
            value = front,
            onValueChange = { front = it },
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            label = { Text("Front Text", color = mintGreen) },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F4F3),
                unfocusedContainerColor = Color(0xFFF0F4F3),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = mintGreen
            )
        )

        // BACK
        TextField(
            value = back,
            onValueChange = { back = it },
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            label = { Text("Back Text", color = mintGreen) },
            shape = RoundedCornerShape(10),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F4F3),
                unfocusedContainerColor = Color(0xFFF0F4F3),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = mintGreen
            )
        )

        Spacer(Modifier.weight(1f))

        Buttons(
            navController = navController,
            deckName = deckName,
            front = front,
            back = back
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Buttons(
    navController: NavHostController,
    deckName: String,
    front: String,
    back: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // CANCEL
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(18.dp),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text("Cancel")
        }

        Spacer(Modifier.weight(1f))

        // SAVE
        Button(
            onClick = {
                DataRepository.addFlashcardToDeck(deckName, front, back)
                navController.popBackStack() // Return to deck details
            },
            enabled = front.isNotBlank() && back.isNotBlank(),
            modifier = Modifier.padding(18.dp),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                containerColor = mintGreen,
                contentColor = Color.White
            )
        ) {
            Text("Save")
        }
    }
}

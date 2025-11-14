package com.example.studify_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val mintGreen = Color(0xFF66BB6A)

@Composable
fun AddFlashCard() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
            text = "Add New Flashcard",
            style = TextStyle(
                fontSize = 18.sp, fontWeight = FontWeight.Bold,
            ),
        )
        FrontTextField()
        BackTextField()
        TagTextField()
        Buttons()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrontTextField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { txt ->
            text = txt
        },
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 18.dp)
            .fillMaxWidth(),
        label = { Text("Front  Text", style = TextStyle(color = mintGreen)) },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.colors(

            focusedContainerColor = Color(0xFFF0F4F3),
            unfocusedContainerColor = Color(0xFFF0F4F3),
            disabledContainerColor = Color(0xFFF0F4F3),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = mintGreen

        )

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTextField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { txt ->
            text = txt
        },
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 18.dp)
            .fillMaxWidth(),
        label = { Text("Back Text", style = TextStyle(color = mintGreen)) },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.colors(

            focusedContainerColor = Color(0xFFF0F4F3),
            unfocusedContainerColor = Color(0xFFF0F4F3),
            disabledContainerColor = Color(0xFFF0F4F3),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            cursorColor = mintGreen

        )

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagTextField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { txt ->
            text = txt
        },
        modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 18.dp)
            .fillMaxWidth(),
        label = { Text("Tag ", style = TextStyle(color = mintGreen)) },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.colors(

            focusedContainerColor = Color(0xFFF0F4F3),
            unfocusedContainerColor = Color(0xFFF0F4F3),
            disabledContainerColor = Color(0xFFF0F4F3),

            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

            cursorColor = mintGreen

        )

    )
}

@Composable
fun Buttons() {

    Row() {
        Button(
            onClick = {},
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            )
        )
        {
            Text(text = "Cancel")

        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {},
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(
                containerColor = mintGreen,
                contentColor = Color.White
            )
        )
        {
            Text(text = "Save")

        }

    }
}


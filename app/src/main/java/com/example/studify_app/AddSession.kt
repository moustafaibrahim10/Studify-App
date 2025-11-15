package com.example.studify_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class AddSession : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AddSessionScreen(navController)
        }
    }
}

@Composable
fun AddSessionScreen(navController: NavHostController) {
    val fieldColor = Color(0xFFE8F2F0)
    val textColor = Color(0xFF67C090)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Add Study Session",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Title", color = textColor) },
            textStyle = TextStyle(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .background(fieldColor, shape = RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                cursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = textColor,
                unfocusedLabelColor = textColor
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Subject", color = textColor) },
            textStyle = TextStyle(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .background(fieldColor, shape = RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                cursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = textColor,
                unfocusedLabelColor = textColor
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Due Date", color = textColor) },
            textStyle = TextStyle(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .background(fieldColor, shape = RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = fieldColor,
                unfocusedContainerColor = fieldColor,
                cursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = textColor,
                unfocusedLabelColor = textColor
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(context, "Session Added Successfully!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = textColor)
        ) {
            Text("Add Session", color = Color.White)
        }
    }
}

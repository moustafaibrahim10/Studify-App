package com.example.finalfinalefinal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun addFlashcarDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    isOpen: Boolean,
    frontSide: String,
    onFrontSideChange: (String) -> Unit,
    backSide: String,
    onBackSideChange: (String) -> Unit,
    tag: String,
    onTagChange: (String) -> Unit
){
    if(isOpen){
        AlertDialog(
            onDismissRequest = {onDismiss},
            title = { Text("ADD Flashcard") },
            text = {
                Column {
                        OutlinedTextField(value = frontSide, onValueChange = onFrontSideChange, label = {Text("Front Side")}, singleLine = true)
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(value = backSide, onValueChange = onBackSideChange, label = {Text("Back Side")}, singleLine = true)
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(value = tag, onValueChange = onTagChange, label = {Text("Tag")}, singleLine = true)
                        Spacer(Modifier.width(8.dp))

                }


            },
            confirmButton = {
                TextButton (onClick =  onConfirm ){
                    Text("Add", color = Color(0xFF67C090))
                }
            },
            dismissButton = {
                TextButton (onClick =  onDismiss){
                    Text("Cancel", color = Color(0xFF67C090))
                }
            }

        )
    }
}
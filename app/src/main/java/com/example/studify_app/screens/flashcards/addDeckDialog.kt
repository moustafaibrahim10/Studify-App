package com.example.finalfinalefinal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun addDeckDialog(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    isOpen: Boolean,
    name: String,
    onnameChange: (String) -> Unit
){
    if(isOpen){
        AlertDialog(
            onDismissRequest = {onDismiss},
            title = { Text("ADD Deck") },
            text = {
                Column {

                    TextField(value = name, onValueChange = onnameChange, label = {Text("Desck name")}, singleLine = true)
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
package com.example.finalfinalefinal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.studify_app.R

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
                        OutlinedTextField(
                            value = frontSide,
                            onValueChange = onFrontSideChange,
                            label = {Text("Front Side", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))}
                            , singleLine = true,
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
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(
                            value = backSide,
                            onValueChange = onBackSideChange,
                            label = {Text("Back Side",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))},
                            singleLine = true,
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
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(
                            value = tag,
                            onValueChange = onTagChange,
                            label = {Text("Tag",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))},
                            singleLine = true,
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
                        Spacer(Modifier.width(8.dp))

                }


            },
            confirmButton = {
                TextButton (onClick =  onConfirm ){
                    Text("Add", color = Color(0xFF67C090),fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                }
            },
            dismissButton = {
                TextButton (onClick =  onDismiss){
                    Text("Cancel", color = Color(0xFF67C090),fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
                }
            }

        )
    }
}
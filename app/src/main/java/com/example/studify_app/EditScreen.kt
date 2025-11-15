package com.example.studify_app

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditScr(navController: NavController) {

    val scrollState = rememberScrollState()

    val Nameinputvalue = remember { mutableStateOf(TextFieldValue()) }
    val Usnameinputvalue = remember { mutableStateOf(TextFieldValue()) }
    val Emailinputvalue = remember { mutableStateOf(TextFieldValue()) }
    val Bioinputvalue = remember { mutableStateOf(TextFieldValue()) }
    val Majorinputvalue = remember { mutableStateOf(TextFieldValue()) }

    Column (
        modifier = Modifier.fillMaxWidth().verticalScroll(scrollState).padding(bottom = 12.dp)
        ,horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .height(56.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Edit",
                    tint = Color.Black
                )
            }
            Text(
                text = "Edit Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.proficcc),
            contentDescription = "Profile Pic",
            modifier = Modifier.size(128.dp).clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Sophia Carter",
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "@sophia.carter",
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans)),
            fontSize = 16.sp,
            color = Color(0xFF4D9987)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Name",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp )
                )
            TextField(
                value = Nameinputvalue.value,
                onValueChange = { Nameinputvalue.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xFFE8F2F0),
                    unfocusedContainerColor = Color(0xFFE8F2F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Username",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp )
            )
            TextField(
                value = Usnameinputvalue.value,
                onValueChange = { Usnameinputvalue.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xFFE8F2F0),
                    unfocusedContainerColor = Color(0xFFE8F2F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Email",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp )
            )
            TextField(
                value = Emailinputvalue.value,
                onValueChange = { Emailinputvalue.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xFFE8F2F0),
                    unfocusedContainerColor = Color(0xFFE8F2F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Bio",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp )
            )
            TextField(
                value = Bioinputvalue.value,
                onValueChange = { Bioinputvalue.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xFFE8F2F0),
                    unfocusedContainerColor = Color(0xFFE8F2F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Major",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 24.dp )
            )
            TextField(
                value = Majorinputvalue.value,
                onValueChange = { Majorinputvalue.value = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xFFE8F2F0),
                    unfocusedContainerColor = Color(0xFFE8F2F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp).padding(horizontal = 24.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF66BB6A),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE8F5E9),
                    disabledContentColor = Color(0xFFB0BEC5)
                )
            ) {
                Text(
                    text = "Save Changes",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold))
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}




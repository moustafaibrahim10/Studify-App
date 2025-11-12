package com.example.finalfinalefinal

import androidx.compose.foundation.background
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun flashCard_testing_screen(
    navController: NavController

){
    var isquestion = true

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("", textAlign = TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ){
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var currentIndex by remember { mutableStateOf(0) }
            var showQuestion by remember { mutableStateOf(true) }

            val questions = Questions()
            val answers = Answers()

            if (currentIndex < questions.lastIndex) {

                if (showQuestion) {
                    qNaBox(questions[currentIndex])
                    flipBtn({ showQuestion = false })
                } else {
                    qNaBox(answers[currentIndex])
                    rightWrongBtn(
                        onNext = {
                            currentIndex++
                            showQuestion = true
                        }
                    )
                }
            }else{
                navController.navigate(routs.flashcardsAnalytics)
            }






        }
    }




}


@Composable
fun qNaBox(qORa: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(300.dp)
            .background(Color(0xFF67C090),shape= RoundedCornerShape(15.dp)),
        contentAlignment = Alignment.Center
    ){
        Text(text =qORa, fontWeight = FontWeight.Bold, color = Color(0xFFFFFFFF))
    }
}

@Composable
fun flipBtn(onflipclick: () -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onflipclick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F0F2)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Flip", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color= Color(
                0xFF000000
            )
            )

        }
    }
}



@Composable
fun rightWrongBtn(onNext: () -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            rightBtn(onNext)
            wrongBtn(onNext)
        }
    }
}



@Composable
fun rightBtn(onNext: () -> Unit){

        Button(
            onClick = onNext,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F0F2)),
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Right 🥳", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color= Color(
                0xFF000000
            )
            )

        }
}



@Composable
fun wrongBtn(onNext: () -> Unit){
           Button(
            onClick = onNext,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F0F2)),
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Wrong 😢", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color= Color(
                0xFF000000
            )
            )

        }
}




fun Questions(): List<String>{
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
fun Answers(): List<String>{
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

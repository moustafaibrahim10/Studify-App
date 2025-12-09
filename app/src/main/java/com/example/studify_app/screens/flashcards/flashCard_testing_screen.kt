package com.example.finalfinalefinal

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.DataRepository
import com.example.studify_app.R

var Total_Right: Int=0
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun flashCard_testing_screen(
    navController: NavController,
    deckname: String
) {
    var currentIndex by remember { mutableStateOf(0) }
    var showQuestion by remember { mutableStateOf(true) }

    val deck = DataRepository.getDeckByTitle(deckname)

    if (deck == null) {
        Text("Deck not found")
        return
    }

    val cards = deck.cards

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(deckname, textAlign = TextAlign.Center,fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (currentIndex < cards.size) {

                qNaBox(
                    if (showQuestion) cards[currentIndex].question
                    else cards[currentIndex].answer
                )

                Spacer(Modifier.height(20.dp))

                if (showQuestion) {
                    flipBtn { showQuestion = false }
                } else {
                    rightWrongBtn(
                         {
                            showQuestion = true
                            currentIndex++
                            Total_Right++
                        },
                        {
                            showQuestion = true
                            currentIndex++
                        }
                    )
                }

            } else {
                navController.navigate("statisticsScreen/$Total_Right/$deckname")
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
        Text(text =qORa, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)), color = Color(0xFFFFFFFF))
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
            Text("Flip", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), modifier = Modifier.padding(10.dp), color= Color(
                0xFF000000
            )
            )

        }
    }
}



@Composable
fun rightWrongBtn(onNext1: () -> Unit,onNext2: () -> Unit){
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
            rightBtn(onNext1)
            wrongBtn(onNext2)
        }
    }
}



@Composable
fun rightBtn(onNext: () -> Unit){

        Button(
            onClick = onNext  ,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F0F2)),
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Right 🥳", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), modifier = Modifier.padding(10.dp), color= Color(
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
            Text("Wrong 😢", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), modifier = Modifier.padding(10.dp), color= Color(
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

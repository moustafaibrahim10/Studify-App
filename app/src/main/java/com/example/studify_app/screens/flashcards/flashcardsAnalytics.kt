package com.example.finalfinalefinal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun flashcardsAnalytics(navController: NavController){

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Analytics",fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)},
                navigationIcon = {
                    IconButton (onClick = {
                        navController.popBackStack()
                        navController.popBackStack()}){
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }


    ) { padding ->
        Column (
            modifier = Modifier.padding(padding).fillMaxSize().padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ){

        }

        }

}




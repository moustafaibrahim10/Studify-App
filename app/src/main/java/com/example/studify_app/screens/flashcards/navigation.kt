package com.example.finalfinalefinal



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun navigation() {
    val navController= rememberNavController()
    NavHost(navController=navController, startDestination = routs.deckList, builder = {
        composable(routs.deckList) {
            decks_list(navController)
        }
        composable(route=routs.deckDetails) {
            deck_details_screen( navController)
        }
        composable(routs.flashCardTesting) {
            flashCard_testing_screen(navController)
        }
        composable(routs.flashcardsAnalytics) {
            flashcardsAnalytics(navController)
        }
})
}






package com.example.studify_app.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.DataRepository
import com.example.studify_app.R
import com.example.finalfinalefinal.routs
import com.example.model.Task

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController) {

    val currentUser = DataRepository.currentUser
    val tasks = currentUser?.tasks ?: emptyList()
    val decks = currentUser?.decks ?: emptyList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        item {
            HeaderSection()
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            val firstName = currentUser?.username?.split(" ")?.firstOrNull() ?: "Student"
            GreetingSection(firstName)
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            ActionButtonsSection(navController)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            GoalsCard(navController, tasks)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            FlashCardsSection(navController, decks.size)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item { ProgressSection(navController) }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Studify",
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
            fontSize = 24.sp,
            color = Color(0xFF1E1E1E)
        )

    }
}
@Composable
fun GreetingSection(userName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.owl_home),
            contentDescription = "Owl",
            modifier = Modifier
                .size(300.dp)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Hi, $userName!",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
            color = Color(0xFF1E1E1E)
        )

        Text(
            text = "Let's make today productive and fun!",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans)),
            color = Color(0xFF666666)
        )
    }
}

@Composable
fun ActionButtonsSection(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { navController.navigate("pomodoro") },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Start Pomodoro",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold))
            )
        }

        Button(
            onClick = { navController.navigate("tasks") },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Add Task",  color = Color.White, fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)))
        }
    }
}
@Composable
fun GoalsCard(navController: NavController, tasks: List<Task>) {

    val nextTask = tasks.firstOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("tasks") },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCEE1DE)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Tasks",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                fontSize = 18.sp,
                color = Color(0xFF1E1E1E)
            )

            Text(
                text = if (tasks.size!=1) "${tasks.size} Tasks" else "1 task ",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                color = Color(0xFF666666),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun UpcomingTasksSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Upcoming Tasks",
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
            fontSize = 18.sp,
            color = Color(0xFF1E1E1E),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TaskItem("Math Assignment","Math","Due Tomorrow", navController)
        Spacer(modifier = Modifier.height(12.dp))
        TaskItem("History Essay","History", "Due Friday", navController)
        Spacer(modifier = Modifier.height(12.dp))
        TaskItem("Science Project","Science", "Due Saturday", navController)
    }
}
@Composable
fun TaskItem(title: String, subject: String, due: String, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("taskDetail/$title/$subject/$due") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("# ", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)), color = Color(0xFF4CAF50), fontSize = 16.sp)
                    Text(title, fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), fontSize = 16.sp, color = Color(0xFF1E1E1E))
                }
                Text(due, color = Color(0xFF666666), fontSize = 14.sp, modifier = Modifier.padding(start = 16.dp),fontFamily = FontFamily(Font(R.font.plus_jakarta_sans)))
            }
        }
    }
}


@Composable
fun FlashCardsSection(navController: NavController, deckCount: Int) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("deckList") },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCEE1DE)),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Flashcards",
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                    fontSize = 16.sp,
                    color = Color(0xFF1E1E1E)
                )

                Text(
                    text = if (deckCount!=1) "${deckCount} Decks" else "1 Deck ",
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                    color = Color(0xFF666666),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ProgressSection(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("progress") },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Weekly Progress Snapshot",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                fontSize = 18.sp,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Study Hours",fontFamily = FontFamily(Font(R.font.plus_jakarta_sans)), color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("12", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)), fontSize = 32.sp, color = Color(0xFF1E1E1E))
            Text(
                text = "This Week: +10%",
                color = Color(0xFF4CAF50),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold))
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Graph
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                val heights = listOf(60, 70, 65, 50, 55, 60, 40)

                days.forEachIndexed { index, day ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .height(heights[index].dp)
                                .background(Color(0xFF4CAF50), RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = day,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold))
                        )
                    }
                }
            }
        }
    }
}

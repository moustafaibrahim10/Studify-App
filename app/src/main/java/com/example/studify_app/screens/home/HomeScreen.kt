package com.example.studify_app.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest.R

@Composable
fun HomeScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF67C090), Color(0xFFE6F4EA))
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
    ) {
        item {
            HeaderSection()
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            GreetingSection()
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ActionButtonsSection()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            GoalsCard()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            UpcomingTasksSection()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            FlashCardsSection()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            ProgressSection()
            Spacer(modifier = Modifier.height(32.dp))
        }
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
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF1E1E1E)
        )
        IconButton(
            onClick = { /* Handle settings */ },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                tint = Color(0xFF1E1E1E)
            )
        }
    }
}

@Composable
fun GreetingSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.owl_home),
            contentDescription = "Owl",
            modifier = Modifier
                .size(240.dp)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hi, Sophia!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E1E1E)
        )

        Text(
            text = "Let's make today productive and fun!",
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )
    }
}

@Composable
fun ActionButtonsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = { /* Start Pomodoro */ },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Start Pomodoro", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }

        Button(
            onClick = { /* Add Task */ },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Add Task", color = Color(0xFF4CAF50), fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun GoalsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Today + Goals",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1E1E1E)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text("2 Tasks Left", fontWeight = FontWeight.Medium, fontSize = 16.sp, color = Color(0xFF1E1E1E))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("3 Study Hours", fontSize = 14.sp, color = Color(0xFF666666))
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE8F5E8), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎯", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun UpcomingTasksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Upcoming Tasks",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1E1E1E),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        TaskItem("Math Assignment", "Due Tomorrow")
        Spacer(modifier = Modifier.height(12.dp))
        TaskItem("History Essay", "Due Friday")
        Spacer(modifier = Modifier.height(12.dp))
        TaskItem("Science Project", "Due Saturday")
    }
}

@Composable
fun TaskItem(title: String, due: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "# ",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50),
                        fontSize = 16.sp
                    )
                    Text(
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color(0xFF1E1E1E)
                    )
                }
                Text(
                    text = due,
                    color = Color(0xFF666666),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun FlashCardsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                    text = "Flashcards Due Today",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF1E1E1E)
                )
                Text(
                    text = "5 Flashcards",
                    color = Color(0xFF666666),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ProgressSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Weekly Progress Snapshot",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1E1E1E)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Study Hours
            Text("Study Hours", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text("12", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF1E1E1E))
            Text(
                text = "This Week: +10%",
                color = Color(0xFF4CAF50),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
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
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
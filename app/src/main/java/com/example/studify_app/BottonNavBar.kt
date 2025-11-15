package com.example.studify_app

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavBar(navController: NavHostController) {
    val activeColor = Color(0xFF67C090)
    val selectedColor = Color.Black
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                   painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home",
                    tint = if (currentRoute == "home") selectedColor else activeColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = {
                Text("Home", color = if (currentRoute == "home") selectedColor else activeColor)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "subjects",
            onClick = {
                if (currentRoute != "subjects") {
                    navController.navigate("subjects") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_subjects),
                    contentDescription = "Subjects",
                    tint = if (currentRoute == "subjects") selectedColor else activeColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = {
                Text("Subjects", color = if (currentRoute == "subjects") selectedColor else activeColor)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "calendar",
            onClick = {
                if (currentRoute != "calendar") {
                    navController.navigate("calendar") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendar",
                    tint = if (currentRoute == "calendar") selectedColor else activeColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = {
                Text("Calendar", color = if (currentRoute == "calendar") selectedColor else activeColor)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "pomodoro",
            onClick = {
                if (currentRoute != "pomodoro") {
                    navController.navigate("pomodoro") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pomodoro),
                    contentDescription = "Pomodoro",
                    tint = if (currentRoute == "pomodoro") selectedColor else activeColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = {
                Text("Pomodoro", color = if (currentRoute == "pomodoro") selectedColor else activeColor)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                if (currentRoute != "profile") {
                    navController.navigate("profile") {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile",
                    tint = if (currentRoute == "profile") selectedColor else activeColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            label = {
                Text("Profile", color = if (currentRoute == "profile") selectedColor else activeColor)
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}

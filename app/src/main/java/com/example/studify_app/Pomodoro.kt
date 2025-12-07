package com.example.studify_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class Pomodoro : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PomodoroScreen(navController)
        }
    }
}

@Composable
fun PomodoroScreen(navController: NavController) {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(25) }
    var seconds by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Pomodoro",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pomodoro_image),
                contentDescription = "Pomodoro Image",
                modifier = Modifier.size(360.dp, 320.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Study Session",
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = buildString {
                append("Focus on your task for the next ")
                if (hours > 0) append("$hours ${if (hours == 1) "hour" else "hours"} ")
                if (minutes > 0) append("$minutes ${if (minutes == 1) "minute" else "minutes"} ")
                if (seconds > 0) append("$seconds ${if (seconds == 1) "second" else "seconds"}")
            },
            style = TextStyle(fontSize = 16.sp),
            fontFamily = FontFamily(Font(R.font.plus_jakarta_sans)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )


        Spacer(modifier = Modifier.height(25.dp))

        // Row لكل Wheels
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularWheel(range = 0..23) { hours = it }
                MinutesWheel { minutes = it }
                SecondsWheel { seconds = it }
            }

        }

        Spacer(modifier = Modifier.height(25.dp))
        val totalSeconds = hours * 3600 + minutes * 60 + seconds
        Button(
            onClick = { navController.navigate("Counter/$totalSeconds") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF67C090))
        ) {
            Text(
                "Add Session",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MinutesWheel(onValueChange: (Int) -> Unit) {
    CircularWheel(range = 0..59, onValueChange = onValueChange)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SecondsWheel(onValueChange: (Int) -> Unit) {
    CircularWheel(range = 0..59, onValueChange = onValueChange)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularWheel(range: IntRange, onValueChange: (Int) -> Unit) {
    val repeatedList = remember { range.toList() + range.toList() + range.toList() }
    val centerIndex = range.count()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = centerIndex)
    val snapping = rememberSnapFlingBehavior(listState)

    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFE8F2F0)),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = snapping,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp)
        )
        {
            items(repeatedList.size) { index ->
                Text(
                    text = repeatedList[index].toString().padStart(2, '0'),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                    modifier = Modifier
                        .height(35.dp)
                        .wrapContentSize()
                )
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->

                if (visibleItems.isNotEmpty()) {

                    val layoutInfo = listState.layoutInfo

                    val center = layoutInfo.viewportStartOffset +
                            (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2
                    val centeredItem = visibleItems.minByOrNull { item ->
                        kotlin.math.abs((item.offset + item.size / 2) - center)
                    }
                    centeredItem?.let { item ->
                        val count = range.count()
                        val actualValue = range.first + (item.index % count)
                        onValueChange(actualValue)
                    }
                }
            }
    }
}

//    LaunchedEffect(listState.isScrollInProgress) {
//        if (!listState.isScrollInProgress) {
//            val idx = listState.firstVisibleItemIndex
//            val value = repeatedList[idx % range.count()]
//            onValueChange(value)
//        }
//    }


//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun CircularWheel(range: IntRange, initialValue : Int , onValueChange: (Int) -> Unit) {
//    val count = range.count()
//    val initialIndex = remember(initialValue) {
//        val mid = Int.MAX_VALUE / 2
//        val reminder = mid % count
//        mid - reminder + (initialValue - range.first)
//    }
//    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
//    val snapping = rememberSnapFlingBehavior(listState)
//
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
//            .collect { visibleItems ->
//                if (visibleItems.isNotEmpty()) {
//                    val center = listState.layoutInfo.viewportStartOffset +
//                            listState.layoutInfo.viewportEndOffset / 2
//
//                    val centeredItem = visibleItems.minByOrNull { item ->
//                        kotlin.math.abs((item.offset + item.size / 2) - center)
//                    }
//
//                    centeredItem?.let {
//                        val actualValue = range.first + (it.index % count)
//                        onValueChange(actualValue)
//                    }
//                }
//            }
//    }
//
//    Box(
//        modifier = Modifier
//            .size(width = 100.dp, height = 60.dp)
//            .clip(RoundedCornerShape(12.dp))
//            .background(Color(0xFFE8F2F0)),
//        contentAlignment = Alignment.Center
//    ){
//        LazyColumn(
//            state = listState,
//            flingBehavior = snapping,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(vertical = 12.dp)
//        ){
//            items(Int.MAX_VALUE){ index ->
//                val number = range.first + (index % count)
//                Text(
//                    text = number.toString().padStart(2, '0'),
//                    style = MaterialTheme.typography.titleLarge,
//                    fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
//                    color = Color.Black,
//                    modifier = Modifier
//                        .height(35.dp)
//                        .wrapContentSize(Alignment.Center)
//                )
//            }
//        }
//    }
//
//}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun MinutesWheel(onValueChange: (Int) -> Unit) {
//    CircularWheel(range = 0..59, onValueChange = onValueChange)
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun SecondsWheel(onValueChange: (Int) -> Unit) {
//    CircularWheel(range = 0..59, onValueChange = onValueChange)
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun CircularWheel(range: IntRange, onValueChange: (Int) -> Unit) {
//    val repeatedList = remember { range.toList() + range.toList() + range.toList() }
//    val centerIndex = range.count()
//    val listState = rememberLazyListState(initialFirstVisibleItemIndex = centerIndex)
//    val snapping = rememberSnapFlingBehavior(listState)
//
// {
        // {
//            items(repeatedList.size) { index ->
//                Text(
//                    text = repeatedList[index].toString().padStart(2, '0'),
//                    style = MaterialTheme.typography.bodyLarge,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .height(35.dp)
//                        .wrapContentSize()
//                )
//            }
//        }
//    }
//
//    LaunchedEffect(listState.isScrollInProgress) {
//        if (!listState.isScrollInProgress) {
//            val idx = listState.firstVisibleItemIndex
//            val value = repeatedList[idx % range.count()]
//            onValueChange(value)
//        }
//    }
//}

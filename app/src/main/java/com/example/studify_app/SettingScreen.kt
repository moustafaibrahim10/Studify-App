package com.example.studify_app


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.data.DataRepository


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScr(navController: NavController , viewModel: PomodoroViewModel) {

    val studyLength by viewModel.studyLength.collectAsState()

    var shortBreakLength by rememberSaveable { mutableStateOf(5) }
    var longBreakLength by rememberSaveable { mutableStateOf(15) }
    var sessionsBeforeLongBreak by rememberSaveable { mutableStateOf(4) }


    val user = DataRepository.currentUser!!

    var notificationsEnabled by rememberSaveable { mutableStateOf(user.notificationsEnabled) }
    var soundEffectsEnabled by rememberSaveable { mutableStateOf(user.soundEnabled) }


    Column (
        modifier = Modifier.fillMaxWidth()
        ,horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Setting",
                    tint = Color.Black
                )

            }
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "General",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )



            Spacer(modifier = Modifier.height(20.dp))

            ToggleRow(
                title = "Notifications",
                subtitle = "Enable push notifications for reminders and updates",
                checked = notificationsEnabled
            ) { enabled ->
                notificationsEnabled = enabled
                DataRepository.setNotificationsEnabled(enabled)
            }


            ToggleRow(
                title = "Sound Effects",
                subtitle = "Enable sound effects for a more engaging experience",
                checked = soundEffectsEnabled
            ) { enabled ->
                soundEffectsEnabled = enabled
                DataRepository.setSoundEnabled(enabled)
            }


            Spacer(Modifier.height(18.dp))

            Text(
                text = "Pomodoro",
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(20.dp))

            ValueSelector(
                label = "Study Length",
                sublabel = "Set the duration of your Pomodoro",
                value = "$studyLength min",
                onIncrease = {if (studyLength < 60) viewModel.updateStudyLength(studyLength + 5) },
                onDecrease = { if(studyLength > 5) viewModel.updateStudyLength(studyLength - 5) }
            )
//            ValueSelector(
//                label = "Short Break Length",
//                sublabel = "Set the duration of your short breaks",
//                value = "$shortBreakLength min",
//                onIncrease = { shortBreakLength += 5 },
//                onDecrease = { if(shortBreakLength > 5) shortBreakLength -= 5 }
//            )
//            ValueSelector(
//                label = "Long Break Length",
//                sublabel = "Set the duration of your long breaks",
//                value = "$longBreakLength min",
//                onIncrease = { longBreakLength += 5 },
//                onDecrease = { if(longBreakLength > 5) longBreakLength -= 5 }
//            )
//            ValueSelector(
//                label = "Sessions Before Long Break",
//                sublabel = "Set the number of study sessions before a long break",
//                value = "$sessionsBeforeLongBreak",
//                onIncrease = { sessionsBeforeLongBreak += 1 },
//                onDecrease = { if(sessionsBeforeLongBreak > 1) sessionsBeforeLongBreak -= 1 }
//            )

        }

    }
}

@Composable
fun ToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    val activeColor = Color(0xFF4D9987)
    val inactiveColor = Color(0xFFDCEEEA)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp,vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(title,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                color = Color.Black
            )
            Text (subtitle,
                fontSize = 12.sp,
                fontFamily = FontFamily (Font(R.font.plus_jakarta_sans)),
                color = Color(0xFF4D9987)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = activeColor,
            checkedBorderColor = Color.Transparent,

            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = inactiveColor,
            uncheckedBorderColor = Color.Transparent)
        )
    }
}

@Composable
fun ValueSelector(
    label: String,
    sublabel: String,
    value: String,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 20.dp)
        ) {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)),
                fontSize = 16.sp,
                color = Color.Black
                )
            Text(
                text = sublabel,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans)),
                fontSize = 12.sp,
                color = Color(0xFF4D9987)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextButton(
                onClick = onDecrease,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
            ) {
                Text("-", color = Color.Black)
            }

            Text(
                text = value,
                fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_bold)),
                color = Color.Black
            )

            TextButton(
                onClick = onIncrease,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
            ) {
                Text("+", color = Color.Black)
            }
        }
    }
}


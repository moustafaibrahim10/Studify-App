package com.example.studify_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset // مهم للرسم
import androidx.compose.ui.graphics.Path // مهم لرسم المنحنى
import androidx.compose.ui.graphics.drawscope.Stroke // مهم لرسم الخطوط
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp // مهم للرسم
import androidx.navigation.NavController
import com.example.finalfinalefinal.routs

// الألوان والمقاييس المستخدمة في التصميم
val PrimaryMint = Color(0xFF66BB6A)
val BackgroundPale = Color(0xFFF0FFF0) // لون خلفية خفيف جداً

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPale),
        topBar = {
            TopAppBar(
                title = {
                    Text("Progress", fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_semibold)), fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                StudyHoursCard(
                    title = "Weekly Study Hours",
                    time = "12h 30m",
                    isMonthly = false
                )
            }
            // 2. ساعات الدراسة الشهرية
            item {
                StudyHoursCard(
                    title = "Monthly Study Hours",
                    time = "50h 15m",
                    isMonthly = true
                )
            }
            // 3. تحليل المواد القوية والضعيفة
            item {
                StrongWeakSubjectsCard()
            }
            // 4. منحنى إتقان بطاقات الفلاش - (هذا هو المكان الذي سيتم فيه التعديل)
            item {
                MasteryCurveCard()
            }
        }
    }
}

// =======================================================
// CODES FOR INDIVIDUAL CARDS
// =======================================================

@Composable
fun StudyHoursCard(title: String, time: String, isMonthly: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().height(250.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text(time, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)

            Spacer(Modifier.height(16.dp))

            // الرسم البياني العمودي (Bar Chart)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                val data = if (isMonthly) listOf(0.8f, 0.9f, 0.7f, 0.85f) else listOf(0.7f, 0.9f, 0.5f, 0.6f, 0.8f, 0.9f, 0.75f)
                val labels = if (isMonthly) listOf("Week 1", "Week 2", "Week 3", "Week 4") else listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

                data.forEachIndexed { index, heightFactor ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // الأعمدة البيانية
                        Box(
                            modifier = Modifier
                                .height(60.dp * heightFactor)
                                .width(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(PrimaryMint)
                        )
                        Spacer(Modifier.height(4.dp))
                        // التسميات أسفل الأعمدة
                        Text(labels[index], fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun StrongWeakSubjectsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Strong vs. Weak Subjects", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))

            SubjectProgress("Math", 0.85f)
            SubjectProgress("Science", 0.6f)
            SubjectProgress("History", 0.75f)
            SubjectProgress("English", 0.5f)
        }
    }
}

@Composable
fun SubjectProgress(subject: String, strength: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // اسم المادة
        Text(subject, modifier = Modifier.width(80.dp), fontSize = 14.sp)
        Spacer(Modifier.width(8.dp))

        // شريط التقدم الفعلي
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE0E0E0)) // خلفية الشريط الرمادية
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(strength) // النسبة المئوية للقوة
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(PrimaryMint) // لون التقدم الأخضر
            )
        }
    }
}

@Composable
fun MasteryCurveCard() {
    Card(
        modifier = Modifier.fillMaxWidth().height(150.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Flashcards Mastery Curve", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))

            // **التعديل هنا:** استبدال الـPlaceholder بـMasteryCurveGraph
            MasteryCurveGraph(
                data = listOf(0.4f, 0.6f, 0.3f, 0.7f, 0.5f, 0.9f, 0.6f, 0.7f, 0.5f), // بيانات وهمية
                modifier = Modifier.fillMaxSize().padding(vertical = 4.dp)
            )
        }
    }
}

// =======================================================
// NEW: GRAPH DRAWING COMPOSABLE
// =======================================================

@Composable
fun MasteryCurveGraph(data: List<Float>, modifier: Modifier) {
    val graphColor = PrimaryMint

    // Canvas هو الـComposable المستخدم للرسم اليدوي
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val spacing = width / (data.size - 1)

        // Path لرسم المنحنى
        val path = Path()

        // حساب النقاط
        data.forEachIndexed { index, value ->
            // نحول قيمة 0.0f - 1.0f إلى ارتفاع بالبكسل
            val x = index * spacing
            val y = height * (1 - value) // (1 - value) لعكس الارتفاع بحيث يبدأ 0 من الأسفل

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                // نستخدم المنحنيات (Cubic Bezier) لجعل الرسم ناعماً
                val prevX = (index - 1) * spacing
                val prevY = height * (1 - data[index - 1])

                path.cubicTo(
                    (prevX + x) / 2f, prevY,
                    (prevX + x) / 2f, y,
                    x, y
                )
            }
        }

        // نرسم الخط
        drawPath(
            path = path,
            color = graphColor,
            style = Stroke(width = 5.dp.toPx())
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProgressScreen() {
    ProgressScreen(navController = androidx.navigation.compose.rememberNavController())
}

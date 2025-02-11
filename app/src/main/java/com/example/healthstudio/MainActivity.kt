package com.example.healthstudio

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.healthstudio.ui.theme.HealthStudioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthStudioTheme {
                AppScreen(context = this)
            }
        }
    }
}

@Composable
fun AppScreen(context: Context) {
    var selectedTab by remember { mutableStateOf("Home") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Health Studio",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(getHealthData(context)) { card ->
                CardBox(title = card.first, content = card.second, color = card.third)
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
        MenuButton(selectedTab, onTabSelected = { selectedTab = it })

    }
}

@Composable
fun CardBox(title: String, content: String, color: Color) {
    Card(
        modifier = Modifier
            .height(135.dp)
            .clickable { /*TODO*/ },
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = title, fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun MenuButton(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp)
            .clickable { /*TODO*/ },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf("Home", "Fitness", "Settings").forEach { tab ->
            Text(
                text = tab,
                fontSize = 18.sp,
                fontWeight = if (tab == selectedTab) FontWeight.Bold else FontWeight.Normal,
                color = if (tab == selectedTab) Color.Blue else Color.Black,
                modifier = Modifier.clickable { onTabSelected(tab) }
            )
        }
    }
}

fun getHealthData(context: Context): List<Triple<String, String, Color>> {
    val steps = 7790
    val distance = 6.26
    val heartRate = 82
    val heartTime = "10:20 AM"
    val sleepHours = 6
    val sleepMinutes = 20
    val activity = 521
    val activityGoal = 270
    val fitness = 32
    val fitnessGoal = 20
    val stand = 12
    val standGoal = 10

    return listOf(
        Triple(
            context.getString(R.string.steps_title),
            context.getString(R.string.steps_content, steps, distance),
            Color(ContextCompat.getColor(context, R.color.steps_color))
        ),
        Triple(
            context.getString(R.string.heart_title),
            context.getString(R.string.heart_content, heartRate, heartTime),
            Color(ContextCompat.getColor(context, R.color.heart_color))
        ),
        Triple(
            context.getString(R.string.sleep_title),
            context.getString(R.string.sleep_content, sleepHours, sleepMinutes),
            Color(ContextCompat.getColor(context, R.color.sleep_color))
        ),
        Triple(
            context.getString(R.string.fitness_title),
            context.getString(R.string.fitness_content, activity, activityGoal, fitness, fitnessGoal, stand, standGoal),
            Color(ContextCompat.getColor(context, R.color.fitness_color))
        )
    )
}

fun defaultHealthData(): List<Triple<String, String, Color>> {
    return listOf(
        Triple("Steps / Distance", "Today: -- Steps\nDistance: -- Kilometre", Color(0xFF1E88E5)),
        Triple("Heart Rate", "Newest: -- Times / Minute\nTime: --", Color(0xFF43A047)),
        Triple("Sleep", "Sleep Time:\n-- Hours -- Minutes", Color(0xFFF57C00)),
        Triple("Fitness Record", "Activity: --/-- Kilocalorie\nFitness --/-- Minutes\nStand --/-- Hours", Color(0xFF6A1B9A))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUserGUI() {
    HealthStudioTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Health Studio",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 50.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(defaultHealthData()) { card ->
                    CardBox(title = card.first, content = card.second, color = card.third)
                }
            }
        }
    }
}

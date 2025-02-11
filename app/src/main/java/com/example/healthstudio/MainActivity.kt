package com.example.healthstudio

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
import com.example.healthstudio.ui.theme.HealthStudioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthStudioTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
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
            items(getHealthData()) { card ->
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
        listOf("Home", "Stats", "Settings").forEach { tab ->
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



fun getHealthData(): List<Triple<String, String, Color>> {
    return listOf(
        Triple("Steps / Distance", "Today: 7,790 Steps\nDistance: 6.26 Kilometre", Color(0xFF1E88E5)),
        Triple("Heart Rate", "Newest: 82 Times / Minute\nTime: 10:20 AM", Color(0xFF43A047)),
        Triple("Sleep", "Sleep Time:\n6 Hours 20 Minutes", Color(0xFFF57C00)),
        Triple("Fitness Record", "Activity: 521/270 Kilocalorie\nFitness 32/20 Minutes\nStand 12/10 Hours", Color(0xFF6A1B9A))
    )
}


@Preview (showBackground = true)
@Composable
fun PreviewUserGUI() {
    AppScreen()
}

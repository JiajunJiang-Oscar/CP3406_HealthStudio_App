package com.example.healthstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
                StartPage()
            }
        }
    }
}

@Composable
fun StartPage() {
    Scaffold(
        topBar = { TopAppBarDemo() },
        bottomBar = { BottomAppBarDemo() },
        content = { paddingValues ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2196F3),
                                Color(0xFFBBDEFB),
                                Color(0xFFF57C00)
                            )
                        )
                    )
            ) {
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(defaultHealthData()) { card ->
                        CardBox(title = card.first, content = card.second)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDemo() {
    TopAppBar(
        title = {
            Text(
                text ="Health Studio",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .padding(top = 20.dp)
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.6f)
        )
    )
}

@Composable
fun CardBox(title: String, content: String) {
    Card(
        modifier = Modifier
            .height(135.dp)
            .clickable { /*TODO*/ },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BottomAppBarDemo() {
    BottomAppBar(
        containerColor = Color.Black.copy(alpha = 0.6f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                "Health", Modifier.clickable { /* TODO: Change Page */ },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                "Fitness", Modifier.clickable { /* TODO: Change Page */ },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                "Me", Modifier.clickable { /* TODO: Change Page */ },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

fun defaultHealthData(): List<Pair<String, String>> {
    // Default Value for the card information
    return listOf(
        "Steps / Distance" to "Today: -- Steps\nDistance: -- Kilometre",
        "Heart Rate" to "Newest: -- Times / Minute\nTime: --",
        "Sleep" to "Sleep Time:\n-- Hours -- Minutes",
        "Fitness Record" to "Activity: --/-- Kilocalorie\nFitness --/-- Minutes\nStand --/-- Hours",
        "Weight" to "-- KG",
        "Height" to "-- M"
    )
}

@Preview(showBackground = true)
@Composable
fun StartPagePreview() {
    HealthStudioTheme {
        StartPage()
    }
}
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.example.healthstudio.ui.theme.BlueLight
import com.example.healthstudio.ui.theme.BluePrimary
import com.example.healthstudio.ui.theme.HealthStudioTheme
import com.example.healthstudio.ui.theme.OrangeAccent

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthStudioTheme {
                DetailPage()
            }
        }
    }
}

@Composable
fun DetailPage() {
    Scaffold(
        topBar = { BackHomePage(onBackClick = { /* TODO: Back To Home Page */ }) },
        bottomBar = { BottomBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(BluePrimary, BlueLight, OrangeAccent)
                        )
                    )
            ) {
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(defaultDetailInformation()) { card ->
                        InformationBox(title = card.first, details = card.second)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackHomePage(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "< Back To Home",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable { onBackClick(/* TODO: Change Page */) }
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black.copy(alpha = 0.6f)
        )
    )
}

@Composable
fun InformationBox(title: String, details: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(color = Color.White, thickness = 1.dp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = details,
                fontSize = 18.sp
            )
        }
    }
}

fun defaultDetailInformation(): List<Pair<String, String>> {
    // Default Value for the card information
    return listOf(
        "Heart Rate" to "Range:\nToday: 47 - 160 times/min",
        "Latest Heart Rate" to "Today: 7:54 PM\n47 times/min",
        "About heart rate" to "Your heart beats about 100,000 times a day, speeding up and slowing" +
                " down during exercise and rest. Heart rate is the number of times the" +
                " heart beats per minute and can be considered an indicator of " +
                "cardiovascular health.\n" +
                "\nHealth Studio displays historical heart rate data collected from your " +
                "smart watch or other heart rate monitoring device, allowing you to see " +
                "how your heart rate patterns and changes at different times and for " +
                "different activities.\n",
    )
}

@Preview(showBackground = true)
@Composable
fun SecondPagePreview() {
    HealthStudioTheme {
        DetailPage()
    }
}
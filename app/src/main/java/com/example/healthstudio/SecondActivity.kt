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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.ui.theme.HealthStudioTheme

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
        bottomBar = { MenuBar() },
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
                    .padding(paddingValues)
            ) {
                InformationBox(
                    title = "Your Heart Rate",
                    details = "\n54 - 174 times/minutes\n" +
                            "\n-------messages------\n" +
                            "\n-------messages------\n" +
                            "\nAbout heart rate\n" +
                            "\nYour heart beats about 100,000 times a day, speeding up and slowing" +
                            " down during exercise and rest. Heart rate is the number of times the" +
                            " heart beats per minute and can be considered an indicator of " +
                            "cardiovascular health.\n" +
                            "\nHealth Studio displays historical heart rate data collected from your " +
                            "smart watch or other heart rate monitoring device, allowing you to see " +
                            "how your heart rate patterns and changes at different times and for " +
                            "different activities.\n"
                )
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
                fontSize = 32.sp,
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
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = details,
                fontSize = 22.sp
            )
        }
    }
}

@Composable
fun MenuBar() {
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

@Preview(showBackground = true)
@Composable
fun SecondPagePreview() {
    HealthStudioTheme {
        DetailPage()
    }
}
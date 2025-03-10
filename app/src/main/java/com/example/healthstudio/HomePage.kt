package com.example.healthstudio

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.ui.theme.BlueLight
import com.example.healthstudio.ui.theme.BluePrimary
import com.example.healthstudio.ui.theme.HealthStudioTheme
import com.example.healthstudio.ui.theme.OrangeAccent

class Home : ComponentActivity() {
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
        topBar = { HealthStudioBar() },
        bottomBar = { BottomBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                BluePrimary,
                                BlueLight,
                                OrangeAccent
                            )
                        )
                    )
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(10.dp),
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
fun HealthStudioBar() {
    TopAppBar(
        title = {
            Text(
                text ="Health Studio",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 40.dp)
            )
        },
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.Center
            ){
                val context = LocalContext.current

                Image(
                    painter = painterResource(id = R.drawable.default_user),
                    contentDescription = "User profile",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable {
                            val intent = Intent(context, Account::class.java)
                            context.startActivity(intent)
                        }
                )
            }

        },
        modifier = Modifier.height(150.dp),
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(alpha = 0f)
        )
    )
}

@Composable
fun CardBox(title: String, content: String) {
    Card(
        modifier = Modifier
            .clickable { /*TODO*/ },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Text(
                text = title,
                fontSize = 25.sp,
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(color = Color.White, thickness = 1.dp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BottomBar() {
    val context = LocalContext.current  // 获取上下文

    BottomAppBar(
        containerColor = Color.Black.copy(alpha = 0.6f)
    ) {
        val items = listOf("Health", "Fitness", "Me")
        val activities = listOf(Home::class.java, Fitness::class.java, Account::class.java)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                Text(
                    text = item,
                    modifier = Modifier.clickable {
                        val intent = Intent(context, activities[index])
                        context.startActivity(intent)
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )

                if (index != items.lastIndex) {
                    VerticalDivider(
                        thickness = 1.dp,
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
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
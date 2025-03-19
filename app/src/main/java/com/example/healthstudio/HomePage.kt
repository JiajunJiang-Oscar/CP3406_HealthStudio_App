package com.example.healthstudio

import android.content.Intent
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.healthstudio.ui.theme.BlueLight
import com.example.healthstudio.ui.theme.BluePrimary
import com.example.healthstudio.ui.theme.HealthStudioTheme
import com.example.healthstudio.ui.theme.OrangeAccent

@Composable
fun HomePage(navController: NavController) {

    Scaffold(
        topBar = { HealthStudioBar() },
        bottomBar = { BottomBar(navController) },
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
                Image(
                    painter = painterResource(id = R.drawable.default_user),
                    contentDescription = "User profile",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable { /*TODO: JUMP TO SOME PAGE*/ }
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
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .clickable {
                val intent = Intent(context, Detail::class.java)
                context.startActivity(intent)
            },
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
fun BottomBar(navController: NavController) {
    val items = listOf("home_page", "fitness_page", "account_page")
    val labels = listOf("Health", "Fitness", "Me")

    BottomAppBar(
        containerColor = Color.Black.copy(alpha = 0.6f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, route ->
                Text(
                    text = labels[index],
                    modifier = Modifier.clickable {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
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
        val navController = rememberNavController() // 在预览模式创建一个 NavController
        HomePage(navController)
    }
}
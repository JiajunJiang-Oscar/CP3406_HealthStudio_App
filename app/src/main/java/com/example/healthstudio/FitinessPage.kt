package com.example.healthstudio

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.healthstudio.data.HealthViewModel
import com.example.healthstudio.data.WeatherViewModel
import com.example.healthstudio.ui.theme.BluePrimary
import com.example.healthstudio.ui.theme.HealthStudioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessPage(
    viewModel: HealthViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadData("fitness")
    }

    // Control popup display (Account)
    var showSheet by remember { mutableStateOf(false) }
    // Weather info display
    val weatherInfo = fetchWeatherInfo(weatherViewModel)
    val healthData by viewModel.healthData.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Return and refresh the database
            viewModel.refreshData("fitness")
        }
    }

    Scaffold(
        // Click avatar to display a pop-up window
        topBar = { FitnessPageBar(weatherInfo = weatherInfo) { showSheet = true } },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    // Default background color
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                // Top gradient background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Padding value to top: 200
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    // Top color
                                    BluePrimary,
                                    // Gradually make the bottom transparent
                                    Color.Transparent
                                )
                            )
                        )
                )
                // LazyColumn dynamically loads database data
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(bottom = 95.dp)
                        .padding(horizontal = 15.dp)
                ) {
                    items(healthData) { item ->
                        FitnessCardBox(
                            title = item.title,
                            content = item.value,
                            unit = item.unit,
                            onClick = {
                                val intent = Intent(context, FitnessDetail::class.java)
                                launcher.launch(intent)
                            }
                        )
                    }
                    // Text of fitness is important
                    item {
                        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                        Image(
                            painter = rememberAsyncImagePainter(
                                "https://play-lh.googleusercontent.com/Lv-fXYSg2DGC2NtR-88dQ-jFEZyA9PtxsGqPS9_Oo7VlmfrrwcEI-SnLwVPbM30-spaS=w648-h364-rw"
                            ),
                            contentDescription = "Fitness Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(25.dp))
                        )
                        Box(
                            modifier = Modifier.padding(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.fitness_intro),
                                fontSize = 20.sp,
                            )
                        }
                    }
                }
            }
            // Rule of popup display
            if (showSheet) {
                ModalBottomSheet(
                    // Click outside to close
                    onDismissRequest = { showSheet = false },
                    sheetState = rememberModalBottomSheetState()
                ) {
                    AccountDetailPage(
                        // Text of popup display
                        username = "TestUsername",
                        email = "Test.User.email@example.com"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessPageBar(weatherInfo: String, showAccountPage: () -> Unit) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.fitness_page),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        // Show weather data
                        text = weatherInfo,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
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
                        .clickable { showAccountPage() }
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
fun FitnessCardBox(
    title: String,
    content: String,
    unit: String,
    onClick: () -> Unit
) {
    val numericValue = content.filter { it.isDigit() }.toIntOrNull()

    // Generate health alerts based on the title
    val healthTip = when {
        "Fitness Record - Activity" in title && numericValue != null -> when {
            numericValue > 1000 -> stringResource(R.string.h_activity)
            numericValue < 500 -> stringResource(R.string.l_activity)
            else -> null
        }
        "Fitness Record - Fitness" in title && numericValue != null -> when {
            numericValue > 1800 -> stringResource(R.string.h_fitness)
            numericValue < 400 -> stringResource(R.string.l_fitness)
            else -> null
        }
        "Fitness Record - Stand" in title && numericValue != null -> when {
            numericValue > 800 -> stringResource(R.string.h_stand)
            numericValue < 400 -> stringResource(R.string.l_stand)
            else -> null
        }
        else -> null
    }

    Card(
        modifier = Modifier
            .clickable {
                onClick()
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
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color.White, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                // Have content and units distributed on both ends
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = content,
                    fontSize = 20.sp,
                )
                Text(
                    text = unit,
                    fontSize = 20.sp,
                )
            }
            healthTip?.let {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun fetchWeatherInfo(viewModel: WeatherViewModel): String {
    var weatherInfo by remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        viewModel.fetchWeather("Singapore") { response ->
            weatherInfo = if (response != null) {
                "${response.main.temp}°C - ${response.weather.firstOrNull()?.description ?: "Unknown"}"
            } else {
                "Weather unavailable"
            }
        }
    }
    return weatherInfo
}

@Preview(showBackground = true)
@Composable
fun FitnessPageReview() {
    HealthStudioTheme {
        HomePage()
    }
}
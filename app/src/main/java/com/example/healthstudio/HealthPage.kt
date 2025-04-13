package com.example.healthstudio

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.key
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
import com.example.healthstudio.ui.theme.HealthStudioTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: HealthViewModel = viewModel()) {
    // Load Data
    LaunchedEffect(Unit) {
        viewModel.loadData("health")
    }

    // Control popup display (Account)
    var showAccount by remember { mutableStateOf(false) }
    val healthData by viewModel.healthData.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Return and refresh the database
            viewModel.refreshData("health")
        }
    }

    Scaffold(
        // Click avatar to display a pop-up window
        topBar = { HealthStudioBar(viewModel) { showAccount = true } },
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
                                    Color(0xFFFFA500),
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
                        CardBox(
                            title = item.title,
                            content = item.value,
                            unit = item.unit,
                            onClick = {
                                val intent = Intent(context, HealthImport::class.java)
                                launcher.launch(intent)
                            }
                        )
                    }
                    // Health article
                    item {
                        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                        Article(
                            title = stringResource(id = R.string.health_title),
                            content = stringResource(id = R.string.health_intro),
                            imageUrl = "https://img.doooor.com/img/forum/202105/30/111213vjsisvp6s8lrper3.jpg"
                        )
                    }
                }
            }
            // Rule of popup display
            if (showAccount) {
                ModalBottomSheet(
                    // Click outside to close
                    onDismissRequest = { showAccount = false },
                    sheetState = rememberModalBottomSheetState()
                ) {
                    val userSettings by viewModel.userSettings.collectAsState()
                    key(userSettings) {
                        AccountDetailPage(viewModel = viewModel)
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthStudioBar(viewModel: HealthViewModel, showAccountPage: () -> Unit) {
    // Function of top bar in health page and pass a function to trigger a popup
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.app_name),
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
                        text = getGreetingMessage(),
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
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.White, shape = CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.health_studio_user2),
                        contentDescription = "User profile",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable {
                                showAccountPage()
                                viewModel.loadUserSettings()
                            }
                    )
                }
            }
        },
        modifier = Modifier.height(150.dp),
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(alpha = 0f)
        )
    )
}

@Composable
fun CardBox(title: String, content: String, unit: String, onClick: () -> Unit) {
    // Function of definition the health content in card
    val numericValue = content.filter { it.isDigit() }.toIntOrNull()

    // Generate health alerts based on the title
    val healthTip = when {
        "Heart Rate" in title && numericValue != null -> when {
            numericValue > 100 -> stringResource(R.string.h_heart_rate)
            numericValue < 50 -> stringResource(R.string.l_heart_rate)
            else -> null
        }
        "Walk Distance Today" in title && numericValue != null && numericValue < 3000
            -> { stringResource(R.string.l_walk_distance) }
        "Sleep Time" in title && numericValue != null && numericValue < 6
            -> { stringResource(R.string.l_sleep_time) }
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
                color = Color(0xFFFFA500),
                fontWeight = FontWeight.Bold
            )
            HalvingLineSpace_Card()
            Row(
                modifier = Modifier.fillMaxWidth(),
                // Have content and units distributed on both ends
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = content, fontSize = 20.sp)
                Text(text = unit, fontSize = 20.sp)
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
fun getGreetingMessage(): String {
    // Function to decide greeting message
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> stringResource(R.string.morning)
        in 12..17 -> stringResource(R.string.afternoon)
        else -> stringResource(R.string.night)
    }
}

@Composable
fun Article(title: String, content: String, imageUrl: String) {
    // function to create article
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(25.dp))
        )

        Text(
            text = content,
            fontSize = 20.sp
        )
    }
}

@Composable
fun HalvingLineSpace() {
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider(color = Color.Gray, thickness = 1.dp)
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun HalvingLineSpace_Card() {
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun StartPagePreview() {
    HealthStudioTheme {
        HomePage()
    }
}
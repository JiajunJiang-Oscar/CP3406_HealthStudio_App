package com.example.healthstudio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.ui.theme.BlueLight
import com.example.healthstudio.ui.theme.BluePrimary
import com.example.healthstudio.ui.theme.HealthStudioTheme
import com.example.healthstudio.ui.theme.OrangeAccent

@Composable
fun AccountPage() {

    Scaffold(
        topBar = { AccountPageBar() },
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
                // Add verticalScroll to get page scrolling
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding()) // 应用 `Scaffold` 提供的顶部 padding
                        .padding(bottom = 100.dp) // 为 `BottomBar` 留出空间，防止遮挡(100dp)
                        .padding(horizontal = 15.dp)
                        .verticalScroll(rememberScrollState()), // 这里启用滚动
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // User Account Info
                    UserInfoCard(
                        username = "TestUsername",
                        email = "Test.User.email@example.com"
                    )
                    SettingsButton(button = stringResource(id = R.string.go_to_login))
                    SettingsButton(button = stringResource(id = R.string.unlock_more_function))
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                    // Input Body Information
                    BodyMetricsForm()
                    SettingsButton(button = stringResource(id = R.string.import_button))
                    SettingsButton(button = stringResource(id = R.string.import_other_way))
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                    SettingsButton(button = stringResource(id = R.string.go_to_setting))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPageBar() {
    TopAppBar(
        title = {
            Text(
                text ="Your Account",
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
                        .clickable { /* TODO: open "me" */ }
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
fun UserInfoCard(username: String, email: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "Username: $username", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: $email", fontSize = 18.sp)
        }
    }
}

@Composable
fun BodyMetricsForm() {
    var selectedMetric by remember { mutableStateOf("Choice Value Type") }
    var expanded by remember { mutableStateOf(false) }
    var metricValue by remember { mutableStateOf("") }

    val metricOptions = listOf("Height (cm)", "Weight (kg)", "Heart Rate", "Sleep Time", "Steps")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text(text = "Import Your Information", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Choice information type here")
        // Choice type check box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(
                    Color.LightGray.copy(alpha = 0.3f),
                    androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(text = selectedMetric, fontSize = 18.sp)
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp)
            ) {
                metricOptions.forEach { option ->
                    Text(
                        text = option,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedMetric = option
                                expanded = false
                            }
                            .padding(10.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Input the value here")
        // Input Value
        OutlinedTextField(
            value = metricValue,
            onValueChange = { metricValue = it },
            label = { Text("Enter Value") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SettingsButton(button: String) {
    Button(
        onClick = { /* TODO: Jump to page */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = " $button " , fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    HealthStudioTheme {
        HomePage()
    }
}
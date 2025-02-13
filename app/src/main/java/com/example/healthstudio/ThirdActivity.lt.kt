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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.ui.theme.HealthStudioTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthStudioTheme {
                AccountPage()
            }
        }
    }
}

@Composable
fun AccountPage() {
    Scaffold(
        topBar = { AccountTopAppBar() },
        bottomBar = { BottomAppBarDemo() },
        content = { paddingValues ->
            Box(
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
                    ),

            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // User Account Info
                    UserInfoCard(username = "TestUsername", email = "Test.User.email@example.com")
                    SettingsButton(button = "Go To Login")


                    // Input Body Information
                    BodyMetricsForm()


                    // **📌 设置按钮**
                    SettingsButton(button = "Go To Setting")
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text ="Your Account",
                fontSize = 30.sp,
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
    var selectedMetric by remember { mutableStateOf("Height (cm)") } // 默认选项
    var expanded by remember { mutableStateOf(false) } // 控制下拉菜单展开
    var metricValue by remember { mutableStateOf("") } // 存储输入的数值

    val metricOptions = listOf("Height (cm)", "Weight (kg)", "BMI") // 指标选项

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text(text = "Import your information", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(10.dp))

        // **📌 选择指标类型的复选框**
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(Color.LightGray.copy(alpha = 0.3f), androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = selectedMetric, fontSize = 18.sp)
        }

        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
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

        Spacer(modifier = Modifier.height(10.dp))

        // **📌 输入数值**
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
        onClick = { /* TODO: 跳转到设置界面 */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "$button" , fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    HealthStudioTheme {
        AccountPage()
    }
}
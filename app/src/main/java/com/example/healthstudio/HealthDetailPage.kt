package com.example.healthstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.ui.theme.HealthStudioTheme

class HealthDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthStudioTheme {
                HealthDetailPage()
            }
        }
    }
}

@Composable
fun HealthDetailPage() {
    val activity = LocalActivity.current

    Scaffold(
        topBar = { BackHomePage(onBackClick = { activity?.finish() }) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    // Background color (except top)
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            // Top background color
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFA500),
                                    // Gradient becomes transparent to the bottom
                                    Color.Transparent
                                )
                            )
                        )
                )
                // Import Box
                Box(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(bottom = 95.dp)
                        .padding(horizontal = 15.dp)
                ) {
                    ImportHealthValues(
                        // Input Body Information
                    )
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
                text = "< Back ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable { onBackClick() }
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun ImportHealthValues() {
    var selectedMetric by remember { mutableStateOf("Choice Value Type") }
    var expanded by remember { mutableStateOf(false) }
    var metricValue by remember { mutableStateOf("") }

    val metricOptions = listOf("Walking Distance (m)", "Heart Rate", "Sleep Time", "Height (cm)", "Weight (kg)", )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text(
            text = "Import Your Information",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text("Choice information type here")
        // Choice type check box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(
                    Color.Black.copy(alpha = 0.2f),
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
        Spacer(modifier = Modifier.height(20.dp))
        Button( // Import button
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500),
            )
        ) {
            Text(
                stringResource(id = R.string.import_button),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.import_health_data), fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun SecondPagePreview() {
    HealthStudioTheme {
        HealthDetailPage()
    }
}
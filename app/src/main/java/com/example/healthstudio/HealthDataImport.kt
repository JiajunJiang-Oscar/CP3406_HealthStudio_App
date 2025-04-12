package com.example.healthstudio

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.data.HealthViewModel
import com.example.healthstudio.ui.theme.HealthStudioTheme

class HealthImport : ComponentActivity() {
    // Class for health data import
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = HealthViewModel()
        setContent {
            HealthStudioTheme {
                HealthDetailPage(viewModel)
            }
        }
    }
}

@Composable
fun HealthDetailPage(viewModel: HealthViewModel) {
    // Function of build this page
    val activity = LocalActivity.current

    Scaffold(
        topBar = { BackHomePage(onBackClick = { activity?.finish() }) },
        content = { paddingValues ->
            // Same color setting with health page
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFA500),
                                    Color.Transparent
                                )
                            )
                        )
                )
                // Import Box
                Box(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(horizontal = 10.dp)
                ) {
                    ImportHealthValues(viewModel)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackHomePage(onBackClick: () -> Unit) {
    // Function of back home page in top bar
    val activity = LocalActivity.current
    TopAppBar(
        title = {
            Text(
                text = "< Back ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable {
                        activity?.setResult(Activity.RESULT_OK)
                        onBackClick()
                    }
            )
        },
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun ImportHealthValues(viewModel: HealthViewModel) {
    // Function of import data part
    var selectedMetric by remember { mutableStateOf("Choice Value Type") }
    var expanded by remember { mutableStateOf(false) }
    var metricValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    val metricOptions = mapOf(
        "Walking distance in m \n(1000m = 1km)" to "Walk Distance Today",
        "Heart rate" to "Heart Rate",
        "Sleep time" to "Sleep Time",
        "Psychological states \n(0 low - 100 high)" to "Psychological States",
        "Height in cm" to "Height",
        "Weight in kg" to "Weight"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(id = R.string.import_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(stringResource(id = R.string.import_text_field_title))
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
                metricOptions.keys.forEach { option ->
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
                    HorizontalDivider(
                        color = Color.Black.copy(alpha = 0.1f),
                        thickness = 1.dp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(stringResource(id = R.string.import_value_title))
        // Input Value
        OutlinedTextField(
            value = metricValue,
            onValueChange = { metricValue = it },
            label = { Text(stringResource(id = R.string.enter_value)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Click the button to import data
        Button(
            onClick = {
                val metricKey = metricOptions[selectedMetric] ?: return@Button
                // Popup window to show data import state
                if (metricValue.isBlank()) {
                    // Error check
                    Toast.makeText(
                        context,
                        "Your data can not be empty!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Successful import
                    viewModel.updateHealthData(metricKey, metricValue)
                    Toast.makeText(
                        context,
                        "Your data import is successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text(
                stringResource(id = R.string.import_button),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        HalvingLineSpace()
        // Import health article
        Article(
            title = stringResource(id = R.string.import_health_title),
            content = stringResource(id = R.string.import_health_data),
            imageUrl = "https://lh3.googleusercontent.com/proxy/_hBig1dYuX9rOfYgYoW2-aWgZicruKPFCiJFl6B1cQjjVUmcMqg4TBWdq2YMk2wQOwlsg2XgLTB-6FD0NPF12Twy_C8IjLGSIUv7dABmot7ed4K4LVAAKkTfrfn-HIStM5Z0-Ah6GxOwjGitCNaPd4rq1xvbaXsUcolUZq8"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondPagePreview() {
    val fakeViewModel = HealthViewModel()
    HealthStudioTheme {
        HealthDetailPage(fakeViewModel)
    }
}
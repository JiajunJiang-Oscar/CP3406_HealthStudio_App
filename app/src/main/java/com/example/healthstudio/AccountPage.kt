package com.example.healthstudio

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import com.example.healthstudio.data.HealthViewModel
import com.example.healthstudio.ui.theme.HealthStudioTheme

@Composable
fun AccountPage() {
    Scaffold(
        topBar = { AccountPageBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    // Default background color
                    .background(Color.Gray.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Padding value to top: 200
                        .height(200.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    // Top color
                                    Color(0xFF2E8B57),
                                    // Gradually make the bottom transparent
                                    Color.Transparent
                                )
                            )
                        )
                )
                // Add verticalScroll to get page scrolling
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        // Apply the top padding provided by 'Scaffold'
                        .padding(top = paddingValues.calculateTopPadding())
                        // Leave space for 'BottomBar' to prevent occlusion (100dp)
                        .padding(bottom = 100.dp)
                        .padding(horizontal = 15.dp)
                        // Enable scrollable widgets
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Box of user's photo
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
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                    }
                    // Box of user info
                    UserInfoCard()
                    // How to use app article
                    Article(
                        title = stringResource(id = R.string.howto_title),
                        content = stringResource(id = R.string.howto_text),
                        imageUrl = "https://assets-eu-01.kc-usercontent.com/80e06f8f-fc39-0158-c90c-a3da02f900f2/87545b77-ac1d-4eb3-83ca-2ffd9540c2a6/Inside%20Health%20Tile%20iStock-1459130407.jpg"
                    )
                    HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                    // App intro article
                    Article(
                        title = stringResource(id = R.string.intro_title),
                        content = stringResource(id = R.string.intro_text),
                        imageUrl = "https://assets-eu-01.kc-usercontent.com/80e06f8f-fc39-0158-c90c-a3da02f900f2/87545b77-ac1d-4eb3-83ca-2ffd9540c2a6/Inside%20Health%20Tile%20iStock-1459130407.jpg"
                    )
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
                text = stringResource(R.string.account_page),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 40.dp)
            )
        },
        modifier = Modifier.height(150.dp),
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(alpha = 0f)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoCard(viewModel: HealthViewModel = viewModel()) {
    var showPurchase by remember { mutableStateOf(false) }

    var inputUsername by remember { mutableStateOf("") }
    var inputGender by remember { mutableStateOf("") }
    var inputAge by remember { mutableStateOf("") }

    val userSettings by viewModel.userSettings.collectAsState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            LabeledText(label = "User Name", value = userSettings.username)
            LabeledText(label = "Gender", value = userSettings.gender)
            LabeledText(label = "Age", value = userSettings.age.toString())
            HalvingLineSpace()

            // Set info
            LabeledTextField(
                value = inputUsername,
                onValueChange = { inputUsername = it },
                label = "Enter New Username"
            )
            LabeledTextField(
                value = inputGender,
                onValueChange = { inputGender = it },
                label = "Enter Gender"
            )
            LabeledTextField(
                value = inputAge,
                onValueChange = { inputAge = it },
                label = "Enter Age"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SaveAllUserSettingsButton(
                inputUsername = inputUsername,
                inputGender = inputGender,
                inputAge = inputAge,
                viewModel = viewModel,
                onSuccess = {
                    inputUsername = ""
                    inputGender = ""
                    inputAge = ""
                }
            )
            // About member in health studio (unlock)
            Button(
                onClick = { showPurchase = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57),
                )
            ) {
                Text(stringResource(id = R.string.unlock_more_function), fontSize = 20.sp)
            }
        }
    }
    // Management unlock function popup window
    if (showPurchase) {
        ModalBottomSheet(
            // Click outside to close
            onDismissRequest = { showPurchase = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            MemberShipPopup()
        }
    }
}

@Composable
fun LabeledText(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$label: $value",
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
fun LabeledTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    // Function of text field to get user info
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SaveAllUserSettingsButton(
    // Function of save user info button
    inputUsername: String,
    inputGender: String,
    inputAge: String,
    onSuccess: () -> Unit,
    viewModel: HealthViewModel
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val isAllFilled = inputUsername.isNotBlank() && inputGender.isNotBlank() && inputAge.isNotBlank()
            val ageInt = inputAge.toIntOrNull()

            if (!isAllFilled) {
                Toast.makeText(
                    context,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            if (ageInt == null) {
                Toast.makeText(context,
                    "Please enter a valid number for age",
                    Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            viewModel.updateUsername(inputUsername)
            viewModel.updateGender(inputGender)
            viewModel.updateAge(ageInt)
            onSuccess()
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
    ) {
        Text("Save All", fontSize = 18.sp)
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    HealthStudioTheme {
        HomePage()
    }
}
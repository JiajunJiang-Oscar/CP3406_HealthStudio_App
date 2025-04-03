package com.example.healthstudio

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.healthstudio.ui.theme.HealthStudioTheme

@Composable
fun AccountPage() {

    Scaffold(
        topBar = { AccountPageBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.2f)) // **默认背景色**
            ) {
                // **顶部渐变背景**
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // **只占据顶部 200dp**
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF2E8B57), // 顶部深色
                                    Color.Transparent // 渐变到底部变透明
                                )
                            )
                        )
                )
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
                    Box(
                        // User photo
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
                    UserInfoCard(
                        // User Account Info
                        username = "TestUsername",
                        email = "Test.User.email@example.com"
                    )
                    Box(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                        Text(
                            stringResource(id = R.string.howto_title),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        Text(
                            stringResource(id = R.string.howto_text),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 60.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
                        Text(
                            stringResource(id = R.string.intro_title),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        Text(
                            stringResource(id = R.string.intro_text),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 60.dp)
                        )
                    }

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
        modifier = Modifier.height(150.dp),
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(alpha = 0f)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoCard(username: String, email: String) {
    var showAccount by remember { mutableStateOf(false) }
    var showPurchase by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(text = "User Name: $username", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: $email", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                // Get more info button
                onClick = { showAccount = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E8B57),
                )
            ) {
                Text(stringResource(id = R.string.get_info), fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                // Unlock function button
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
    // Management account pop-up window
    if (showAccount) {
        ModalBottomSheet(
            onDismissRequest = { showAccount = false }, // **点击外部关闭**
            sheetState = rememberModalBottomSheetState()
        ) {
            AccountDetailPage( // **弹窗内容**
                username = "TestUsername",
                email = "Test.User.email@example.com"
            )
        }
    }
    // Management unlock function pop-up window
    if (showPurchase) {
        ModalBottomSheet(
            onDismissRequest = { showPurchase = false }, // **点击外部关闭**
            sheetState = rememberModalBottomSheetState()
        ) {
            UnlockFunctionPage()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPagePreview() {
    HealthStudioTheme {
        HomePage()
    }
}
package com.example.healthstudio

import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthstudio.ui.theme.BluePrimary
import com.example.healthstudio.ui.theme.HealthStudioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessPage() {
    var showSheet by remember { mutableStateOf(false) } // 控制弹窗显示

    Scaffold(
        topBar = { FitnessPageBar { showSheet = true } }, // **点击头像，显示弹窗**
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
                                    BluePrimary, // 顶部深色
                                    Color.Transparent // 渐变到底部变透明
                                )
                            )
                        )
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(bottom = 95.dp)
                        .padding(horizontal = 15.dp)
                ) {
                    items(defaultFitnessData()) { card ->
                        FitnessCardBox(title = card.first, content = card.second)
                    }
                }
            }

            // **底部弹窗**
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false }, // **点击外部关闭**
                    sheetState = rememberModalBottomSheetState()
                ) {
                    AccountDetailPage( // **弹窗内容**
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
fun FitnessPageBar(showAccountPage: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text ="Fitness Studio",
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
fun FitnessCardBox(title: String, content: String) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .clickable {
                val intent = Intent(context, FitnessDetail::class.java)
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


fun defaultFitnessData(): List<Pair<String, String>> {
    // Default Value for the card information
    return listOf(
        "Fitness progress" to "Active time: \nFitness time: \nStand time: ",
        "Heart Rate" to "Newest: -- Times / Minute\nTime: --",
        "Medal earned" to "",
        "Run time" to "-- Hrs",
        "Cycling time " to "-- Hrs",
        "Swimming time" to "-- Hrs",
    )
}

@Preview(showBackground = true)
@Composable
fun FitnessPageReview() {
    HealthStudioTheme {
        HomePage()
    }
}
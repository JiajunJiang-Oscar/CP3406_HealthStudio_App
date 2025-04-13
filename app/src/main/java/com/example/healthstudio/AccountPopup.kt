package com.example.healthstudio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.healthstudio.data.HealthViewModel

@Composable
fun AccountDetailPage(viewModel: HealthViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val userSettings by viewModel.userSettings.collectAsState()
        Text(
            text = stringResource(id = R.string.account_pop_title),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        HalvingLineSpace()
        Box(
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(width = 4.dp, color = Color.Gray, shape = CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.health_studio_user2),
                    contentDescription = "User profile",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LabeledText(label = "User Name", value = userSettings.username)
                LabeledText(label = "Gender", value = userSettings.gender)
                LabeledText(label = "Age", value = userSettings.age.toString())
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HalvingLineSpace()
        Text(
            text = "Edit personal information to go to the 'Me' page",
            fontSize = 15.sp
        )
    }
}




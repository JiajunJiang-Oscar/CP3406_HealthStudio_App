package com.example.healthstudio

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UnlockFunctionPage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Get Health+ & Fitness+ Now!",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.fitnessandhealth),
            contentDescription = "poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text("Unlock Your Best Self with Health+ & Fitness+! \n" +
                "\nWhy settle for average when you can achieve more? With Health+ & " +
                "Fitness+, you’ll unlock premium features designed to help you track！\n" +
                "\nHealth+ Features:\n" +
                "Sleep Tracking – Improve your rest quality. \n" +
                "Hearing Monitoring – Protect your ears from loud noise. \n" +
                "Resting Heart Rate – Understand your heart trends. \n" +
                "Annual Health Report – Get a full-year health summary. \n" +
                "\nFitness+ Perks: \n" +
                "Personalized Fitness Advice – Get workouts tailored to you! \n" +
                "\nUpgrade now for a healthier, stronger you!"
                )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /* Buy function */ }
        ) {
            Text(
                text = "To be continue...",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}


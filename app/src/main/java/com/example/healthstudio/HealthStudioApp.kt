package com.example.healthstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthstudio.ui.theme.HealthStudioTheme

class HealthStudioApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthStudioTheme {
                val navController = rememberNavController() // 初始化 NavController

                Scaffold(
                    bottomBar = {  }
                ) { paddingValues ->
                    NavHost( // 直接在 Main 里管理 NavController
                        navController = navController,
                        startDestination = "home_page",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("home_page") { HomePage(navController) } // 传递 NavController
                        composable("fitness_page") { FitnessPage(navController) }
                        composable("account_page") { AccountPage(navController) }
                    }
                }
            }
        }
    }
}
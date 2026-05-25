package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.add.AddScreen
import com.example.myapplication.ui.alarm.AlarmScreen
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.home.LoginScreen
import com.example.myapplication.ui.list.StockListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { nickname ->
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = homeViewModel,
                    onRegisterClick = { navController.navigate("add") },
                    onTabSelected = { tab ->
                        when (tab) {
                            "종목" -> navController.navigate("stockList")
                            "알람" -> navController.navigate("alarm")
                            "마이페이지" -> { }
                        }
                    }
                )
            }
            composable("add") {
                AddScreen(
                    onNavigateToHome = { stockCode, stockName, price, quantity, targetRate, purchaseSite ->
                        homeViewModel.addStock(stockCode, stockName, price, quantity, targetRate, purchaseSite)
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
            composable("stockList") {
                StockListScreen(
                    viewModel = homeViewModel,
                    onTabSelected = { tab ->
                        when (tab) {
                            "홈" -> navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                            "알람" -> navController.navigate("alarm")
                            "마이페이지" -> { }
                        }
                    }
                )
            }
            composable("alarm") {
                AlarmScreen(
                    onTabSelected = { tab ->
                        when (tab) {
                            "홈" -> navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                            "종목" -> navController.navigate("stockList")
                            "마이페이지" -> { }
                        }
                    }
                )
            }
        }
    }
}
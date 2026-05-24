package com.example.myapplication.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.home.item.BottomNavBar
import com.example.myapplication.ui.home.item.StockCardItem
import com.example.myapplication.ui.home.item.StockListItem
import com.example.myapplication.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit = {},
    onTabSelected: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val stockList by viewModel.stockList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadInitialStocks()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp)  // BottomNavBar 높이만큼 여백
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "싸게 사고 비싸게 팔자", fontSize = 12.sp, color = Color.Gray)
            Text(
                text = "비팔톡",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF802D)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "하락 종목", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (isLoading) {
                CircularProgressIndicator(color = Color(0xFFFF802D))
            } else if (stockList.isEmpty()) {
                Text(text = "주식을 등록해주세요", fontSize = 14.sp, color = Color.Gray)
            } else {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    stockList.forEach { stock ->
                        StockCardItem(
                            stockCode = stock.stockCode,
                            companyName = stock.companyName,
                            price = stock.price,
                            changeRate = stock.changeRate,
                            sign = stock.sign
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "임박 종목", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (stockList.isEmpty()) {
                Text(text = "주식을 등록해주세요", fontSize = 14.sp, color = Color.Gray)
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    stockList.forEach { stock ->
                        StockListItem(
                            stockCode = stock.stockCode,
                            companyName = stock.companyName,
                            price = stock.price,
                            changeRate = stock.changeRate,
                            sign = stock.sign
                        )
                    }
                }
            }

            errorMessage?.let {
                Text(text = it, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF802D))
            ) {
                Text(text = "종목 등록", fontSize = 16.sp, color = Color.White)
            }
        }

        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedTab = "홈",
            onTabSelected = onTabSelected
        )
    }
}
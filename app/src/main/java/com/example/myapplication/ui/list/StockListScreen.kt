package com.example.myapplication.ui.list


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.home.item.BottomNavBar
import com.example.myapplication.ui.home.item.StockListItem
import com.example.myapplication.viewmodel.HomeViewModel

@Composable
fun StockListScreen(
    viewModel: HomeViewModel,
    onTabSelected: (String) -> Unit = {}
) {
    val stockList by viewModel.stockList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 80.dp)
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
            Text(text = "전체 종목", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (stockList.isEmpty()) {
                Text(
                    text = "등록된 종목이 없어요",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(stockList) { stock ->
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
        }

        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedTab = "종목",
            onTabSelected = onTabSelected
        )
    }
}
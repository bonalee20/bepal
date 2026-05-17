package com.example.myapplication.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.home.item.StockCardItem
import com.example.myapplication.ui.home.item.StockListItem

@Composable
fun HomeScreen(
    onRegisterClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 앱 타이틀
        Text(text = "싸게 사고 비싸게 팔자", fontSize = 12.sp, color = Color.Gray)
        Text(
            text = "비팔톡",
            fontSize = 30.sp,
            fontWeight = FontWeight.W900,
            color = Color(0xFFFF802D)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 하락 종목
        Text(text = "하락 종목", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 임시 더미 데이터
            StockCardItem("005930", "삼성전자", "71,500", "2.5", "4")
            StockCardItem("000660", "SK하이닉스", "128,500", "1.8", "4")
            StockCardItem("035420", "NAVER", "185,000", "0.5", "4")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 임박 종목
        Text(text = "임박 종목", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // 임시 더미 데이터
            StockListItem("005930", "삼성전자", "71,500", "2.5", "2")
            StockListItem("000660", "SK하이닉스", "128,500", "1.8", "4")
            StockListItem("035420", "NAVER", "185,000", "0.5", "2")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 종목 등록 버튼
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
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
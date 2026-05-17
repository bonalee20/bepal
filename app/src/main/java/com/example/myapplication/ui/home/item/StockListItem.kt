package com.example.myapplication.ui.home.item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StockListItem(
    stockCode: String,      // stck_shrn_iscd
    companyName: String,    // hts_kor_isnm
    price: String,          // stck_prpr
    changeRate: String,     // prdy_ctrt
    sign: String            // prdy_vrss_sign (1,2=상승 / 4,5=하락)
) {
    val isPositive = sign == "1" || sign == "2"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 로고 자리 (임시 회색 원)
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stockCode, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = companyName, color = Color.Gray, fontSize = 12.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(text = price, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(
                text = if (isPositive) "▲$changeRate%" else "▼$changeRate%",
                color = if (isPositive) Color(0xFFF44336) else Color(0xFF2196F3),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StockListItemPreview() {
    StockListItem(
        stockCode = "000660",
        companyName = "SK하이닉스",
        price = "1,285,000",
        changeRate = "2.5",
        sign = "4"
    )
}
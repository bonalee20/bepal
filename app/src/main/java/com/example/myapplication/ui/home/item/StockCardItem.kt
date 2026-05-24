package com.example.myapplication.ui.home.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.util.formatNumber

@Composable
fun StockCardItem(
    stockCode: String,      // stck_shrn_iscd
    companyName: String,    // hts_kor_isnm
    price: String,          // stck_prpr
    changeRate: String,     // prdy_ctrt
    sign: String            // prdy_vrss_sign (1,2=상승 / 4,5=하락)
) {
    val isPositive = sign == "1" || sign == "2"

    Box(
        modifier = Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF0F4FF))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = stockCode, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = companyName, color = Color.Gray, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = formatNumber(price), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isPositive) "▲$changeRate%" else "▼$changeRate%",
                    color = if (isPositive) Color(0xFFF44336) else Color(0xFF2196F3),
                    fontSize = 12.sp
                )
            }
        }
    }
}
@Preview(showBackground = true) // api 가져오면 삭제 예정
@Composable
fun StockCardItemPreview() {
    StockCardItem(
        stockCode = "005930",
        companyName = "삼성전자",
        price = "71,500",
        changeRate = "2.5",
        sign = "2"
    )
}
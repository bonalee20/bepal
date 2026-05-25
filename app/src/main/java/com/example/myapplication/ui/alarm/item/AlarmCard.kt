package com.example.myapplication.ui.alarm.item

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val OrangeColor = Color(0xFFFF802D)

@Composable
fun AlarmCard(
    stockName: String,
    currentPrice: String,
    triggeredAt: String,
    onDelete: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = OrangeColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stockName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1.5f)
            ) {
                Text(text = "달성 금액", fontSize = 11.sp, color = Color.Gray)
                Text(text = currentPrice, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1.5f)
            ) {
                Text(text = "달성 시간", fontSize = 11.sp, color = Color.Gray)
                Text(text = triggeredAt, fontSize = 15.sp, fontWeight = FontWeight.Medium)
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(28.dp)
            ) {
                Text(text = "✕", fontSize = 12.sp, color = Color.LightGray)
            }
        }
    }
}
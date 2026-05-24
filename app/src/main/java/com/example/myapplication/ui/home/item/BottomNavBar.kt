package com.example.myapplication.ui.home.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedTab: String = "홈",
    onTabSelected: (String) -> Unit = {}
) {
    val tabs = listOf("홈", "종목", "알람", "마이페이지")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(0.5.dp, Color.LightGray),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Text(
                text = tab,
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .clickable { onTabSelected(tab) },
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color(0xFFFF802D) else Color.Gray
            )
        }
    }
}
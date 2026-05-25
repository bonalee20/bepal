package com.example.myapplication.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.alarm.item.AlarmCard
import com.example.myapplication.ui.home.item.BottomNavBar

data class AlarmHistoryUi(
    val id: String = java.util.UUID.randomUUID().toString(),
    val stockName: String,
    val currentPrice: String,
    val triggeredAt: String
)

@Composable
fun AlarmScreen(
    onTabSelected: (String) -> Unit = {}
) {
    val periodOptions = listOf("일주일", "한달", "전체")
    var selectedPeriod by remember { mutableStateOf("일주일") }
    var expanded by remember { mutableStateOf(false) }

    var historyList by remember {
        mutableStateOf(
            listOf(
                AlarmHistoryUi(stockName = "삼성전자", currentPrice = "400,000", triggeredAt = "04/28 12:30"),
                AlarmHistoryUi(stockName = "현대제철", currentPrice = "40,000", triggeredAt = "04/28 12:30")
            )
        )
    }

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "알람 기록", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "기간", fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(text = selectedPeriod, fontSize = 13.sp, color = Color.DarkGray)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            periodOptions.forEach { period ->
                                DropdownMenuItem(
                                    text = { Text(text = period, fontSize = 14.sp) },
                                    onClick = {
                                        selectedPeriod = period
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (historyList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "알람 기록이 없습니다", fontSize = 14.sp, color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(historyList, key = { it.id }) { history ->
                        AlarmCard(
                            stockName = history.stockName,
                            currentPrice = history.currentPrice,
                            triggeredAt = history.triggeredAt,
                            onDelete = {
                                historyList = historyList.filter { it.id != history.id }
                            }
                        )

                    }
                }
            }
        }

        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedTab = "알람",
            onTabSelected = onTabSelected
        )
    }
}
package com.example.myapplication.ui.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.add.item.AddField
import com.example.myapplication.ui.add.item.AddTextField
import com.example.myapplication.ui.add.item.StockSearchField
import com.example.myapplication.ui.home.item.BottomNavBar
import com.example.myapplication.util.formatNumber

private fun calculateExpectedProfit(
    purchasePrice: String,
    quantity: String,
    targetRate: String
): String {
    val price = purchasePrice.toDoubleOrNull() ?: return ""
    val qty = quantity.toDoubleOrNull() ?: return ""
    val rate = targetRate.toDoubleOrNull() ?: return ""
    val profit = price * qty * (rate / 100)
    return "%,.0f".format(profit)
}


@Composable
fun AddScreen(
    onNavigateToHome: (
        stockCode: String,
        stockName: String,
        price: String,
        quantity: String,
        targetRate: String,
        purchaseSite: String
    ) -> Unit = { _, _, _, _, _, _ -> }
){
    var stockName by remember { mutableStateOf("") }
    var stockCode by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var purchaseSite by remember { mutableStateOf("") }
    var targetRate by remember { mutableStateOf("") }
    var alarmEnabled by remember { mutableStateOf(false) }
    var stockError by remember { mutableStateOf(false) }  // 추가

    val expectedProfit = remember(purchasePrice, quantity, targetRate) {
        calculateExpectedProfit(purchasePrice, quantity, targetRate)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 80.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("싸게 사고 비싸게 팔자", fontSize = 12.sp, color = Color.Gray)
            Text("비팔톡", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF802D))

            Spacer(modifier = Modifier.height(24.dp))
            Text("종목 등록", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            AddField(label = "구매 종목") {
                StockSearchField(
                    value = stockName,
                    onValueChange = {
                        stockName = it
                        if (it.isNotBlank()) stockError = false
                    },
                    onStockSelected = { code, name ->
                        stockCode = code
                        stockName = name
                        stockError = false
                    }
                )
                if (stockError) {
                    Text(
                        "구매 종목을 선택해주세요.",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "구매 가격") {
                AddTextField(
                    value = formatNumber(purchasePrice),
                    onValueChange = { purchasePrice = it },
                    placeholder = "400000",
                    keyboardType = KeyboardType.Number
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "수량(주)") {
                AddTextField(
                    value = formatNumber(quantity),
                    onValueChange = { quantity = it },
                    placeholder = "10",
                    keyboardType = KeyboardType.Number
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "구매 사이트") {
                AddTextField(
                    value = purchaseSite,
                    onValueChange = { purchaseSite = it },
                    placeholder = "토스 증권"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "목표 수익률 (%)") {
                AddTextField(
                    value = formatNumber(targetRate),
                    onValueChange = { targetRate = it },
                    placeholder = "10",
                    keyboardType = KeyboardType.Number
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddField(label = "예상 수익") {
                AddTextField(
                    value = formatNumber(expectedProfit),
                    onValueChange = {},
                    placeholder = "0",
                    readOnly = true
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("알람 동의", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(8.dp))
                Checkbox(
                    checked = alarmEnabled,
                    onCheckedChange = { alarmEnabled = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF802D))
                )
            }
            Text(
                "설정하신 수신 수단으로 목표 수익률에 도달했을 때 알람 발송",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (stockName.isBlank()) {
                        stockError = true
                    } else {
                        onNavigateToHome(
                            stockCode,
                            stockName,
                            purchasePrice,
                            quantity,
                            targetRate,
                            purchaseSite
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF802D))
            ) {
                Text("등록 완료", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        BottomNavBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedTab = "종목"
        )
    }
}